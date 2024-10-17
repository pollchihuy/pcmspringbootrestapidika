package com.juaracoding.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component("customAuthenticationEntryPoint")
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Autowired
	ObjectMapper mapper ;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
			throws IOException, ServletException {

		response.setHeader("Content-Type", "application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		Map<String, Object> data = new HashMap<>();
		data.put("status", false);
		data.put("timestamp", Calendar.getInstance().getTime());
		data.put("error", e.getMessage());
		response.getOutputStream().println(mapper.writeValueAsString(data));
	}
}