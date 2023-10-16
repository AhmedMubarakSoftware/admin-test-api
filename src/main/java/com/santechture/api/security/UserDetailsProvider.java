package com.santechture.api.security;

import com.santechture.api.entity.Admin;
import com.santechture.api.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsProvider implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String name)
            throws UsernameNotFoundException {
        System.out.println("Init loadUser" + "  name : " + name);
        Admin admin = adminRepository.findByUsernameIgnoreCase(name);

        if (admin == null) {
            System.out.println("exception");
            throw new RuntimeException();
        }

        List<GrantedAuthority> authorities = getAuthorities(admin);
        return buildUserForAuthentication(admin, authorities);
    }

    private List<GrantedAuthority> getAuthorities(Admin admin) {
        System.out.println("Init getAuthorities");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        return authorities;
    }

    private UserDetails buildUserForAuthentication(Admin admin,
                                                   List<GrantedAuthority> authorities) {
        System.out.println("Init buildUser");

        System.out.println("================= Username: " + admin.getUsername() + "=======Password: " + admin.getPassword() + "================");
        System.out.println("Authority: " + authorities.get(0).getAuthority());
        return new User(admin.getUsername(), admin.getPassword(), true,
                true, true, true, authorities);
    }
}
