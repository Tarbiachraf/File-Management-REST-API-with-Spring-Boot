package com.example.apiFileManagementSystem.service.activityService;

import com.example.apiFileManagementSystem.entity.*;
import com.example.apiFileManagementSystem.repos.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TagActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    public void createTagActivity(AppUser user, Tag tag) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.TAG_CREATE);
        activity.setActivityDate(LocalDateTime.now());
        activity.setTag(tag);
        activityRepository.save(activity);
    }
    public void deleteTagActivity(AppUser user, Tag tag) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.TAG_DELETE);
        activity.setActivityDate(LocalDateTime.now());
        activity.setTag(tag);
        activityRepository.save(activity);
    }
    public void updateTagActivity(AppUser user, Tag tag) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.TAG_UPDATE);
        activity.setActivityDate(LocalDateTime.now());
        activity.setTag(tag);
        activityRepository.save(activity);
    }
    public void addTagToFileActivity(AppUser user, Tag tag) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.TAG_ADDTOFILE);
        activity.setActivityDate(LocalDateTime.now());
        activity.setTag(tag);
        activityRepository.save(activity);
    }
    public void removeTagToFileActivity(AppUser user, Tag tag) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.TAG_REMOVEINFILE);
        activity.setActivityDate(LocalDateTime.now());
        activity.setTag(tag);
        activityRepository.save(activity);
    }

}
