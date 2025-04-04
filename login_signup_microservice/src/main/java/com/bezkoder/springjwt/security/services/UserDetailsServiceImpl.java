package com.bezkoder.springjwt.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.UserRepository;

// Service class responsible for loading user details from the database
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UserRepository userRepository; // Repository for accessing user data

  @Override
  @Transactional // Ensures the operation is executed within a transactional context
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // Retrieve user from the database by username, or throw an exception if not found
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    // Convert User entity into UserDetailsImpl and return it for authentication
    return UserDetailsImpl.build(user);
  }
}

