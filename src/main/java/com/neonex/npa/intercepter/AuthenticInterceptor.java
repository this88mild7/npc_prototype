package com.neonex.npa.intercepter;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


// 이름 변경
@Service
public class AuthenticInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		// request information print
		printRequestLog(request);
		
		// session check
		// 일단 tester 라는 sesstion 등록
		String userEmail = (String)request.getSession().getAttribute("userEmail");
		if(!StringUtils.hasLength(userEmail)){
			request.getSession().setAttribute("userEmail", "tester");
		}
		return true;
	}


	private void printRequestLog(HttpServletRequest request) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("[REQ]");
			sb.append("_METHOD_[" + request.getMethod() + "]");
			sb.append("_IP_[" + request.getRemoteAddr() + "]");
			sb.append("_REQURL_[" + request.getRequestURI() + "]");
			sb.append("_PARAM_[");

			// parameter
			Enumeration eNames = request.getParameterNames();
			while (eNames.hasMoreElements()) {
				String name = (String) eNames.nextElement();
				String[] values = request.getParameterValues(name);
				String paramIngo = "[" + name + " : ";
				for (int x = 0; x < values.length; x++) {
					if (x == 0) {
						paramIngo += values[x];
					} else {
						paramIngo += ", " + values[x];
					}
				}

				if (StringUtils.hasText((name))) {
					if (name.equals("passWd")) {
						paramIngo = "[password : xxxx ]";
					} else {
						paramIngo += "]";
						
					}
				}

				if (eNames.hasMoreElements()) {
					sb.append(paramIngo + ",");
				} else {
					sb.append(paramIngo);
				}
			}
			sb.append("]");
			LOGGER.info(sb.toString());
		} catch (Exception e) {
			LOGGER.error("{}", e);
		}
	}

}
