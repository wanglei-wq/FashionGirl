package cn.kgc.tangcco.zwpl.action.face;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import cn.kgc.tangcco.zwpl.utils.AipFaceHelper;
import cn.kgc.tangcco.zwpl.utils.Base64Util;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/faceLogin.action")
public class FaceLoginAction extends HttpServlet {
	
	private static final long serialVersionUID = -2220092345945011140L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 设置防止提交的中文数据乱码
		response.setContentType("text/html;charset=UTF-8"); // 设置响应的信息不乱码
		PrintWriter out = response.getWriter();// 获取一个能够向客户端显示信息的对象
		// 传入可选参数调用接口
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("quality_control", "LOW");// 图片质量控制
		options.put("liveness_control", "LOW");// 活体检测控制
		options.put("max_user_num", "1"); // 查找后返回的用户数量。返回相似度最高的几个用户
		String image = request.getParameter("base");
		image = Base64Util.base64SubString(image);
		String imageType = "BASE64";
		String groupIdList = "groupId_01"; // 从指定的group中进行查找 用逗号分隔，上限20个
		// 人脸搜索
		JSONObject res = AipFaceHelper.getInstance().search(image, imageType, groupIdList, options);
		//拿到返回的信息（json解析）
		System.out.println(res.toString(2));
		if(res.get("error_msg").toString().equals("SUCCESS") && Integer.parseInt((res.get("error_code").toString()))==0) {
			Object object = res.get("result");
			JSONArray array = ((JSONObject) object).getJSONArray("user_list");
			int arraySize = array.length();
			String[] value = new String[arraySize];
			for (int i = 0; i < arraySize; i++)// 通过循环取出数组里的值
			{
				JSONObject jsonTemp = (JSONObject) array.getJSONObject(i);
				value[i] = jsonTemp.getString("user_info");
			}
			request.getSession().setAttribute("id", value[0]);
			
			out.print(res.toString(2));
		}else {
			out.print(res.toString(2));
		}
	}

}
