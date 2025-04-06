package com.example.chatapp_v2.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chat_groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_name", nullable = false)
    private String name;


    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)//cascade->if group deleted, users in it deleted, orphanRemoval->can remove ppl from grp
    private Set<GroupMember> members = new HashSet<>();//hashset for unique members

    // Private constructor for builder
    private Group(GroupBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.members = builder.members;
    }

    // No-arg constructor for JPA
    public Group() {}

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<GroupMember> getMembers() {
        return members;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMembers(Set<GroupMember> members) {
        this.members = members;
    }

    // Static inner Builder class
    public static class GroupBuilder {
        private Long id;
        private String name;
        private Set<GroupMember> members = new HashSet<>();

        public GroupBuilder() {}

        public GroupBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public GroupBuilder name(String name) {
            this.name = name;
            return this;
        }

        public GroupBuilder members(Set<GroupMember> members) {
            this.members = members;
            return this;
        }

        public Group build() {
            return new Group(this);
        }
    }
}
