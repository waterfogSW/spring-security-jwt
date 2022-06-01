package com.example.test.group.entity;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.*;

@Entity
@Table(name = "group_permission")
public class GroupPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(optional = false)
    @JoinColumn(name = "permission_id")
    private Permission permission;

    public Permission getPermission() {
        return permission;
    }
}
