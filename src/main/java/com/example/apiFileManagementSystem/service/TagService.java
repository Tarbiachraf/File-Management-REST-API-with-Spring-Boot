package com.example.apiFileManagementSystem.service;


import com.example.apiFileManagementSystem.config.ErrorCodeConstants;
import com.example.apiFileManagementSystem.config.ErrorMessages;
import com.example.apiFileManagementSystem.config.ResourceTypeConstants;
import com.example.apiFileManagementSystem.entity.AppFile;
import com.example.apiFileManagementSystem.entity.Tag;
import com.example.apiFileManagementSystem.exception.RessourceNotFoundException;
import com.example.apiFileManagementSystem.exception.UnauthorizedException;
import com.example.apiFileManagementSystem.repos.FileRepository;
import com.example.apiFileManagementSystem.repos.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

// TagService
@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private AppFileService appFileService;

    public Tag addTag(Tag tag) {
        // Implémentez la logique pour ajouter un tag dans la base de données.
        return tagRepository.save(tag);
    }

    public Tag getTag(Long tagId) {
        return tagRepository.findById(tagId).orElseThrow(()->new RessourceNotFoundException(ErrorCodeConstants.TAG_NOT_FOUND,
                ResourceTypeConstants.TAG,tagId, ErrorMessages.TagNotFoundMessage));
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public boolean deleteTag(Long tagId) {
        if (tagRepository.existsById(tagId)) {
            tagRepository.deleteById(tagId);
            return true;
        }
        throw new RessourceNotFoundException(ErrorCodeConstants.TAG_NOT_FOUND,
                ResourceTypeConstants.TAG,tagId, ErrorMessages.TagNotFoundMessage);
    }

 /*   public boolean updateTag(Long tagId, Tag updatedTag) {
        Tag existingTag = tagRepository.findById(tagId).orElse(null);
        if (existingTag != null) {
            // Mettez à jour les attributs du tag existant avec les valeurs de updatedTag.
            // Assurez-vous de gérer les modifications appropriées.
            // Puis sauvegardez le tag mis à jour dans la base de données.
            existingTag.setTagNom(updatedTag.getTagNom());
            return tagRepository.save(existingTag);
        }
        return null;
    }*/

    public boolean addTagToFile(Long tagId, Long fileId, Authentication authentication){
        AppFile file = appFileService.getFileById(fileId);
        if (!file.getDossier().getUser().getEmail().equals(authentication.getName())) {
            throw new UnauthorizedException(ErrorCodeConstants.UNAUTHORIZED,ResourceTypeConstants.FILE,fileId,ErrorMessages.FileNotFoundMessage);
        }
        Tag tag = tagRepository.findById(tagId).
                orElseThrow(()->new RessourceNotFoundException(ErrorCodeConstants.TAG_NOT_FOUND,ResourceTypeConstants.TAG,tagId,ErrorMessages.TagNotFoundMessage));
        //if(tag != null && file != null){
            file.getTags().add(tag);
            fileRepository.save(file);
            return true;
        //}

    }

    public boolean removeTagFromFile(Long tagId, Long fileId, Authentication authentication) {
        AppFile file = appFileService.getFileById(fileId);
        if (!file.getDossier().getUser().getEmail().equals(authentication.getName())) {
            throw new UnauthorizedException(ErrorCodeConstants.UNAUTHORIZED,ResourceTypeConstants.FILE,fileId,ErrorMessages.FileNotFoundMessage);
        }
        Tag tag = tagRepository.findById(tagId).get();
      //  if(tag != null && appFile != null){
            file.getTags().remove(tag);
            fileRepository.save(file);
            return true;
        //}
       // return false;
    }
    public List<AppFile> getFilesWithTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId).
                orElseThrow(()->new RessourceNotFoundException(ErrorCodeConstants.TAG_NOT_FOUND,ResourceTypeConstants.TAG,tagId,ErrorMessages.TagNotFoundMessage));

       // if (tagOptional.isPresent()) {
       //     Tag tag = tagOptional.get();
            return tag.getAppFiles();


        //return Collections.EMPTY_LIST;
    }
    public boolean updateTag(Long tagId, Tag updatedTag) {
        Tag tag = tagRepository.findById(tagId).
                orElseThrow(()->new RessourceNotFoundException(ErrorCodeConstants.TAG_NOT_FOUND,ResourceTypeConstants.TAG,tagId,ErrorMessages.TagNotFoundMessage));

        tag.setTagNom(updatedTag.getTagNom());

            tagRepository.save(tag);
            return true;
        }


}
