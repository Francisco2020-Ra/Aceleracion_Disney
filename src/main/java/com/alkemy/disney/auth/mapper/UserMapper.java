package com.alkemy.disney.auth.mapper;

import com.alkemy.disney.auth.config.SecurityConfiguration;
import com.alkemy.disney.auth.dto.UserDTO;
import com.alkemy.disney.auth.entity.RoleEntity;
import com.alkemy.disney.auth.entity.UserEntity;
import com.alkemy.disney.auth.entity.enumrole.ERole;
import com.alkemy.disney.auth.repository.RoleRespository;
import com.alkemy.disney.auth.repository.UserRepository;
import com.alkemy.disney.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {


    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private RoleRespository roleRespository;

    @Autowired
    public UserMapper(@Lazy PasswordEncoder encoder){
        this.encoder = encoder;
    }

    public UserEntity userDto2Entity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(encriptPass(userDTO));
        userEntity.setRoles(roles(userDTO));
        return userEntity;
    }

    public String encriptPass(UserDTO userDTO){
        return encoder.encode(userDTO.getPassword());
    }

    public Set<RoleEntity> roles(UserDTO userDTO){

        Set<String> strRoles = userDTO.getRole();
        Set<RoleEntity> roles = new HashSet<>();
        if (strRoles == null) {
            RoleEntity userRole = roleRespository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        RoleEntity adminRole = roleRespository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        RoleEntity modRole = roleRespository.findByName(ERole.ROLE_MODERATOR).orElseThrow(()->new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        RoleEntity userRole = roleRespository.findByName(ERole.ROLE_USER).orElseThrow(()->new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
       return roles;
    }

    public Collection<? extends GrantedAuthority> userEntityRole2Colletion (UserEntity userEntity){
        Collection<? extends GrantedAuthority> authorities;

        List<GrantedAuthority> authoritie = userEntity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        authorities = authoritie;
        return authorities;

    }
}
