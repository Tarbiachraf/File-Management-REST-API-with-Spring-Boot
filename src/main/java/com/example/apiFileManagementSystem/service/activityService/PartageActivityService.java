package com.example.apiFileManagementSystem.service.activityService;

import com.example.apiFileManagementSystem.entity.*;
import com.example.apiFileManagementSystem.repos.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PartageActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    public void partageActivity(AppUser user, Partage partage) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.PARTAGE_SHARE);
        activity.setActivityDate(LocalDateTime.now());
        activity.setPartage(partage);


        activityRepository.save(activity);
    }
    public void deletePartageActivity(AppUser user, Partage partage) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.PARTAGE_DELETE);
        activity.setActivityDate(LocalDateTime.now());
        activity.setPartage(partage);


        activityRepository.save(activity);
    }
    public void affichePartageActivity(AppUser user, Partage partage) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.PARTAGE_AFFICHE);
        activity.setActivityDate(LocalDateTime.now());
        activity.setPartage(partage);


        activityRepository.save(activity);
    }
}
