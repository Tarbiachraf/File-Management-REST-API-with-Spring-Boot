package com.example.apiFileManagementSystem.controller;

import com.example.apiFileManagementSystem.entity.AppUser;
import com.example.apiFileManagementSystem.entity.GroupEntity;
import com.example.apiFileManagementSystem.models.*;
import com.example.apiFileManagementSystem.repos.UserRepository;
import com.example.apiFileManagementSystem.service.AppUserService;
import com.example.apiFileManagementSystem.service.activityService.UserActivityService;
import com.example.apiFileManagementSystem.util.JwtUutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class AppUserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private UserActivityService userActivityService;

    @Autowired
    private JwtUutil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody(required = true) Map<String, String> requestMap) {
        String jwt = appUserService.login(requestMap);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public ResponseEntity<UserProfile> update(@RequestBody UpdateRequest updateRequest){
        AppUser appUser = appUserService.update(updateRequest);
        UserProfile userProfile = new UserProfile(appUser.getEmail(),appUser.getLastName(),appUser.getFirstName(),appUser.getRoleEnum().getDisplayName());
        return ResponseEntity.status(HttpStatus.OK).body(userProfile);
    }
  @PostMapping("/changeMotDePass")
    public ResponseEntity<String> updateMotPass(@RequestBody NewPassword newPassword, Authentication authentication){
        AppUser appUser = appUserService.updatePassword(newPassword,authentication);
        return ResponseEntity.status(HttpStatus.OK).body("le mot de passe est changé correctement");

    }
    @PostMapping("/register")
    public ResponseEntity<AppUser> register(@RequestBody RegisterRequest registerRequest){
        if(registerRequest==null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("vous n'avez envoyé aucune information");
        }
        AppUser newUser = appUserService.createUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getProfile(Authentication authentication) {
        UserProfile userProfile = appUserService.getProfile(authentication);
        if(userProfile!=null){
            return ResponseEntity.ok(userProfile);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(Authentication authentication) {
        if (authentication != null) {
            if(appUserService.deleteUser(authentication.getName())){
            return ResponseEntity.ok("Votre compte a été supprimé avec succès.");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("L'opération de suppression a échoué.");

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("L'opération de suppression a échoué.");
    }
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserProfile>> getAllUsers(){
        List<UserProfile> users = appUserService.getAllUsers();
        if(users!=null){
            return ResponseEntity.ok(users);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserProfile> addUser(@RequestBody AppUser user) {
        AppUser newUser = appUserService.addUser(user);
        UserProfile userProfile = new UserProfile(newUser.getEmail(),newUser.getLastName(),newUser.getFirstName(),newUser.getRoleEnum().getDisplayName());
        return new ResponseEntity< >(userProfile, HttpStatus.CREATED);
    }








    /*@PostMapping
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }


        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

     */
}
