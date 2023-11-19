package com.example.apiFileManagementSystem.service;

import com.example.apiFileManagementSystem.entity.AppUser;
import com.example.apiFileManagementSystem.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(username).get();
        UserDetails userDetails = User.builder()
                .username(user.getEmail()) // Utilisez l'e-mail comme nom d'utilisateur
                .password(user.getPassword())
                //.authorities(user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()))
                //.roles("ADMIN")
                .roles(user.getRoleEnum().name())
                // Liste des rôles sous forme d'autorités
                .build();

        return userDetails;
    }
}
