package com.iniestar.mark1.config.handler;

import com.iniestar.mark1.db.entity.Admin;
import com.iniestar.mark1.db.repo.AdminRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService {

    AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findOneByAdminName(username);

        if(null != admin) {
            return admin;
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
