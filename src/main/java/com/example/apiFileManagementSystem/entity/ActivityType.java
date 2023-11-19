package com.example.apiFileManagementSystem.entity;

public enum ActivityType {
    LOGIN ("Connexion"),
    LOGOUT ("Déconnexion"),
    DELETE_USER ("Supprimer Compte"),
    PASS_UPDATE ("Changer Mot de Passe"),
    REGISTER ("Créer Compte"),
    PROFILE ("Voir Profil"),
    USERS_SHOW ("Voir Utilisateurs"),
    USER_UPDATE("Modifier user"),
    FILE_UPLOAD ("Importer Fichier"),
    UPDATE_USER("Modifier l'utilisateur"),
    FILE_DOWNLOAD ("Télécharger Fichier"),
    FILE_DELETE ("Supprimer Fichier"),
    GROUP_CREATE ("Créer Groupe"),
    GROUP_DELETE ("Supprimer Groupe"),
    GROUP_JOIN ("Rejoindre Groupe"),
    GROUP_LEAVE ("Quitter Groupe"),
    FOLDER_CREATE ("Créer Dossier"),
    FOLDER_DELETE ("Supprimer Dossier"),
    FOLDER_UPDATE ("M"),
    FOLDER_SUBFOLDER ("Créer Sous-Dossier"),
    TAG_CREATE ("Créer Tag"),
    TAG_DELETE ("Supprimer Tag"),
    TAG_UPDATE ("Modifier Tag"),
    TAG_ADDTOFILE ("Ajouter Tag au Fichier"),
    TAG_REMOVEINFILE ("Enlever Tag du Fichier"),
    PARTAGE_SHARE ("Partager Fichier"),
    PARTAGE_DELETE ("Supprimer Partage"),
    PARTAGE_AFFICHE ("Voir Partages"),
    GOUP_UPDATE ("Modifier Groupe");




    private final String displayName;

    ActivityType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
