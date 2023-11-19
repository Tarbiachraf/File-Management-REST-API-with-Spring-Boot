package com.example.apiFileManagementSystem.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
@Service
public class FileStorageService {

    public static final String uploadDir = "C:/Users/dell/Desktop/fichiers"; // Remplacez par le chemin de votre dossier d'upload

    public String saveFile(MultipartFile file) throws IOException {
        // Obtenez le nom original du fichier
        String fileName = file.getOriginalFilename();

        // Définissez le chemin complet où vous souhaitez enregistrer le fichier
        Path filePath = Path.of(uploadDir, fileName);

        // Copiez le contenu du fichier dans le dossier d'upload
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Vous pouvez retourner le nom du fichier enregistré si nécessaire
        return fileName;
    }
    public boolean deleteFile(String fileName) throws IOException {
        // Définissez le chemin complet du fichier à supprimer
        Path filePath = Path.of(uploadDir, fileName);

        try {
            // Supprimez le fichier s'il existe
            Files.deleteIfExists(filePath);
            return true; // La suppression a réussi
        } catch (IOException e) {
            // Gérez les exceptions d'erreur de suppression
            e.printStackTrace();
            return false; // La suppression a échoué
        }
    }
}