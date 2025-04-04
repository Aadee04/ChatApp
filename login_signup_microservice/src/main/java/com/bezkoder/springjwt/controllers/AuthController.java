package com.bezkoder.springjwt.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.JwtResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;

/**
 * Controller for handling authentication-related endpoints.
 *
 * This controller manages user authentication operations including sign-in and sign-up.
 * It allows cross-origin requests from all origins with a maximum age of 3600 seconds
 * and exposes endpoints under the "/api/auth" base path.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  /**
   * Authentication manager for handling authentication requests.
   * Used to authenticate user credentials during sign-in.
   */
  @Autowired
  AuthenticationManager authenticationManager;

  /**
   * Repository for user data access.
   * Used to check for existing users and save new users.
   */
  @Autowired
  UserRepository userRepository;

  /**
   * Repository for role data access.
   * Used to fetch roles when assigning them to users.
   */
  @Autowired
  RoleRepository roleRepository;

  /**
   * Password encoder for securely hashing passwords.
   * Used when registering new users to ensure passwords are not stored in plain text.
   */
  @Autowired
  PasswordEncoder encoder;

  /**
   * Utility for JWT token generation and validation.
   * Used to create authentication tokens upon successful sign-in.
   */
  @Autowired
  JwtUtils jwtUtils;

  /**
   * Processes user sign-in requests.
   *
   * This endpoint authenticates a user based on the provided credentials
   * and returns a JWT token along with user details upon successful authentication.
   *
   * @param loginRequest Object containing username and password for authentication
   * @return ResponseEntity with JWT token and user details or error response
   */
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    try {
      // Attempt authentication
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

      // Set authentication in security context
      SecurityContextHolder.getContext().setAuthentication(authentication);

      // Generate JWT token
      String jwt = jwtUtils.generateJwtToken(authentication);

      // Extract user details
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      List<String> roles = userDetails.getAuthorities().stream()
              .map(item -> item.getAuthority())
              .collect(Collectors.toList());

      return ResponseEntity.ok(new JwtResponse(jwt,
              userDetails.getId(),
              userDetails.getUsername(),
              userDetails.getEmail(),
              roles));

    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
              .body(Map.of("error", "Invalid username or password"));
    }
  }


  /**
   * Processes user registration requests.
   *
   * This endpoint validates the registration data, creates a new user account
   * with appropriate roles, and saves it to the database.
   *
   * @param signUpRequest Object containing registration details including username, email, password, and roles
   * @return ResponseEntity with success message or error response
   */
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    // Check if username is already taken
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Username is already taken!"));
    }

    // Check if email is already in use
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account with encrypted password
    User user = new User(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()));

    // Extract roles from the request
    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    // If no roles specified, assign the default USER role
    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      // Process each role specified in the request
      strRoles.forEach(role -> {
        switch (role) {
          case "admin":
            // Assign ADMIN role
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
            break;

          case "mod":
            // Assign MODERATOR role
            Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(modRole);
            break;

          default:
            // Assign default USER role for unrecognized role strings
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
      });
    }

    // Set the resolved roles to the user
    user.setRoles(roles);

    // Save the user to the database
    userRepository.save(user);

    // Return success response
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }


}