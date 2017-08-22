package xss;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

public class XSSCheckFilter implements Filter {
	 private String encode = "UTF-8";// 默认UTF-8编码
	 
	private FilterConfig config;
	
	// 出错跳转的目的地
	private static String errorPath;
	
	// 不进行拦截的url
	private static String[] excludePaths;
	
	// 需要拦截的JS字符关键字
	private static String[] safeless = {
			"<script",
			"</script",
			"<iframe",
			"</iframe",
			"<frame", 
			"</frame",
			"set-cookie",
			"%3cscript",
			"%3c/script",
			"%3ciframe",
			"%3c/iframe",
			"%3cframe", 
			"%3c/frame",
			"src=\"javascript:", 
			"<body",
			"</body",
			"%3cbody",
			"%3c/body",
			"alert",
			"onmouseover",
			"onload",
			"prompt"
	};

	public void doFilter(ServletRequest req, ServletResponse resp,FilterChain filterChain) throws IOException, ServletException {
		 // 设置request编码
        req.setCharacterEncoding(encode);
        // 设置相应信息
        //response.setContentType("text/html;charset=" + encode);
        //response.setCharacterEncoding(encode);
        
		Enumeration params = req.getParameterNames();
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		boolean isSafe = true;
		String requestUrl = request.getRequestURI();
		if (isSafe(requestUrl)) {
			requestUrl = requestUrl.substring(requestUrl.indexOf("/"));
			if (!excludeUrl(requestUrl)) {
				while (params.hasMoreElements()) {
					String cache = req.getParameter((String) params.nextElement());
					if (StringUtils.isNotBlank(cache)) {
						if (!isSafe(cache)) {
							isSafe = false;
							break;
						}
					}
				}
			}
		}else {
			isSafe = false;
		}

		if (!isSafe) {
			request.setAttribute("err", "您输入的参数有非法字符，请输入正确的参数！");
			System.out.println("您请求的参数中有非法字符，请输入正确的参数！");
			request.getRequestDispatcher(errorPath).forward(request, response);
			return;
		}
		filterChain.doFilter(req, resp);
	}

	private static boolean isSafe(String str) {
		if (StringUtils.isNotBlank(str)) {
			for (String s : safeless) {
				if (str.toLowerCase().contains(s)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean excludeUrl(String url) {
		if (excludePaths != null && excludePaths.length > 0) {
			for (String path : excludePaths) {
				if (url.toLowerCase().equals(path)) {
					return true;
				}
			}
		}
		return false;
	}

	public void destroy() {
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		errorPath = config.getInitParameter("errorPath");
		String excludePath = config.getInitParameter("excludePaths");
		if (StringUtils.isNotBlank(excludePath)) {
			excludePaths = excludePath.split(",");
		}
	}
}