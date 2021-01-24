package com.synergy.projecttrackingsystem.service;

import com.synergy.projecttrackingsystem.entity.ContactEntity;
import com.synergy.projecttrackingsystem.entity.ProjectEntity;
import com.synergy.projecttrackingsystem.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    ProjectService projectService;

    public ContactEntity addContact(ContactEntity entity) {
        return contactRepository.save(entity);
    }

    public List<ContactEntity> getAllContactsByProjectId(ProjectEntity entity) {
        return contactRepository.findAllByProjectEntity(entity);
    }

    public void deleteContactByProjectEntity(int entityId) {
        ProjectEntity projectEntity = projectService.getProjectById(entityId);
        List<ContactEntity> contactEntityList = getAllContactsByProjectId(projectEntity);
        for (ContactEntity contactEntity : contactEntityList) {
            contactRepository.deleteById(contactEntity.getContactId());
        }
    }

    public void deleteContactById(Integer contactId) {
        contactRepository.deleteById(contactId);
    }
}
