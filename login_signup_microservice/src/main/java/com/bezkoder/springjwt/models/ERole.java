package com.bezkoder.springjwt.models;

/**
 * Enumeration of user roles for authorization in the application.
 * This enum defines the possible roles that can be assigned to users
 * for role-based access control (RBAC).
 */
public enum ERole {
  /**
   * Standard user role with basic access permissions.
   */
  ROLE_USER,

  /**
   * Moderator role with intermediate access permissions.
   * Typically has more privileges than a standard user but fewer than an admin.
   */
  ROLE_MODERATOR,

  /**
   * Administrator role with highest level of access permissions.
   * Users with this role typically have full control over the application.
   */
  ROLE_ADMIN
}