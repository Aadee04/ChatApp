package com.bezkoder.springjwt.payload.request;

import java.util.Set;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object (DTO) for handling user registration requests.
 *
 * This class captures all necessary information for creating a new user account,
 * including credentials, contact information, and role assignments.
 */
public class SignupRequest {
  /**
   * Username for the new account.
   * Cannot be null or blank as indicated by the @NotBlank constraint.
   * The username must be unique within the system.
   */
  @NotBlank
  private String username;

  /**
   * Email address for the new account.
   * Must be a valid email format and cannot be null or blank.
   * The email must be unique within the system.
   */
  @NotBlank
  @Email
  private String email;

  /**
   * Set of role names to be assigned to the new user.
   * If null or empty, a default user role will typically be assigned.
   */
  private Set<String> role;

  /**
   * Password for the new account.
   * Cannot be null or blank as indicated by the @NotBlank constraint.
   * Will be encoded before storage in the database.
   */
  @NotBlank
  private String password;

  /**
   * Returns the username from the signup request.
   *
   * @return The requested username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username for the signup request.
   *
   * @param username The username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Returns the email address from the signup request.
   *
   * @return The email address
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email address for the signup request.
   *
   * @param email The email address to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Returns the password from the signup request.
   *
   * @return The password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password for the signup request.
   *
   * @param password The password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Returns the set of roles requested for the new user.
   *
   * @return Set of role names
   */
  public Set<String> getRole() {
    return this.role;
  }

  /**
   * Sets the roles for the new user.
   *
   * @param role Set of role names to assign
   */
  public void setRole(Set<String> role) {
    this.role = role;
  }
}