package com.example.notifs.repository;

import com.example.notifs.entity.GroupMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMembershipRepository extends JpaRepository<GroupMembership, Long> {
    List<GroupMembership> findByGroupId(String groupId);
}
