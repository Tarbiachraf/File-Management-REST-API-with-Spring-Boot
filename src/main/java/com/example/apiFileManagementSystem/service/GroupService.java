package com.example.apiFileManagementSystem.service;

import com.example.apiFileManagementSystem.config.ErrorCodeConstants;
import com.example.apiFileManagementSystem.config.ErrorMessages;
import com.example.apiFileManagementSystem.config.ResourceTypeConstants;
import com.example.apiFileManagementSystem.entity.AppUser;
import com.example.apiFileManagementSystem.entity.GroupEntity;
import com.example.apiFileManagementSystem.exception.GroupNotFoundException;
import com.example.apiFileManagementSystem.exception.RessourceNotFoundException;
import com.example.apiFileManagementSystem.repos.GroupRepository;
import com.example.apiFileManagementSystem.repos.UserRepository;
import com.example.apiFileManagementSystem.service.activityService.GroupActivityService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupActivityService groupActivityService;

    @Autowired
    private UserRepository userRepository;

    public GroupEntity addGroup(GroupEntity groupEntity, Authentication authentication) {
        GroupEntity newGroup = groupRepository.save(groupEntity);
        AppUser appUser = userRepository.findByEmail(authentication.getName()).orElseThrow(()->
                new RessourceNotFoundException(ErrorCodeConstants.USER_NOT_FOUND,
                        ResourceTypeConstants.USER,
                        authentication.getName(),
                        ErrorMessages.USERNotFoundMessage));
        if(newGroup != null ) groupActivityService.createGroupActivity(appUser,newGroup);
        return newGroup;
    }

    public GroupEntity getGroup(Long groupId) {
        return groupRepository.findById(groupId).orElseThrow(()->new RessourceNotFoundException(ErrorCodeConstants.GROUP_NOT_FOUND,
                ResourceTypeConstants.Group,
                groupId,
                ErrorMessages.GroupNotFoundMessage));
    }

    public List<GroupEntity> getAllGroups() {
        return groupRepository.findAll();
    }

    public boolean deleteGroup(Long groupId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = userRepository.findByEmail(authentication.getName()).orElseThrow(()->
                new RessourceNotFoundException(ErrorCodeConstants.USER_NOT_FOUND,
                        ResourceTypeConstants.USER,
                        authentication.getName(),
                        ErrorMessages.USERNotFoundMessage));
        GroupEntity group = groupRepository.findById(groupId).orElseThrow(()->new RessourceNotFoundException(ErrorCodeConstants.GROUP_NOT_FOUND,
                ResourceTypeConstants.Group,
                groupId,
                ErrorMessages.GroupNotFoundMessage));
       // if (groupRepository.existsById(groupId)) {
            groupRepository.deleteById(groupId);
            groupActivityService.deleteGroupActivity(appUser,group);
            return true;
       // }
       // return false;
    }

    public GroupEntity updateGroup(Long groupId, GroupEntity updatedGroup) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = userRepository.findByEmail(authentication.getName()).orElseThrow(()->
                new RessourceNotFoundException(ErrorCodeConstants.USER_NOT_FOUND,
                        ResourceTypeConstants.USER,
                        authentication.getName(),
                        ErrorMessages.USERNotFoundMessage));
        GroupEntity existingGroup = groupRepository.findById(groupId).orElseThrow(()->new RessourceNotFoundException(ErrorCodeConstants.GROUP_NOT_FOUND,
                ResourceTypeConstants.Group,
                groupId,
                ErrorMessages.GroupNotFoundMessage));

            existingGroup.setGroupNom(updatedGroup.getGroupNom());
            GroupEntity groupEntity = groupRepository.save(existingGroup);
            groupActivityService.updateGroupActivity(appUser,groupEntity);
            return groupEntity;

    }

    @Transactional
    public boolean addUserToGroup(Long groupId, Long userId){
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RessourceNotFoundException(ErrorCodeConstants.GROUP_NOT_FOUND,ResourceTypeConstants.Group,groupId,ErrorMessages.GroupNotFoundMessage));

        AppUser appUser = userRepository.findById(userId).orElseThrow(()->
                new RessourceNotFoundException(ErrorCodeConstants.USER_NOT_FOUND,
                        ResourceTypeConstants.USER,
                        userId,
                        ErrorMessages.USERNotFoundMessage));
        if(!group.getAppUsers().contains(appUser)){
            group.getAppUsers().add(appUser);
            groupRepository.save(group);
            groupActivityService.addUserToGroupActivity(appUser,group);
            return true;
        }
        return false;

    }
    @Transactional
    public boolean removeUserFromGroup(Long groupId, Long userId) throws GroupNotFoundException {
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RessourceNotFoundException(ErrorCodeConstants.GROUP_NOT_FOUND,ResourceTypeConstants.Group,groupId,ErrorMessages.GroupNotFoundMessage));

        AppUser appUser = userRepository.findById(userId)
                .orElseThrow(()->
                        new RessourceNotFoundException(ErrorCodeConstants.USER_NOT_FOUND,
                                ResourceTypeConstants.USER,
                                userId,
                                ErrorMessages.USERNotFoundMessage));
        if(group.getAppUsers().contains(appUser)){
            group.getAppUsers().remove(appUser);
            groupRepository.save(group);
            groupActivityService.removeUserToGroupActivity(appUser,group);
            return true;
        }
        return false;

    }
}