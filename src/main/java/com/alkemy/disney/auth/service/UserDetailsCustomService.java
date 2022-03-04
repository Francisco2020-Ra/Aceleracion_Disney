package com.alkemy.disney.auth.service;

import com.alkemy.disney.auth.dto.UserDTO;
import com.alkemy.disney.auth.entity.UserEntity;
import com.alkemy.disney.auth.mapper.UserMapper;
import com.alkemy.disney.auth.repository.UserRepository;
import com.alkemy.disney.exception.ParamNotFound;
import com.alkemy.disney.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserDetailsCustomService implements UserDetailsService {

   private UserMapper userMapper;

   @Autowired
   private UserRepository userRepository;
   @Autowired
   private EmailService emailService;

   @Autowired
   public UserDetailsCustomService(@Lazy UserMapper userMapper){
       this.userMapper=userMapper;
   }



    //SingUp
    public ResponseEntity<?> save (UserDTO userDTO){
       if(userRepository.existsByUsername(userDTO.getUsername())){
           throw new ParamNotFound("Usuario existente");
       }
       UserEntity userEntity = userMapper.userDto2Entity(userDTO);
        userEntity = userRepository.save(userEntity);
        if (userEntity != null) {
            emailService.sendWelcomeEmailTo(userEntity.getUsername());
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //SingIn
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userName));
        Collection<? extends GrantedAuthority> authorities = userMapper.userEntityRole2Colletion(userEntity);
        return new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
    }


}

