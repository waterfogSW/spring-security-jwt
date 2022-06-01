package com.example.test.group.repository;

import com.example.test.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("select g from Group g join fetch g.permissions where g.name = :name")
    Optional<Group> findByName(String name);
}
