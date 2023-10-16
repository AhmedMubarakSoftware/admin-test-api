package com.santechture.api.service;

import com.santechture.api.dto.GeneralResponse;
import com.santechture.api.dto.admin.AdminDto;
import com.santechture.api.entity.Admin;
import com.santechture.api.exception.BusinessExceptions;
import com.santechture.api.repository.AdminRepository;
import com.santechture.api.security.AuthenticationSetManager;
import com.santechture.api.utils.JwtTokenUtil;
import com.santechture.api.validation.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    @Autowired
    MessageSource messageSource;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public ResponseEntity<GeneralResponse> login(LoginRequest loginRequest) throws BusinessExceptions {


        try {
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword());
            Authentication auth = authManager.authenticate(authReq);

            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
        } catch (Exception e) {
            throw new BusinessExceptions("login.credentials.not.match");

        }
        Admin admin = adminRepository.findByUsernameIgnoreCase(loginRequest.getUsername());
        String token = jwtTokenUtil.generateToken(admin.getUsername());
        AuthenticationSetManager.addNewAuthentication(loginRequest.getUsername(), token);
        return new GeneralResponse().response(new AdminDto(admin, token));
    }

}
