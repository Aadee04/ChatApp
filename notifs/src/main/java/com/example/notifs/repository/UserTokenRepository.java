package com.example.notifs.repository;

import com.example.notifs.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, String> {
    List<UserToken> findAllByUserIdIn(List<String> userIdsInGroup);
}

