package com.bezkoder.springjwt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a user role in the system.
 * This class maps to the "roles" table in the database and is used to
 * store different authorization roles that can be assigned to users.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {
  /**
   * Unique identifier for the role.
   * Auto-generated using identity strategy.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * The name of the role stored as a string representation of the ERole enum.
   * Limited to 20 characters in the database.
   */
  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private ERole name;

//  /**
//   * Default constructor required by JPA.
//   */
//  public Role() {
//
//  }
//
//  /**
//   * Constructs a new Role with the specified role name.
//   *
//   * @param name The ERole enum value representing the role
//   */
  public Role(ERole name) {
    this.name = name;
  }

  /**
   * Returns the role's ID.
   *
   * @return The role's unique identifier
   */
  public Integer getId() {
    return id;
  }

  /**
   * Sets the role's ID.
   *
   * @param id The unique identifier to set
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * Returns the role's name.
   *
   * @return The ERole enum value of this role
   */
  public ERole getName() {
    return name;
  }

  /**
   * Sets the role's name.
   *
   * @param name The ERole enum value to set
   */
  public void setName(ERole name) {
    this.name = name;
  }
}