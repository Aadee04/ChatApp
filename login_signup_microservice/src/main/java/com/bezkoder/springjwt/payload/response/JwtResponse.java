package com.bezkoder.springjwt.payload.response;

import java.util.List;

/**
 * This class represents the response payload containing the JWT token
 * and user details after a successful authentication.
 */
public class JwtResponse {
  // The JWT access token
  private String token;

  // Token type, defaulted to "Bearer"
  private String type = "Bearer";

  // Unique identifier of the user
  private Long id;

  // Username of the authenticated user
  private String username;

  // Email of the authenticated user
  private String email;

  // List of roles assigned to the user
  private List<String> roles;

  /**
   * Constructor to initialize JwtResponse with token and user details.
   *
   * @param accessToken The generated JWT token
   * @param id          The user's unique ID
   * @param username    The user's username
   * @param email       The user's email
   * @param roles       The user's roles (authorities)
   */
  public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
    this.token = accessToken;
    this.id = id;
    this.username = username;
    this.email = email;
    this.roles = roles;
  }

  // Getter and Setter methods for accessing and modifying properties

  public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<String> getRoles() {
    return roles;
  }
}
