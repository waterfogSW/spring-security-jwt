package com.example.test.group.service;

import com.example.test.group.entity.Group;
import com.example.test.group.repository.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
public class DefaultGroupService implements GroupService {

    private final GroupRepository groupRepository;

    public DefaultGroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Group findByName(String name) {
        Assert.isTrue(isNotEmpty(name), "Name must be provided");

        return groupRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Cannot found group for " + name));
    }
}
