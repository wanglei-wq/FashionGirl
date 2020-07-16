package cn.kgc.tangcco.zwpl.common.servelt;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class BaseServlet
 */
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BaseServlet() {
		super();
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Class<? extends BaseServlet> clazz = this.getClass();
		String methodName = request.getParameter("methodName");
		Method method = null;
		if (!StringUtils.isEmpty(methodName)) {
			try {
				method = clazz.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
				method.setAccessible(true);
				method.invoke(this, request, response);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {

		}
	}

	/**
	 * 请求转发
	 * 
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @param path     跳转路径
	 */
	public void forword(HttpServletRequest request, HttpServletResponse response, String path) {
		try {
			request.getRequestDispatcher(path).forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 网页重定向
	 * 
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @param location 跳转路径
	 */
	public void redirect(HttpServletRequest request, HttpServletResponse response, String location) {
		try {
			response.sendRedirect(location);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 目标地址增加默认前缀
	 * 
	 * @param path 目标地址
	 * @return 增加前缀后的目标地址
	 */
	public String prefix(String path) {
		return "/WEB-INF/" + path;
	}
	/**
	 * 目标地址增加后缀
	 * @param path	目标地址
	 * @return		增加后缀后的目标地址
	 */
	public String subfix(String path) {
		return path + ".jsp";
	}
	/**
	 * 将对象转为json字符串向客户端输出
	 * @param request
	 * @param response
	 * @param object
	 */
	public void printJson(HttpServletRequest request, HttpServletResponse response, Object object) {
		try {
			PrintWriter writer = response.getWriter();
			writer.print(JSON.toJSONString(object));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
