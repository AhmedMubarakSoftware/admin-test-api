package com.santechture.api.service;

import com.santechture.api.dto.GeneralResponse;
import com.santechture.api.dto.user.UserDto;
import com.santechture.api.entity.User;
import com.santechture.api.exception.BusinessExceptions;
import com.santechture.api.repository.UserRepository;
import com.santechture.api.security.AuthenticationSetManager;
import com.santechture.api.validation.AddUserRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GeneralResponse> list(Pageable pageable) {
        return new GeneralResponse().response(userRepository.findAll(pageable));
    }
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GeneralResponse> addNewUser(AddUserRequest request) throws BusinessExceptions {

        if (userRepository.existsByUsernameIgnoreCase(request.getUsername())) {
            throw new BusinessExceptions("username.exist");
        } else if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new BusinessExceptions("email.exist");
        }

        User user = new User(request.getUsername(), request.getEmail());
        userRepository.save(user);
        SecurityContext sc = SecurityContextHolder.getContext();
        UserDetails userDetails= (UserDetails) sc.getAuthentication().getPrincipal();
        AuthenticationSetManager.removeAuthentication(userDetails.getUsername() );
        return new GeneralResponse().response(new UserDto(user));
    }

}
