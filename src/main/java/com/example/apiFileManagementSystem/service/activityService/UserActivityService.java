package com.example.apiFileManagementSystem.service.activityService;

import com.example.apiFileManagementSystem.entity.Activity;
import com.example.apiFileManagementSystem.entity.ActivityType;
import com.example.apiFileManagementSystem.entity.AppUser;
import com.example.apiFileManagementSystem.repos.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public void loginActivity(AppUser user) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.LOGIN);
        activity.setActivityDate(LocalDateTime.now());
        activityRepository.save(activity);
    }
    public void logoutActivity(AppUser user) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.LOGOUT);
        activity.setActivityDate(LocalDateTime.now());
        activityRepository.save(activity);
    }
    public void profileActivity(AppUser user) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.PROFILE);
        activity.setActivityDate(LocalDateTime.now());
        activityRepository.save(activity);
    }
    public void chanePasswordActivity(AppUser user) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.PASS_UPDATE);
        activity.setActivityDate(LocalDateTime.now());
        activityRepository.save(activity);
    }
    public void createUserActivity(AppUser user) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.REGISTER);
        activity.setActivityDate(LocalDateTime.now());
        activityRepository.save(activity);
    }
    public void deleteUserActivity(AppUser user) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.DELETE_USER);
        activity.setActivityDate(LocalDateTime.now());
        activityRepository.save(activity);
    }
    public void showUsersActivity(AppUser user) {
        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.USERS_SHOW);
        activity.setActivityDate(LocalDateTime.now());
        activityRepository.save(activity);
    }


    public void updateUserActivity(AppUser user) {

        Activity activity = new Activity();
        activity.setAppuser(user);
        activity.setActivityType(ActivityType.UPDATE_USER);
        activity.setActivityDate(LocalDateTime.now());
        activityRepository.save(activity);
    }
}
