package com.example.apiFileManagementSystem.service.activityService;

import com.example.apiFileManagementSystem.entity.*;
import com.example.apiFileManagementSystem.repos.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FolderActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public void createFolderActivity(AppUser user, Folder folder) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.FOLDER_CREATE);
        activity.setActivityDate(LocalDateTime.now());
        activity.setFolder(folder);
        activityRepository.save(activity);
    }
    public void FolderDelete(AppUser user, Folder folder) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.FOLDER_DELETE);
        activity.setActivityDate(LocalDateTime.now());
        activity.setFolder(folder);
        activityRepository.save(activity);
    }
    public void CreateSubFolder(AppUser user, Folder folder) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.FOLDER_SUBFOLDER);
        activity.setActivityDate(LocalDateTime.now());
        activity.setFolder(folder);
        activityRepository.save(activity);
    }
    public void UpdateFolder(AppUser user, Folder folder) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.FILE_UPLOAD);
        activity.setActivityDate(LocalDateTime.now());
        activity.setFolder(folder);
        activityRepository.save(activity);
    }
}
