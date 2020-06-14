package ${basePackageValue}.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.cgr.common.data.jwt_check_res.JwtCheckResUserInfo;
import com.cgr.common.utils.JwtProcess;

public class AuthorityInterceptor extends HandlerInterceptorAdapter {

	/*代码分界head TODO*/
	
	/**
	 * 使用拦截器对访问鉴权
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean checkRes = false;
		checkRes = requestMethodExistCheck(request, handler);
		createResponse(response, checkRes);
		return checkRes;
	}

	private void createResponse(HttpServletResponse response, boolean checkRes) throws IOException {
		if(!checkRes) {
			//重置response
	        response.reset();
	        //设置编码格式
	        response.setCharacterEncoding("UTF-8");
	        response.setContentType("application/json;charset=UTF-8");

	        PrintWriter pw = response.getWriter();
	        JSONObject jsonObject = new JSONObject();
	        jsonObject.put("code", 455);
	        jsonObject.put("message", "鉴权失败");
	        jsonObject.put("data", "");
	        jsonObject.put("currentTime", System.currentTimeMillis());
	        pw.write(jsonObject.toJSONString());
	        pw.flush();
	        pw.close();
		}
	}

	private boolean requestMethodExistCheck(HttpServletRequest request, Object handler) {
		boolean checkRes;
		//请求的是存在的接口方法允许通过
		if ((handler instanceof HandlerMethod)) {
			checkRes = getTokenAndCheck(request);
		}else {
			checkRes = false;
		}
		return checkRes;
	}

	private boolean getTokenAndCheck(HttpServletRequest request) {
		boolean checkRes;
		final String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			final String token = authHeader.substring(7);
			JwtCheckResUserInfo jwtCheckResUserInfo = JwtProcess.checkToken(token);
			checkRes = checkTokenResJudge(request, jwtCheckResUserInfo);
		}else {
			checkRes = false;
		}
		return checkRes;
	}

	private boolean checkTokenResJudge(HttpServletRequest request, JwtCheckResUserInfo jwtCheckResUserInfo) {
		boolean checkRes;
		if (jwtCheckResUserInfo != null) {
			request.setAttribute("JwtCheckResUserInfo", jwtCheckResUserInfo);
			checkRes = true;
		}else {
			checkRes = false;
		}
		return checkRes;
	}
	
	/*代码分界end TODO*/
	
}


