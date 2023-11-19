package com.example.apiFileManagementSystem.service.activityService;

import com.example.apiFileManagementSystem.entity.*;
import com.example.apiFileManagementSystem.repos.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GroupActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    public void createGroupActivity(AppUser user, GroupEntity groupEntity) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.GROUP_CREATE);
        activity.setActivityDate(LocalDateTime.now());
        activity.setGroup(groupEntity);

        activityRepository.save(activity);
    }
    public void addUserToGroupActivity(AppUser user, GroupEntity groupEntity) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.GROUP_JOIN);
        activity.setActivityDate(LocalDateTime.now());
        activity.setGroup(groupEntity);

        activityRepository.save(activity);
    }
    public void removeUserToGroupActivity(AppUser user, GroupEntity groupEntity) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.GROUP_LEAVE);
        activity.setActivityDate(LocalDateTime.now());
        activity.setGroup(groupEntity);

        activityRepository.save(activity);
    }
    public void deleteGroupActivity(AppUser user, GroupEntity groupEntity) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.GROUP_DELETE);
        activity.setActivityDate(LocalDateTime.now());
        activity.setGroup(groupEntity);

        activityRepository.save(activity);
    }
    public void updateGroupActivity(AppUser user, GroupEntity groupEntity) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.GOUP_UPDATE);
        activity.setActivityDate(LocalDateTime.now());
        activity.setGroup(groupEntity);

        activityRepository.save(activity);
    }
}
