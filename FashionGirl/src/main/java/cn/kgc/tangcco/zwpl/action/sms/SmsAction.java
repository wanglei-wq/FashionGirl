package cn.kgc.tangcco.zwpl.action.sms;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import cn.kgc.tangcco.zwpl.utils.RandomNumber;

/**
 * Servlet implementation class SmsAction
 */
@WebServlet("/SmsAction.action")
public class SmsAction extends HttpServlet {
	private static final long serialVersionUID = -1920645928655572805L;
    public SmsAction() {
        super();
    }
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4Fk6kYyE5CJMEMuLVsnV", "6uV3T4H1W2s7scjG3wJRMtxCn2f8Ab");
	        IAcsClient client = new DefaultAcsClient(profile);
	        
	        String phone = req.getParameter("phone");
	        String random = RandomNumber.smsRandom();
	        CommonRequest request = new CommonRequest();
	        request.setMethod(MethodType.POST);
	        request.setDomain("dysmsapi.aliyuncs.com");
	        request.setVersion("2017-05-25");
	        request.setAction("SendSms");
	        request.putQueryParameter("RegionId", "cn-hangzhou");
	        request.putQueryParameter("PhoneNumbers", phone);
	        request.putQueryParameter("SignName", "时尚丽人");
	        request.putQueryParameter("TemplateCode", "SMS_175240532");
	        request.putQueryParameter("TemplateParam", "{\"code\":"+random+"}");
	        try {
	            CommonResponse response = client.getCommonResponse(request);
	            //{"Message":"OK","RequestId":"8F81ECC2-5D3E-4730-8C6E-73D7E0C7117B","BizId":"613801670709904184^0","Code":"OK"}
	            String data = response.getData().toString();
	            System.out.println(data);
	            JSONObject  jsonObject  = JSON.parseObject(data);
	            String stauts = jsonObject.getString("Message");
	            Map<String,Object> map = new HashMap<String, Object>();
	            map.put("status", "failed");
	            PrintWriter writer = resp.getWriter();
	            if(stauts.equals("OK")) {
	            	map.put("status", "success");
	            	map.put("code", random);
	    			writer.print(JSON.toJSONString(map));
	    			writer.flush();
	    			writer.close();
	            }else {
	            	writer.print(JSON.toJSONString(map));
	    			writer.flush();
	    			writer.close();
	            }
	        } catch (ServerException e) {
	            e.printStackTrace();
	        } catch (ClientException e) {
	            e.printStackTrace();
	        }
//			String random = RandomNumber.smsRandom();
//	        Map<String,Object> map = new HashMap<String, Object>();
//            map.put("status", "success");
//            map.put("code", random);
//            new BaseServlet().printJson(req,resp,map);
	}
	@Override
	public void destroy() {
		super.destroy();
	}
	@Override
	public void init() throws ServletException {
		super.init();
	}
}
