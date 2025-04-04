package com.bezkoder.springjwt.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entity representing a user in the system.
 *
 * This class defines the attributes of a user and their relationship
 * with roles. It is mapped to the "users" table in the database.
 */
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
  /**
   * Unique identifier for the user.
   * Auto-generated using identity strategy.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * The username of the user.
   * Cannot be blank and must be unique across all users.
   */
  @NotBlank
  private String username;

  /**
   * The email address of the user.
   * Must be a valid email format, cannot be blank, and must be unique.
   */
  @NotBlank
  @Email
  private String email;

  /**
   * The hashed password of the user.
   * Cannot be blank. Should never store plain text passwords.
   */
  @NotBlank
  private String password;

  /**
   * The set of roles assigned to this user.
   * Implements a many-to-many relationship with the Role entity through
   * the "user_roles" join table. Uses lazy loading for performance optimization.
   */
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  /**
   * Default constructor required by JPA.
   */
  public User() {
  }

  /**
   * Constructs a new User with the specified username, email, and password.
   *
   * @param username The unique username for the user
   * @param email The email address of the user
   * @param password The password (should be encrypted before storage)
   */
  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  /**
   * Returns the user's ID.
   *
   * @return The user's unique identifier
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the user's ID.
   *
   * @param id The unique identifier to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Returns the user's username.
   *
   * @return The username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the user's username.
   *
   * @param username The username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Returns the user's email address.
   *
   * @return The email address
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the user's email address.
   *
   * @param email The email address to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Returns the user's password.
   * Note: In production environments, consider if directly returning
   * the password (even if hashed) is necessary.
   *
   * @return The hashed password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the user's password.
   * Note: The password should be encrypted before setting.
   *
   * @param password The hashed password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Returns the set of roles assigned to this user.
   *
   * @return Set of roles
   */
  public Set<Role> getRoles() {
    return roles;
  }

  /**
   * Sets the roles for this user.
   *
   * @param roles The set of roles to assign to this user
   */
  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
}