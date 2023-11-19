package com.example.apiFileManagementSystem.service;

import com.example.apiFileManagementSystem.config.ErrorCodeConstants;
import com.example.apiFileManagementSystem.config.ErrorMessages;
import com.example.apiFileManagementSystem.config.ResourceTypeConstants;
import com.example.apiFileManagementSystem.entity.AppFile;
import com.example.apiFileManagementSystem.entity.AppUser;
import com.example.apiFileManagementSystem.entity.GroupEntity;
import com.example.apiFileManagementSystem.entity.Partage;
import com.example.apiFileManagementSystem.exception.GroupNotFoundException;
import com.example.apiFileManagementSystem.exception.RessourceNotFoundException;
import com.example.apiFileManagementSystem.models.PartageDao;
import com.example.apiFileManagementSystem.repos.FileRepository;
import com.example.apiFileManagementSystem.repos.GroupRepository;
import com.example.apiFileManagementSystem.repos.PartageRepository;
import com.example.apiFileManagementSystem.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Service
public class PartageService {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private PartageRepository partageRepository;



    public PartageDao shareResource(PartageDao partageDao) throws GroupNotFoundException, FileNotFoundException {
        if(partageDao == null || partageDao.getSourceUser() == null || partageDao.getFileName() == null){
            return null;
        }
        if(partageDao.getTargetUser()==null && partageDao.getTargetGroup()!=null){
            GroupEntity groupEntity = groupRepository.findByGroupNom(partageDao.getTargetGroup())
                    .orElseThrow(() -> new RessourceNotFoundException(ErrorCodeConstants.GROUP_NOT_FOUND,ResourceTypeConstants.Group, partageDao.getTargetGroup(),ErrorMessages.GroupNotFoundMessage));

            AppUser appUser = userRepository.findByEmail(partageDao.getSourceUser()).orElseThrow(()->
                    new RessourceNotFoundException(ErrorCodeConstants.USER_NOT_FOUND,
                            ResourceTypeConstants.USER,
                            partageDao.getSourceUser(),
                            ErrorMessages.USERNotFoundMessage));
            AppFile appFile = fileRepository.findByName(partageDao.getFileName()).orElseThrow(()->new FileNotFoundException("il n'y a pas de fichier avec tel nom"));
            Partage partage = new Partage();
            partage.setSourceUser(appUser);
            partage.setTargetGroup(groupEntity);
            partage.setFile(appFile);
            partageRepository.save(partage);
            return PartageDao.builder().sourceUser(appUser.getEmail()).targetGroup(groupEntity.getGroupNom()).fileName(appFile.getName()).build();
        }
        if(partageDao.getTargetUser()!=null && partageDao.getTargetGroup()==null){
            AppUser targetUser =  userRepository.findByEmail(partageDao.getTargetUser()).orElseThrow(()->
                    new RessourceNotFoundException(ErrorCodeConstants.USER_NOT_FOUND,
                            ResourceTypeConstants.USER,
                            partageDao.getTargetUser(),
                            ErrorMessages.USERNotFoundMessage));
            AppUser sourceUser = userRepository.findByEmail(partageDao.getSourceUser()).orElseThrow(()->
                    new RessourceNotFoundException(ErrorCodeConstants.USER_NOT_FOUND,
                            ResourceTypeConstants.USER,
                            partageDao.getSourceUser(),
                            ErrorMessages.USERNotFoundMessage));
            AppFile appFile = fileRepository.findByName(partageDao.getFileName()).orElseThrow(()-> new RessourceNotFoundException(ErrorCodeConstants.FILE_NOT_FOUND,
                    ResourceTypeConstants.FILE, partageDao.getFileName(), ErrorMessages.FileNotFoundMessage));
            Partage partage = new Partage();
            partage.setSourceUser(sourceUser);
            partage.setTargetUser(targetUser);
            partage.setFile(appFile);
            Partage partageSaved = partageRepository.save(partage);
            return PartageDao.builder().sourceUser(sourceUser.getEmail()).targetUser(targetUser.getEmail()).fileName(appFile.getName()).build();
        }
        return null;
    }

    public PartageDao getPartageById(Long id) {
        Partage partage = partageRepository.findBySourceUserIdOrTargetUserId(id, id).orElseThrow(()->
                new RessourceNotFoundException(ErrorCodeConstants.USER_NOT_FOUND,
                        ResourceTypeConstants.USER,
                        id,
                        ErrorMessages.USERNotFoundMessage));
        return PartageDao.builder().sourceUser(partage.getSourceUser().getEmail()).targetUser((partage.getTargetUser()!=null) ? partage.getTargetUser().getEmail() : null)
                .targetGroup((partage.getTargetGroup()!=null) ? partage.getTargetGroup().getGroupNom() : null)
                .fileName(partage.getFile().getName())
                .build();
    }
             //   .parentName((folder.getParent() != null) ? folder.getParent().getNom() : null)

    public boolean deletePartage(Long partageId) {
        if(partageRepository.existsById(partageId)){
            partageRepository.deleteById(partageId);
            return true;
        }
        return false;
    }
}
