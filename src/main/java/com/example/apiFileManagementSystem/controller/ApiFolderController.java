package com.example.apiFileManagementSystem.controller;

import com.example.apiFileManagementSystem.models.FolderRequest;
import com.example.apiFileManagementSystem.models.FolderResp;
import com.example.apiFileManagementSystem.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/folders")
public class ApiFolderController {

    @Autowired
    private FolderService folderService;
    @PostMapping("/createSub")
    public ResponseEntity<FolderResp> createSubFolder(@RequestBody FolderRequest folderRequest, Authentication authentication){
        FolderResp createdFolder = folderService.createdFolder(folderRequest,authentication);
        //if(createdFolder!=null)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFolder);
        //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    @PostMapping("/create")
    public ResponseEntity<FolderResp> createFolder(@RequestParam String name,Authentication authentication){
        FolderResp createdFolder = folderService.createdFolder(name,authentication);
        //if(createdFolder!=null)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFolder);
        //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

    }
    @DeleteMapping("/deleteFolder")
    public ResponseEntity<String> deleteFolder(@RequestParam String name){
        if(folderService.deleteFolder(name)){
            return ResponseEntity.ok("la suppression du folder a été passé avec succés");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("la suppression a échoué ");
    }
    @GetMapping
    public ResponseEntity<List<FolderResp>> getFolders(Authentication authentication){
        List<FolderResp> folderResps = folderService.getAllFolders(authentication);
        return ResponseEntity.ok(folderResps);
    }
    @PutMapping("/rename/{folderId}")
    public ResponseEntity<?> updateFolder(@PathVariable Long folderId, @RequestBody FolderRequest folderRequest, Authentication authentication) {
        if(folderRequest!=null && authentication != null && folderRequest.getName() != null ){
            //try {
                FolderResp folderResp = folderService.updateFolder(folderId,folderRequest,authentication);
                if(folderResp!=null){
                    return ResponseEntity.status(HttpStatus.OK).body(folderResp);
                }
           /* }catch (FolderNotFoundException folderNotFoundException){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("dossier non trouvé : "+folderNotFoundException.getMessage());
            }*/
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);


    }

}
