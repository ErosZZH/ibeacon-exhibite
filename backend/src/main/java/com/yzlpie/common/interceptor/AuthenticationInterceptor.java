package com.yzlpie.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yzlpie.common.EncryptUtil;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory
			.getLogger(AuthenticationInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		String token = request.getHeader("Authorization");
		
		if(token == null || token.trim().equals("")) {
			String deviceToken = request.getHeader("DeviceToken");
			if(deviceToken == null || deviceToken.trim().equals("")) {
				logger.error("No token.");
				return false;
			} else {
				request.setAttribute("deviceToken", deviceToken);
				return true;
			}
			
		}
			
		String userId = EncryptUtil.decryptUserToken(token);
		if(!userId.equals("error")) {
			request.setAttribute("userid", userId);
			return true;
		} else {
			logger.error("Invalid token.");
			return false;
		}
		
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//logger.info("Post-handle");
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//logger.info("After completion handle");
		try {
			request.getSession().setAttribute("ErrorMsg", null);
		} catch (Exception e) {

		}
	}
}
