package com.example.chatapp_v2.repository;

import com.example.chatapp_v2.entity.GroupMember;
import com.example.chatapp_v2.entity.GroupMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {
    List<GroupMember> findByIdGroupId(Long groupId);
    List<GroupMember> findByIdUserId(String userId);
}
