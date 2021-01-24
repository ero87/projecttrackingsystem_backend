package com.synergy.projecttrackingsystem.controller;

import com.synergy.projecttrackingsystem.entity.ContactEntity;
import com.synergy.projecttrackingsystem.entity.ProjectEntity;
import com.synergy.projecttrackingsystem.service.ContactService;
import com.synergy.projecttrackingsystem.service.ProjectService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    ContactService contactService;

    @PostMapping("/add/project")
    public String addComment(@RequestBody Map<String, Object> payload) {
        Object body = payload.get("contactentityes");
        List<Map<String, String>> contactsList = (List<Map<String, String>>) body;
        ProjectEntity projectEntity = new ProjectEntity();
        String status = (String) payload.get("status");
        if (status.isEmpty() || status.isBlank()) {
            return "Incorrect status";
        }
        projectEntity.setStatus(status);
        String projectName = (String) payload.get("projectName");
        if (projectName.isEmpty() || projectName.isBlank()) {
            return "Incorrect project name";
        }
        projectEntity.setProjectName(projectName);
        ProjectEntity project = projectService.addProject(projectEntity);
        return saveProject(contactsList, project);
    }

    private String saveProject(List<Map<String, String>> contactsList, ProjectEntity project) {
        for (Map<String, String> contact : contactsList) {
            ContactEntity contactEntity = new ContactEntity();
            String contactFullName = contact.get("contactFullName");
            if (contactFullName.isEmpty() || contactFullName.isBlank()) {
                return "Incorrect contact name";
            }
            contactEntity.setContactFullName(contact.get("contactFullName"));
            String email = contact.get("email");
            if (!EmailValidator.getInstance().isValid(email)) {
                return "Invalid email";
            }
            contactEntity.setEmail(contact.get("email"));
            contactEntity.setPhone(contact.get("phone"));
            contactEntity.setProjectEntity(project);
            contactService.addContact(contactEntity);
        }
        return "Successfully saved";
    }

    @GetMapping("/getAllProjects")
    public List<ProjectEntity> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/projects/{projectName}")
    public List<ContactEntity> getAllData(@PathVariable("projectName") String projectName) throws Exception {
        List<ContactEntity> allContactsByProjectId = contactService.getAllContactsByProjectId(
                projectService.getProjectByName(projectName));
        return allContactsByProjectId;
    }

    @DeleteMapping("/delete/project/{projectId}")
    public String deleteProject(@PathVariable("projectId") Integer projectId) {
        try {
            contactService.deleteContactByProjectEntity(projectId);
            projectService.deleteProject(projectId);
            return "The project is successfully deleted!!!";
        } catch (EmptyResultDataAccessException e) {
            return "Incorrect project id";
        }
    }

    @GetMapping("/get/contacts/{contactId}")
    public List<ContactEntity> getAllData(@PathVariable("contactId") Integer contactId) throws Exception {
        List<ContactEntity> allContactsByProjectId = contactService.getAllContactsByProjectId(
                projectService.getProjectById(contactId));
        return allContactsByProjectId;
    }

    @DeleteMapping("/delete/contact/{contactId}")
    public String deleteContact(@PathVariable("contactId") Integer contactId) {
        try {
            contactService.deleteContactById(contactId);
            return "The contact is successfully deleted!!!";
        } catch (EmptyResultDataAccessException e) {
            return "Incorrect contact id";
        }
    }

    @PatchMapping("update/data")
    public String updateData(@RequestBody Map<String, Object> payload) {
        ProjectEntity projectEntity = new ProjectEntity();
        String id = payload.get("projectId").toString();
        projectEntity.setProjectId(Integer.parseInt(id));
        String projectName = (String) payload.get("projectName");
        if (projectName.isEmpty() || projectName.isBlank()) {
            return "Incorrect project name";
        }
        projectEntity.setProjectName(projectName);
        String status = (String) payload.get("status");
        if (status.isEmpty() || status.isBlank()) {
            return "Incorrect status";
        }
        projectEntity.setStatus(status);
        ProjectEntity entityWithId = projectService.saveProject(projectEntity);
        Object contacts = payload.get("contacts");
        List<Map<String, Object>> contactsList = (List<Map<String, Object>>) contacts;
        contactService.deleteContactByProjectEntity(Integer.parseInt(id));
        return saveContact(entityWithId, contactsList);
    }

    private String saveContact(ProjectEntity entityWithId, List<Map<String, Object>> contactsList) {
        for (Map<String, Object> stringStringMap : contactsList) {
            ContactEntity contactEntity = new ContactEntity();
            contactEntity.setProjectEntity(entityWithId);
            String contactFullName = (String) stringStringMap.get("contactFullName");
            if (contactFullName.isEmpty() || contactFullName.isBlank()) {
                return "Incorrect contact name";
            }
            contactEntity.setContactFullName((String) stringStringMap.get("contactFullName"));
            contactEntity.setPhone((String) stringStringMap.get("phone"));
            String email = (String) stringStringMap.get("email");
            if (!EmailValidator.getInstance().isValid(email)) {
                return "Invalid email";
            }
            contactEntity.setEmail(email);
            if (stringStringMap.get("contactId") != null) {
                String contactId = stringStringMap.get("contactId").toString();
                contactEntity.setContactId(Integer.parseInt(contactId));
            }
            contactService.addContact(contactEntity);
        }
        return "Contact successfully saved";
    }
}

