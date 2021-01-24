package com.synergy.projecttrackingsystem.repository;

import com.synergy.projecttrackingsystem.entity.ContactEntity;
import com.synergy.projecttrackingsystem.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Integer> {
    List<ContactEntity> findAllByProjectEntity(ProjectEntity entity);
}
