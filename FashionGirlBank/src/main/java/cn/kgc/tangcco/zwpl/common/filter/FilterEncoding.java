package cn.kgc.tangcco.zwpl.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class FilterEncoding
 */
@WebFilter(filterName = "filterEncoding", urlPatterns = { "*.action" })
public class FilterEncoding implements Filter {

	/**
	 * Default constructor.
	 */
	public FilterEncoding() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 强制转换参数的数据类型
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		// 处理中文乱码
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");

		String requestMethod = req.getMethod();
		String remoteHost = req.getRemoteHost();
		System.out.println(remoteHost);
		System.out.println(requestMethod);
		if ("OPTIONS".equalsIgnoreCase(requestMethod)) {
			// ajax跨域
			resp.setHeader("Access-Control-Allow-Origin", "*");
			resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
			resp.setHeader("Access-Control-Max-Age", "3600");
			resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
			resp.setHeader("Access-Control-Allow-Credentials", "true");
			return;
		} else if (!"OPTIONS".equalsIgnoreCase(requestMethod)) {
			// ajax跨域
			resp.setHeader("Access-Control-Allow-Origin", "*");
			resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
			resp.setHeader("Access-Control-Max-Age", "3600");
			resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
			resp.setHeader("Access-Control-Allow-Credentials", "true");
			// 放行通过过滤器
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
