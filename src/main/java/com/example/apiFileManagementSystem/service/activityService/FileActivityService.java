package com.example.apiFileManagementSystem.service.activityService;

import com.example.apiFileManagementSystem.entity.Activity;
import com.example.apiFileManagementSystem.entity.ActivityType;
import com.example.apiFileManagementSystem.entity.AppFile;
import com.example.apiFileManagementSystem.entity.AppUser;
import com.example.apiFileManagementSystem.repos.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FileActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    public void createFileUploadActivity(AppUser user, AppFile file) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.FILE_UPLOAD);
        activity.setActivityDate(LocalDateTime.now());
        activity.setAppFile(file);

        activityRepository.save(activity);
    }

    public void fileDeleteActivity(AppUser user, AppFile file) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.FILE_DELETE);
        activity.setActivityDate(LocalDateTime.now());
        activity.setAppFile(file);

        activityRepository.save(activity);
    }
    public void fileDownloadActivity(AppUser user, AppFile file){
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.FILE_DOWNLOAD);
        activity.setActivityDate(LocalDateTime.now());
        activity.setAppFile(file);

        activityRepository.save(activity);
    }
}
