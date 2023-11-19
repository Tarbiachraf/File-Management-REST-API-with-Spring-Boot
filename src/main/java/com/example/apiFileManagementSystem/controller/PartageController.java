package com.example.apiFileManagementSystem.controller;

import com.example.apiFileManagementSystem.entity.Partage;
import com.example.apiFileManagementSystem.exception.GroupNotFoundException;
import com.example.apiFileManagementSystem.models.PartageDao;
import com.example.apiFileManagementSystem.service.PartageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/partages")
public class PartageController {
    @Autowired
    private PartageService partageService;

    @PostMapping("/share")
    public ResponseEntity<?> shareResource(@RequestBody PartageDao partageDao) throws FileNotFoundException, GroupNotFoundException {
        /*try {
            Partage sharedPartage = partageService.shareResource(partageRequest);
            if(sharedPartage !=null){
                return ResponseEntity.status(HttpStatus.CREATED).body(sharedPartage);
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requête invalide.");
            }
        }catch (GroupNotFoundException groupNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(groupNotFoundException.getMessage());
        }catch (FileNotFoundException fileNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(fileNotFoundException.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur inattendue : " + e.getMessage());
        }*/
        PartageDao sharedPartage = partageService.shareResource(partageDao);
        if (sharedPartage != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(sharedPartage);
        }

        // Si sharedPartage est null, vous pouvez choisir de ne pas renvoyer de réponse BadRequest,
        // car GlobalExceptionHandler s'occupera des exceptions spécifiques.

        return ResponseEntity.status(HttpStatus.OK).body("La ressource n'a pas été partagée.");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PartageDao> getPartagesByUsers(@PathVariable Long userId){
        PartageDao partage = partageService.getPartageById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(partage);
    }

    @DeleteMapping("/{partageId}")
    public ResponseEntity<String> deletePartage(@PathVariable Long partageId) {
        boolean deleted = partageService.deletePartage(partageId);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Partage supprimé avec succès.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Partage non trouvé.");
        }
    }

}
