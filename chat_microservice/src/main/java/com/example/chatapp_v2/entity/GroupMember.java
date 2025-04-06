package com.example.chatapp_v2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "group_members")
public class GroupMember {

    //below is for creating a composite primary key (group_id,user_id)
    @EmbeddedId
    private GroupMemberId id;

    @ManyToOne//This tells JPA that many GroupMembers can belong to a single Group.
    @MapsId("groupId")//@MapsId links this field (group) to the groupId in the primary key (GroupMemberId).
    @JoinColumn(name = "group_id")//This tells Hibernate to use the group_id column in the database as the foreign key.
    private Group group;


    public GroupMember() {}

    private GroupMember(GroupMemberBuilder builder) {
        this.id = builder.id;
        this.group = builder.group;
    }

    public GroupMemberId getId() {
        return id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public static class GroupMemberBuilder {
        private GroupMemberId id;
        private Group group;

        public GroupMemberBuilder() {}

        public GroupMemberBuilder id(GroupMemberId id) {
            this.id = id;
            return this;
        }

        public GroupMemberBuilder group(Group group) {
            this.group = group;
            return this;
        }

        public GroupMember build() {
            return new GroupMember(this);
        }
    }
}
