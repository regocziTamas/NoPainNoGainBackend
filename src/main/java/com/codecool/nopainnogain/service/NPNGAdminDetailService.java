package com.codecool.nopainnogain.service;

import com.codecool.nopainnogain.auth.NPNGAdmin;
import com.codecool.nopainnogain.repositories.AdminRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class NPNGAdminDetailService implements UserDetailsService {

    private AdminRepository adminRepository;

    public NPNGAdminDetailService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        NPNGAdmin admin = adminRepository.findByUsername(s);
        if(admin == null){
            throw new UsernameNotFoundException(s);
        }
        return new User(admin.getUsername(), admin.getPassword(),Collections.emptyList());
    }
}
