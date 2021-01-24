package com.synergy.projecttrackingsystem.service;

import com.synergy.projecttrackingsystem.entity.ProjectEntity;
import com.synergy.projecttrackingsystem.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ContactService contactService;

    public ProjectEntity addProject(ProjectEntity entity) {
        return projectRepository.save(entity);
    }

    public ProjectEntity getProjectByName(String projectName) {
        return projectRepository.findByProjectName(projectName);
    }

    public List<ProjectEntity> getAllProjects() {
        return projectRepository.findAll();
    }

    public ProjectEntity getProjectById(int id) {
        return projectRepository.findById(id).orElse(null);
    }

    public void deleteProject(int id) {
        projectRepository.deleteById(id);
    }

    public ProjectEntity saveProject(ProjectEntity entity) {
        return projectRepository.save(entity);
    }
}
