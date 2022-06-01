package com.example.test.group.service;

import com.example.test.group.entity.Group;

import java.util.Optional;

public interface GroupService {
    Group findByName(String name);
}
