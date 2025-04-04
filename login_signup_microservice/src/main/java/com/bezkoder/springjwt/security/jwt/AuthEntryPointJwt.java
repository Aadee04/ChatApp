package com.bezkoder.springjwt.security.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class handles unauthorized access attempts in the application.
 * It is triggered whenever an unauthenticated user tries to access a secured resource.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
  // Logger for logging unauthorized access attempts
  private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

  /**
   * This method is invoked when an unauthenticated user attempts to access a secured endpoint.
   * It sends a 401 Unauthorized response with a JSON error message.
   *
   * @param request       The HTTP request that triggered the authentication error.
   * @param response      The HTTP response to be sent back to the client.
   * @param authException The authentication exception that describes the reason for the failure.
   * @throws IOException      If an input or output error occurs while writing the response.
   * @throws ServletException If a servlet-related error occurs.
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    // Log the unauthorized access attempt
    logger.error("Unauthorized error: {}", authException.getMessage());

    // Set response type and status
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    // Construct JSON response body
    final Map<String, Object> body = new HashMap<>();
    body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
    body.put("error", "Unauthorized");
    body.put("message", authException.getMessage());
    body.put("path", request.getServletPath());

    // Convert response body to JSON and write it to the output stream
    final ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(response.getOutputStream(), body);
  }

}
