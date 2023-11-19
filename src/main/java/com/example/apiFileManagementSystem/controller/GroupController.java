package com.example.apiFileManagementSystem.controller;

import com.example.apiFileManagementSystem.entity.GroupEntity;
import com.example.apiFileManagementSystem.exception.GroupNotFoundException;
import com.example.apiFileManagementSystem.models.GroupDao;
import com.example.apiFileManagementSystem.service.GroupService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/add")
    public ResponseEntity<GroupDao> addGroup(@RequestBody GroupEntity groupEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        GroupEntity addedGroup = groupService.addGroup(groupEntity,authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(GroupDao.builder().groupNom(addedGroup.getGroupNom()).id(addedGroup.getId()).build());
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDao> getGroup(@PathVariable Long groupId) {
        GroupEntity group = groupService.getGroup(groupId);
        //if (group != null) {
        return ResponseEntity.status(HttpStatus.OK).body(GroupDao.builder().groupNom(group.getGroupNom()).id(group.getId()).build());
        //} else {
        //    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        //}
    }

    @GetMapping("/all")
    public ResponseEntity<List<GroupDao>> getAllGroups() {
        List<GroupEntity> groups = groupService.getAllGroups();
        return ResponseEntity.status(HttpStatus.OK).body(groups.stream().
                map(group -> GroupDao.builder().groupNom(group.getGroupNom()).
                        id(group.getId()).build()).collect(Collectors.toList()));
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<String> deleteGroup(@PathVariable Long groupId) {
       groupService.deleteGroup(groupId);
       // if (deleted) {
        return ResponseEntity.status(HttpStatus.OK).body("Groupe supprimé avec succès.");
       // } else {
       //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Groupe non trouvé.");
       // }
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<GroupDao> updateGroup(@PathVariable Long groupId, @RequestBody GroupEntity updatedGroup) {
        GroupEntity group = groupService.updateGroup(groupId, updatedGroup);
        //if (group != null) {
        return ResponseEntity.status(HttpStatus.OK).body(GroupDao.builder().groupNom(group.getGroupNom()).id(group.getId()).build());
        //} else {
        //    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
       // }
    }
    @PostMapping("/{userId}/addTo/{groupId}")
    ResponseEntity<String> addUserToGroup(@PathVariable Long groupId, @PathVariable Long userId) throws GroupNotFoundException {
       // try {
        groupService.addUserToGroup(groupId,userId);
        return ResponseEntity.ok("l'utilisateur de l'id "+groupId+" est ajouté avec succés au groupe de l'id "+ groupId);


     //   }catch (GroupNotFoundException | UsernameNotFoundException e){
     //       ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
     //   }


      //  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @DeleteMapping("/{userID}/removeFrom/{groupId}")
    ResponseEntity<String> removeUserFromGroup(@PathVariable Long groupId, @PathVariable Long userId){
        //try {
            groupService.addUserToGroup(groupId,userId);
            return ResponseEntity.ok("l'utilisateur de l'id "+groupId+" est supprimé avec succés au groupe de l'id "+ groupId);


        //}catch (GroupNotFoundException | UsernameNotFoundException e){
        //    ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
       // }


       // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
