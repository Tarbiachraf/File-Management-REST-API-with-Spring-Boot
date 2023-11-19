package com.example.apiFileManagementSystem.controller;

import com.example.apiFileManagementSystem.entity.AppFile;
import com.example.apiFileManagementSystem.exception.FileStorageException;
import com.example.apiFileManagementSystem.models.FolderResp;
import com.example.apiFileManagementSystem.models.FolderResponse;
import com.example.apiFileManagementSystem.repos.FileRepository;
import com.example.apiFileManagementSystem.repos.UserRepository;
import com.example.apiFileManagementSystem.service.AppFileService;
import com.example.apiFileManagementSystem.service.FileStorageService;
import com.example.apiFileManagementSystem.service.activityService.FileActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
public class AppFileController {

    @Autowired
    private FileStorageService fileStorageService; // Vous devez créer ce service pour gérer le stockage des fichiers
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private AppFileService fileService;


    /*@PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Vérifiez si le fichier est vide
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Le fichier est vide.");
            }

            // Utilisez le service de stockage des fichiers pour enregistrer le fichier
            String fileName = fileStorageService.saveFile(file);

            AppFile appFile = AppFile.builder()
                    .name(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .size(file.getSize())
                    .build();
            fileRepository.save(appFile);

            // Vous pouvez effectuer d'autres opérations, comme enregistrer le nom du fichier dans la base de données, si nécessaire

            return ResponseEntity.ok("Fichier " + fileName + " téléchargé avec succès.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erreur lors de l'enregistrement du fichier.");
        }
    }*/

    /*@GetMapping("/{id}")
    public ResponseEntity<FolderResponse> getApiFile(@PathVariable Long id){
        FolderResp
    }*/
    @PostMapping("/upload/{folderId}")
    public ResponseEntity<String> uploadFile(@PathVariable Long folderId,@RequestParam("file") MultipartFile file, Authentication authentication) {
       // try {
            String fileName = fileService.uploadFile(folderId,file,authentication);
            //fileActivityService.createFileUploadActivity(authentication.getName(),);
            return ResponseEntity.ok("Fichier " + fileName + " téléchargé avec succès.");
        //} catch (FileStorageException e) {
         //   return ResponseEntity.badRequest().body(e.getMessage());
       // }
    }
    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<String> deleteFile(@PathVariable String filename,Authentication authentication) throws IOException {
        fileService.deleteFileByName(filename, authentication);
        return ResponseEntity.ok("le fichier est supprimé avec succés");
            //}
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fichier non trouvé : " + filename);

    }
    /*@PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
        if(file!=null)
            return ResponseEntity.ok("hhhhhhhhhhhhhhhhh");
        return ResponseEntity.ok("oooooooooooofffff");
    }*/

    @GetMapping("/telecharger-fichier/{id}")
    public ResponseEntity<Resource> telechargerFichier(@PathVariable Long id) throws IOException {
     //   try {
            Resource resource = fileService.telechargerFichier(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(resource.getFilename()).build());

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .body(resource);
      //  } catch (FileNotFoundException e) {
        //    return ResponseEntity.notFound().build();
      //  } catch (IOException e) {
        //    throw new RuntimeException(e);
       // }
    }




    /*@GetMapping("/telecharger-fichier/{id}")
    public ResponseEntity<Resource> telechargerFichier(@PathVariable Long id) throws FileNotFoundException {
        String cheminDuFichier = "C:/Users/dell/Desktop/fichiers";

        AppFile appFile = fileService.getFileById(id);

        if (appFile != null) {
            try {
                Path fichierPath = Paths.get(cheminDuFichier, appFile.getName());
                Resource resource = new FileSystemResource(fichierPath);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDisposition(ContentDisposition.builder("attachment").filename(appFile.getName()).build());

                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(resource.contentLength())
                        .body(resource);

            } catch (Exception e) {
                return ResponseEntity.notFound().build();
            }
        } else {
            // Gérez le cas où l'ID de fichier n'a pas été trouvé.
            return ResponseEntity.notFound().build();
        }
    }*/


}
