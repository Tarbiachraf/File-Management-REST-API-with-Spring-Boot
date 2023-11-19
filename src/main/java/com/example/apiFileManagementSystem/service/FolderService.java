package com.example.apiFileManagementSystem.service;

import com.example.apiFileManagementSystem.config.ErrorCodeConstants;
import com.example.apiFileManagementSystem.config.ErrorMessages;
import com.example.apiFileManagementSystem.config.ResourceTypeConstants;
import com.example.apiFileManagementSystem.entity.AppUser;
import com.example.apiFileManagementSystem.entity.Folder;
import com.example.apiFileManagementSystem.exception.RessourceNotFoundException;
import com.example.apiFileManagementSystem.exception.UnauthorizedException;
import com.example.apiFileManagementSystem.models.FolderRequest;
import com.example.apiFileManagementSystem.models.FolderResp;
import com.example.apiFileManagementSystem.repos.FolderRepository;
import com.example.apiFileManagementSystem.repos.UserRepository;
import com.example.apiFileManagementSystem.service.activityService.FolderActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FolderService {

    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FolderActivityService folderActivityService;

    public FolderResp createdFolder(FolderRequest folder, Authentication authentication){
        AppUser appUser = userRepository.findByEmail(authentication.getName()).orElseThrow(()->{throw new RessourceNotFoundException(ErrorCodeConstants.USER_NOT_FOUND,
                ResourceTypeConstants.USER,authentication.getName(),
                ErrorMessages.USERNotFoundMessage);});
        Folder newFolder = Folder.builder()
                .nom(folder.getName())
                .dateCreation(new Date())
                .parent(folderRepository.findById(folder.getParentId()).
                        orElseThrow(()->{
                            throw new RessourceNotFoundException(ErrorCodeConstants.FOLDER_NOT_FOUND,
                                    ResourceTypeConstants.FOLDER
                                    , folder.getParentId(),
                                    ErrorMessages.FolderNotFoundMessage);
                        }))
                .user(appUser)
                .build();

        Folder createdFolder = folderRepository.save(newFolder);
        folderActivityService.createFolderActivity(appUser,newFolder);
        String parentNom = (createdFolder.getParent() != null) ? createdFolder.getParent().getNom() : null;

        FolderResp folderResp = new FolderResp(createdFolder.getId(), createdFolder.getNom(), parentNom, authentication.getName(), createdFolder.getDateCreation().toString());
        return folderResp;
    }

    public FolderResp createdFolder(String name, Authentication authentication) {
        AppUser appUser = userRepository.findByEmail(authentication.getName()).orElseThrow(()->{throw new RessourceNotFoundException(ErrorCodeConstants.USER_NOT_FOUND,
                ResourceTypeConstants.USER,authentication.getName(),
                ErrorMessages.USERNotFoundMessage);});

        Folder newFolder = Folder.builder()
                .nom(name)
                .dateCreation(new Date())
                .user(appUser)
                .build();

        Folder createdFolder = folderRepository.save(newFolder);



        FolderResp folderResp = new FolderResp(createdFolder.getId(), createdFolder.getNom(), null, authentication.getName(), createdFolder.getDateCreation().toString());
        return folderResp;
    }

    public boolean deleteFolder(String name) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Folder folder = folderRepository.findByNom(name).orElseThrow(()->{
            throw new RessourceNotFoundException(ErrorCodeConstants.FOLDER_NOT_FOUND,
                    ResourceTypeConstants.FOLDER
                    ,name,
                    ErrorMessages.FolderNotFoundMessage);
        });
        if(!folder.getUser().getEmail().equals(email)){
            throw new UnauthorizedException(ErrorCodeConstants.UNAUTHORIZED,ResourceTypeConstants.FOLDER,folder.getId(),ErrorMessages.FolderNotFoundMessage);
        }
            folderRepository.delete(folder);
            folderActivityService.FolderDelete(folder.getUser(),folder);
            return true;
    }


    public List<FolderResp> getAllFolders(Authentication authentication) {
        List<Folder> folders = folderRepository.findAll();
        return folders.stream().map(folder -> FolderResp.builder().id(folder.getId()).name(folder.getNom()).owner(folder.getUser().getEmail()).dateCreation(folder.getDateCreation().toString())
                .parentName(folder.getParent().getNom()).build()).filter(folderResp -> folderResp.getOwner().equals(authentication.getName())).collect(Collectors.toList());
    }

    public FolderResp updateFolder(Long folderId, FolderRequest folderRequest, Authentication authentication)  {
        Folder folder = folderRepository.findById(folderId).
                orElseThrow(()->new RessourceNotFoundException(ErrorCodeConstants.FOLDER_NOT_FOUND, ResourceTypeConstants.FOLDER,folderId,ErrorMessages.FolderNotFoundMessage));
        if(!folder.getUser().getEmail().equals(authentication.getName()))
            throw new UnauthorizedException(ErrorCodeConstants.UNAUTHORIZED,ResourceTypeConstants.FOLDER,folder.getId(),ErrorMessages.FolderNotFoundMessage);
        folder.setNom(folderRequest.getName());
        if(folderRequest.getParentId()!=null)
            folder.setParent(folderRepository.findById(folderRequest.getParentId()).orElseThrow(()->{throw new RessourceNotFoundException(ErrorCodeConstants.FOLDER_NOT_FOUND,
                    ResourceTypeConstants.FOLDER,
                    folderRequest.getParentId(),
                    ErrorMessages.FolderNotFoundMessage);}));

        folderRepository.save(folder);
        folderActivityService.UpdateFolder(folder.getUser(),folder);
        return FolderResp.builder()
                .id(folder.getId())
                .name(folder.getNom())
                .owner(folder.getUser().getEmail())
                .dateCreation(folder.getDateCreation().toString())
             //   .parentName(folder.getParent().getNom())
                .parentName((folder.getParent() != null) ? folder.getParent().getNom() : null)
                .build();

    }

}
