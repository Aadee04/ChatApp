package com.example.notifs.service;

import com.example.notifs.entity.GroupMembership;
import com.example.notifs.entity.UserToken;
import com.example.notifs.repository.GroupMembershipRepository;
import com.example.notifs.repository.UserTokenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserTokenService {
    private final UserTokenRepository userTokenRepository;
    private final GroupMembershipRepository groupMembershipRepository;
    private final Map<String, Set<String>> groupMembers = new ConcurrentHashMap<>();

    public void addUserToGroup(String groupId, String userId) {
        GroupMembership groupMembership = new GroupMembership();
        groupMembership.setGroupId(groupId);
        groupMembership.setUserId(userId);
        groupMembershipRepository.save(groupMembership);
    }


    public UserTokenService(UserTokenRepository userTokenRepository,
                            GroupMembershipRepository groupMembershipRepository) {
        this.userTokenRepository = userTokenRepository;
        this.groupMembershipRepository = groupMembershipRepository;
    }

    public void saveToken(String userId, String fcmToken) {
        UserToken userToken = new UserToken(userId, fcmToken);
        userTokenRepository.save(userToken);
    }

    public List<String> getTokensByGroupExcludingSender(String groupId, String senderId) {
        List<GroupMembership> members = groupMembershipRepository.findByGroupId(groupId);
        List<String> userIds = members.stream()
                .map(GroupMembership::getUserId)
                .filter(id -> !id.equals(senderId))
                .toList();

        return userTokenRepository.findAllByUserIdIn(userIds)
                .stream()
                .map(UserToken::getFcmToken)
                .toList();
    }
}
