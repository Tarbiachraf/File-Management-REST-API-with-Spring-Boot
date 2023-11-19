package com.example.apiFileManagementSystem.controller;


import com.example.apiFileManagementSystem.entity.AppFile;
import com.example.apiFileManagementSystem.entity.Tag;
import com.example.apiFileManagementSystem.models.FileDao;
import com.example.apiFileManagementSystem.models.TagResponse;
import com.example.apiFileManagementSystem.service.AppFileService;
import com.example.apiFileManagementSystem.service.AppUserService;
import com.example.apiFileManagementSystem.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

// TagController
@RestController
@RequestMapping("/api/tags")
public class TagContr{

    @Autowired
    private TagService tagService;
    @Autowired
    private AppFileService appFileService;
    @Autowired
    private AppUserService appUserService;

    @PostMapping("/add")
    public ResponseEntity<Tag> addTag(@RequestBody Tag tag) {
        Tag addedTag = tagService.addTag(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedTag);
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<TagResponse> getTag(@PathVariable Long tagId) {
        Tag tag = tagService.getTag(tagId);
        //if (tag != null) {

        return ResponseEntity.status(HttpStatus.OK).body(TagResponse.builder().tagNom(tag.getTagNom()).id(tag.getId()).build());
       // } else {
       //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
       // }
    }

    @GetMapping("/all")
    public ResponseEntity<List<TagResponse>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        return ResponseEntity.status(HttpStatus.OK).body(tags.stream().map(tag ->TagResponse.builder().tagNom(tag.getTagNom()).id(tag.getId()).build()).collect(Collectors.toList()));
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<String> deleteTag(@PathVariable Long tagId) {
       // boolean deleted =
                tagService.deleteTag(tagId);
        //if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Tag supprimé avec succès.");
        //} else {
        //    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag non trouvé.");
       // }
    }

    /*@PutMapping("/{tagId}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long tagId, @RequestBody Tag updatedTag) {
        Tag tag = tagService.updateTag(tagId, updatedTag);
        if (tag != null) {
            return ResponseEntity.status(HttpStatus.OK).body(tag);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }*/
    @PostMapping("/{tagId}/add-to-file/{fileId}")
    public ResponseEntity<String> addTagToFile(@PathVariable Long tagId, @PathVariable Long fileId, Authentication authentication) throws FileNotFoundException {
        // Vérifier si le fichier appartient à l'utilisateur authentifié
      //  AppFile file = appFileService.getFileById(fileId);
      //  if (!file.getDossier().getUser().getEmail().equals(authentication.getName())) {
      //      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Vous n'êtes pas autorisé à effectuer cette opération.");
      //  }

        // Ajouter le tag au fichier
        tagService.addTagToFile(tagId, fileId, authentication);
        return ResponseEntity.ok("Le tag a été ajouté au fichier avec succès.");
        //} else {
        //    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag ou fichier non trouvé.");
        //}
    }

    @DeleteMapping("/{tagId}/remove-from-file/{fileId}")
    public ResponseEntity<String> removeTagFromFile(@PathVariable Long tagId, @PathVariable Long fileId, Authentication authentication) throws FileNotFoundException {
        // Vérifier si le fichier appartient à l'utilisateur authentifié
       // AppFile file = appFileService.getFileById(fileId);
       // if (file == null || !file.getDossier().getUser().getEmail().equals(authentication.getName())) {
       //     return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Vous n'êtes pas autorisé à effectuer cette opération.");
       // }

        // Supprimer le tag du fichier
        tagService.removeTagFromFile(tagId, fileId, authentication);
        return ResponseEntity.ok("Le tag a été supprimé du fichier avec succès.");
       // } else {
       //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag ou fichier non trouvé.");
       // }
    }

    @GetMapping("/files-with-tag/{tagId}")
    public ResponseEntity<List<FileDao>> getFilesWithTag(@PathVariable Long tagId) {
        List<AppFile> files = tagService.getFilesWithTag(tagId);
        return ResponseEntity.ok(files.stream().map(file -> FileDao.builder().
                name(file.getName()).fileType(file.getFileType()).
                owner(file.getDossier().getUser().getEmail()).
                size(file.getSize()).folder(file.getDossier().getNom()).build()).collect(Collectors.toList()));
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<String> updateTag(@PathVariable Long tagId, @RequestBody Tag tag) {
        tagService.updateTag(tagId, tag);
        return ResponseEntity.ok("Le tag a été mis à jour avec succès.");
       // } else {
       //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag non trouvé.");
       // }
    }
}
