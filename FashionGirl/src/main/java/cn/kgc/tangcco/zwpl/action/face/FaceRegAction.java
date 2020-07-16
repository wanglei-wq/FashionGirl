package cn.kgc.tangcco.zwpl.action.face;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;

import cn.kgc.tangcco.zwpl.service.LoginService;
import cn.kgc.tangcco.zwpl.service.impl.LoginServiceImp;
import cn.kgc.tangcco.zwpl.utils.AipFaceHelper;
import cn.kgc.tangcco.zwpl.utils.Base64Util;
import cn.kgc.tangcco.zwpl.utils.RandomNumber;

/**
 * Servlet implementation class RegServlet
 */
@WebServlet("/faceRegist.action")
public class FaceRegAction extends HttpServlet {

	private static final long serialVersionUID = -789877973145341643L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8"); // 设置防止提交的中文数据乱码
		response.setContentType("text/html;charset=UTF-8"); // 设置响应的信息不乱码
		PrintWriter out = response.getWriter();// 获取一个能够向客户端显示信息的对象
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("user_info", RandomNumber.faceRandom());// 用户资料，长度限制256B
		options.put("quality_control", "LOW");// 图片质量控制
		options.put("liveness_control", "LOW");// 活体检测控制
		// 取决于image_type参数，传入BASE64字符串或URL字符串或FACE_TOKEN字符串
		String image = request.getParameter("base");
		image = Base64Util.base64SubString(image);
		String imageType = "BASE64";
		String groupId = "groupId_01";
		String userId = RandomNumber.faceRandom();
		// 人脸注册
		JSONObject res = AipFaceHelper.getInstance().addUser(image, imageType, groupId, userId, options);
		System.out.println(res.toString(2));
		if(res.get("error_msg").toString().equals("SUCCESS") && Integer.parseInt((res.get("error_code").toString()))==0) {
			LoginService loginService = new LoginServiceImp();
	        Map<String, Object> faceMap = loginService.addFaceId(userId);
	        switch (faceMap.get("status").toString()) {
			case "success":
				out.print(JSON.toJSONString(faceMap));
				break;
			default:
				System.out.println("faceId添加错误");
				out.print(res.toString(2));
				break;
			}
		}else {
			out.print(res.toString(2));
		}
	}
}
