
Security Service Overview

This module provides authentication and authorization using JWT in a Spring Boot application. It handles user registration and login, initializes default roles, generates and validates JWT tokens, and configures security settings for protected endpoints.

──────────────────────────────
1. Controllers (package: com.bezkoder.springjwt.controllers)

• AuthController.java  
 - Manages user sign-in and sign-up endpoints under “/api/auth”.  
 - Uses an AuthenticationManager to validate credentials, generates a JWT token via JwtUtils, and returns a JwtResponse containing the token and user details.  
 - Processes role assignment for new users using RoleRepository.

• TestController.java  
 - Provides test endpoints under “/api/test” to verify access control.  
 - Defines endpoints for public access (/all) and for users with specific roles (e.g., /user, /mod, /admin) using @PreAuthorize annotations.

──────────────────────────────
2. Configuration (package: com.bezkoder.springjwt.config)

• StartupInitializer.java  
 - Implements CommandLineRunner to ensure that the default roles (ROLE_USER, ROLE_MODERATOR, ROLE_ADMIN) are created in the database at startup.

• WebSecurityConfig.java  
 - Configures Spring Security settings including CSRF disabling, stateless session management, and access rules for endpoints.  
 - Registers the custom JWT authentication filter (AuthTokenFilter) and sets up a DaoAuthenticationProvider with BCrypt password encoding.

──────────────────────────────
3. Main Application (package: com.bezkoder.springjwt)

• SpringBootSecurityJwtApplication.java  
 - The main entry point for the Security Service application.

──────────────────────────────
4. JWT Handling (package: com.bezkoder.springjwt.security.jwt)

• JwtUtils.java  
 - Contains methods to generate JWT tokens, extract the username from a token, and validate tokens.  
 - Loads secret key and expiration settings from application properties.

• AuthEntryPointJwt.java  
 - Implements AuthenticationEntryPoint to handle unauthorized access attempts.  
 - When a secured endpoint is accessed without proper authentication, it logs the error and sends a JSON response with HTTP status 401.

──────────────────────────────
5. Payload – Request DTOs (package: com.bezkoder.springjwt.payload.request)

• LoginRequest.java  
 - Captures login credentials (username and password) with validation constraints.

• SignupRequest.java  
 - Captures registration details including username, email, password, and an optional set of roles.

──────────────────────────────
6. Payload – Response DTOs (package: com.bezkoder.springjwt.payload.response)

• JwtResponse.java  
 - Represents the response after successful authentication; includes the JWT token, user ID, username, email, and roles.

• MessageResponse.java  
 - Provides a generic response message used to return success or error messages.

──────────────────────────────
7. Security Services (package: com.bezkoder.springjwt.security.services)

• UserDetailsImpl.java  
 - Implements the UserDetails interface and stores user-specific information (ID, username, email, password, authorities).  
 - Ensures that sensitive information such as passwords is not exposed in JSON responses.

• UserDetailsServiceImpl.java  
 - Implements UserDetailsService to load user details from the database based on username.  
 - Converts a User entity into a UserDetailsImpl instance for authentication.
