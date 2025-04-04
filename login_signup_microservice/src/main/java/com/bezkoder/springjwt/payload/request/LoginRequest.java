package com.bezkoder.springjwt.payload.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for handling login requests.
 *
 * This class captures the credentials needed for user authentication in the system.
 * It's used to transfer login data from client to server in a structured format.
 */
public class LoginRequest {
	/**
	 * Username for authentication.
	 * Cannot be null or blank as indicated by the @NotBlank constraint.
	 */
	@NotBlank
	private String username;

	/**
	 * Password for authentication.
	 * Cannot be null or blank as indicated by the @NotBlank constraint.
	 * This should be transmitted securely (e.g., over HTTPS).
	 */
	@NotBlank
	private String password;

	/**
	 * Returns the username provided in the login request.
	 *
	 * @return The username for authentication
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username for the login request.
	 *
	 * @param username The username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Returns the password provided in the login request.
	 *
	 * @return The password for authentication
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password for the login request.
	 *
	 * @param password The password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}