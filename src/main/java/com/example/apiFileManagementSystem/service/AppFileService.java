package com.example.apiFileManagementSystem.service;

import com.example.apiFileManagementSystem.config.ErrorCodeConstants;
import com.example.apiFileManagementSystem.config.ErrorMessages;
import com.example.apiFileManagementSystem.config.ResourceTypeConstants;
import com.example.apiFileManagementSystem.entity.AppFile;
import com.example.apiFileManagementSystem.entity.AppUser;
import com.example.apiFileManagementSystem.entity.Folder;
import com.example.apiFileManagementSystem.exception.FileStorageException;
import com.example.apiFileManagementSystem.exception.RessourceNotFoundException;
import com.example.apiFileManagementSystem.exception.UnauthorizedException;
import com.example.apiFileManagementSystem.repos.FileRepository;
import com.example.apiFileManagementSystem.repos.FolderRepository;
import com.example.apiFileManagementSystem.repos.UserRepository;
import com.example.apiFileManagementSystem.service.activityService.FileActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AppFileService {
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private FileActivityService fileActivityService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FolderRepository folderRepository;

    private static final String CHEMIN_FICHIERS = "C:/Users/dell/Desktop/fichiers";

    public String uploadFile(Long folderId, MultipartFile file, Authentication authentication) {
        try {
            // Vérifiez si le fichier est vide
            if (file.isEmpty()) {
                throw new FileStorageException("Le fichier est vide.");
            }

            // Utilisez le service de stockage des fichiers pour enregistrer le fichier
            String fileName = fileStorageService.saveFile(file);

            Folder folder = folderRepository.findById(folderId).orElseThrow(null);

            AppFile appFile = AppFile.builder()
                    .name(file.getOriginalFilename())
                    .dossier(folder)
                    .fileType(file.getContentType())
                    .size(file.getSize())
                    .build();
            fileRepository.save(appFile);
            AppUser appUser = userRepository.findByEmail(authentication.getName()).get();

            fileActivityService.createFileUploadActivity(appUser,appFile);



            // Vous pouvez effectuer d'autres opérations, comme enregistrer le nom du fichier dans la base de données, si nécessaire

            return fileName;
        } catch (IOException e) {
            throw new FileStorageException("Erreur lors de l'enregistrement du fichier.", e);
        }
    }


    public boolean deleteFileByName(String filename, Authentication authentication) throws IOException {
        AppFile appFile = fileRepository.findByName(filename).orElseThrow(()-> new RessourceNotFoundException(ErrorCodeConstants.FILE_NOT_FOUND,
                ResourceTypeConstants.FILE,filename, ErrorMessages.FileNotFoundMessage));
        AppUser appUser =userRepository.findByEmail(authentication.getName()).orElseThrow(()->
                new RessourceNotFoundException(ErrorCodeConstants.USER_NOT_FOUND,
                        ResourceTypeConstants.USER,
                        authentication.getName(),
                        ErrorMessages.USERNotFoundMessage));
        if(!appFile.getDossier().getUser().getEmail().equals(authentication.getName()))
            throw new UnauthorizedException(ErrorCodeConstants.UNAUTHORIZED,ResourceTypeConstants.FILE,appFile.getId(),ErrorMessages.FolderNotFoundMessage);
        if (fileStorageService.deleteFile(appFile.getName())){

            fileRepository.delete(appFile);
            fileActivityService.fileDeleteActivity(appUser,appFile);
            return true;
        }
        return false;
    }

    public AppFile getFileById(Long fileId){
        AppFile file = fileRepository.findById(fileId).orElseThrow(()->new RessourceNotFoundException(ErrorCodeConstants.FILE_NOT_FOUND,
                ErrorCodeConstants.FILE_NOT_FOUND,fileId,
                ErrorMessages.FileNotFoundMessage));
        fileActivityService.fileDownloadActivity(file.getDossier().getUser(),file);
        return file;
    }
////matnssach
    public Resource telechargerFichier(Long id) throws FileNotFoundException {
        AppFile appFile = fileRepository.findById(id).orElseThrow(()->new RessourceNotFoundException(ErrorCodeConstants.FILE_NOT_FOUND,
                ErrorCodeConstants.FILE_NOT_FOUND,id,
                ErrorMessages.FileNotFoundMessage));
        //if (appFile != null) {
        //    try {
                Path fichierPath = Paths.get(CHEMIN_FICHIERS, appFile.getName());
                Resource resource = new FileSystemResource(fichierPath);

                return resource;
        //    } catch (Exception e) {
        //        throw new FileNotFoundException("Le fichier n'a pas pu être trouvé.");
        //    }
        //} else {
        //    throw new FileNotFoundException("Fichier non trouvé.");
        //}
    }
}
