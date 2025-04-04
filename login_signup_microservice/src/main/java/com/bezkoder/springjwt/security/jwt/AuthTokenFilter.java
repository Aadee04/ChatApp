package com.bezkoder.springjwt.security.jwt;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bezkoder.springjwt.security.services.UserDetailsServiceImpl;

/**
 * This filter intercepts HTTP requests to check for a JWT token.
 * If a valid token is found, it extracts user details and sets authentication in the security context.
 */
public class AuthTokenFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  // Logger for logging authentication-related issues
  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  /**
   * This method intercepts each request and checks if a valid JWT token is present.
   * If valid, it sets the authenticated user in the security context.
   *
   * @param request     The incoming HTTP request.
   * @param response    The HTTP response.
   * @param filterChain The filter chain to pass the request further.
   * @throws ServletException If servlet-related errors occur.
   * @throws IOException      If an input or output error is detected.
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {
    try {
      // Extract JWT token from request header
      String jwt = parseJwt(request);

      // Validate token and set authentication context if valid
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
        String username = jwtUtils.getUserNameFromJwtToken(jwt);

        // Load user details from the database
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Create authentication token with user details and roles
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // Credentials are not needed
                        userDetails.getAuthorities());

        // Set additional request details
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Set authentication in security context
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      logger.error("Cannot set user authentication: {}", e.getMessage());
    }

    // Continue the filter chain to allow the request to proceed
    filterChain.doFilter(request, response);
  }

  /**
   * Extracts the JWT token from the request Authorization header.
   *
   * @param request The HTTP request containing the Authorization header.
   * @return The JWT token if present and valid, otherwise null.
   */
  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");

    // Check if the Authorization header is present and starts with "Bearer "
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7); // Extract the token after "Bearer "
    }

    return null;
  }
}
