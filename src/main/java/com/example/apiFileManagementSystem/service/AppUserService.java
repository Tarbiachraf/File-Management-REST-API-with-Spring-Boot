package com.example.apiFileManagementSystem.service;

import ch.qos.logback.core.spi.ErrorCodes;
import com.example.apiFileManagementSystem.config.ErrorCodeConstants;
import com.example.apiFileManagementSystem.config.ErrorMessages;
import com.example.apiFileManagementSystem.config.ResourceTypeConstants;
import com.example.apiFileManagementSystem.entity.AppUser;
import com.example.apiFileManagementSystem.entity.RoleEnum;
import com.example.apiFileManagementSystem.exception.DuplicateResourceException;
import com.example.apiFileManagementSystem.exception.RessourceNotFoundException;
import com.example.apiFileManagementSystem.models.NewPassword;
import com.example.apiFileManagementSystem.models.RegisterRequest;
import com.example.apiFileManagementSystem.models.UpdateRequest;
import com.example.apiFileManagementSystem.models.UserProfile;
import com.example.apiFileManagementSystem.repos.UserRepository;
import com.example.apiFileManagementSystem.service.activityService.UserActivityService;
import com.example.apiFileManagementSystem.util.JwtUutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserActivityService userActivityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUutil jwtUtil;

    public AppUser createUser(RegisterRequest registerRequest) {
        AppUser appUser = AppUser.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .lastName(registerRequest.getLastName())
                .firstName(registerRequest.getFirstName())
                .roleEnum(RoleEnum.User)
                .build();
        userRepository.save(appUser);
        userActivityService.createUserActivity(appUser);
        return appUser;
    }

    public UserProfile getProfile(Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            AppUser appUser = userRepository.findByEmail(email).orElseThrow(()->
                    new RessourceNotFoundException(ErrorCodeConstants.USER_NOT_FOUND,
                            ResourceTypeConstants.USER,
                            email,
                            ErrorMessages.USERNotFoundMessage));
            if (appUser != null) {
                UserProfile userProfile = new UserProfile(appUser.getEmail(), appUser.getLastName(),appUser.getFirstName(), appUser.getRoleEnum().getDisplayName());
                userActivityService.profileActivity(appUser);
                return userProfile;
            }
        }
        return null;
    }

    public boolean deleteUser(String name) {
        AppUser appUser = userRepository.findByEmail(name).get();
        if (appUser != null) {
            userRepository.delete(appUser);
            userActivityService.deleteUserActivity(appUser);
            return true;
        }
        return false;
    }

//Activity
    public List<UserProfile> getAllUsers() {
        List<AppUser> users = userRepository.findAll();
        return users.stream().map(user -> new UserProfile(user.getEmail(), user.getLastName(),user.getFirstName(),user.getRoleEnum().getDisplayName())).collect(Collectors.toList());
    }

    public String login(Map<String, String> requestMap) {
        authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(requestMap.get("email"));

        AppUser appUser = userRepository.findByEmail(requestMap.get("email")).get();
        userActivityService.loginActivity(appUser);
        final String jwt = jwtUtil.generateToken(userDetails);
        userActivityService.loginActivity(appUser);
        return jwt;
    }

    public AppUser update(UpdateRequest updateRequest) {
        AppUser user = userRepository.findByEmail(updateRequest.getEmail()).orElseThrow(()->
                new RessourceNotFoundException(ErrorCodeConstants.USER_NOT_FOUND,
                        ResourceTypeConstants.USER,
                        updateRequest.getEmail(),
                        ErrorMessages.USERNotFoundMessage));

            user.setLastName(user.getLastName());
            user.setFirstName(user.getFirstName());
            userActivityService.updateUserActivity(user);
            return userRepository.save(user);

    }

    public AppUser updatePassword(NewPassword passWord, Authentication authentication) {
        String username = authentication.getName();
        AppUser user = userRepository.findByEmail(username).orElseThrow(()->
                new RessourceNotFoundException(ErrorCodeConstants.USER_NOT_FOUND,
                        ResourceTypeConstants.USER,
                        username,
                        ErrorMessages.USERNotFoundMessage));
        user.setPassword(passwordEncoder.encode(passWord.getNewPassword()));
        return userRepository.save(user);

    }

    public AppUser addUser(AppUser user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new DuplicateResourceException(ErrorCodeConstants.USER_DUPLICATE, ResourceTypeConstants.USER, ErrorMessages.USER_DUPLICATE_MESSAGE);
        }
        user.setRoleEnum(RoleEnum.User);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

/*    public AppUser addUser(AppUser user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Email is already taken");
            throw new BadRequestException(apiResponse);
        }

        List<Role> roles = new ArrayList<>();
        roles.add(
                roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new AppException("User role not set")));
        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }*/
}