package com.example.apiFileManagementSystem.service;

import com.example.apiFileManagementSystem.entity.Activity;
import com.example.apiFileManagementSystem.entity.ActivityType;
import com.example.apiFileManagementSystem.entity.AppFile;
import com.example.apiFileManagementSystem.repos.ActivityRepository;
import org.apache.catalina.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {
    @Autowired
    public ActivityRepository activityRepository;



}
