package com.bezkoder.springjwt.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bezkoder.springjwt.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
// Implementation of UserDetails, which represents authenticated user details
public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;
  private String username;
  private String email;

  @JsonIgnore // Prevents password from being serialized in JSON responses
  private String password;

  private Collection<? extends GrantedAuthority> authorities;

  // Constructor to initialize user details
  public UserDetailsImpl(Long id, String username, String email, String password,
                         Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  // Static method to create UserDetailsImpl from a User entity
  public static UserDetailsImpl build(User user) {
    List<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName().name())) // Convert roles to GrantedAuthority
            .collect(Collectors.toList());

    return new UserDetailsImpl(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities; // Returns the roles/authorities of the user
  }

  public Long getId() {
    return id; // Returns the user ID
  }

  public String getEmail() {
    return email; // Returns the user email
  }

  @Override
  public String getPassword() {
    return password; // Returns the user's password
  }

  @Override
  public String getUsername() {
    return username; // Returns the username
  }

  @Override
  public boolean isAccountNonExpired() {
    return true; // Indicates that the account is not expired
  }

  @Override
  public boolean isAccountNonLocked() {
    return true; // Indicates that the account is not locked
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true; // Indicates that the credentials are not expired
  }

  @Override
  public boolean isEnabled() {
    return true; // Indicates that the user is enabled
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id); // Compares users based on ID
  }
}