package com.example.itjobportal.service;

import com.example.itjobportal.dto.AuthDTO;
import com.example.itjobportal.dto.UserDTO;
import com.example.itjobportal.entity.User;
import com.example.itjobportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public User toEnity(UserDTO userDTO){
        return User.builder()
                .id(userDTO.getId())
                .fullName(userDTO.getFullName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .profileImageUrl(userDTO.getProfileImageUrl())
                .role(userDTO.getRole())
                .createdAt(userDTO.getCreatedAt())
                .updatedAt(userDTO.getUpdatedAt())
                .build();
    }

    public  UserDTO toDTO(User user){
        boolean isCompanyActive = false;
        if (user.getCompany() != null) {

            isCompanyActive = user.getCompany().isActive();
        }
        return UserDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .role(user.getRole())
                .companyId(user.getCompany() != null ? user.getCompany().getId() : null)
                .companyIsAtive(isCompanyActive)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public UserDTO registerUser(UserDTO userDTO){
        User newUser = toEnity(userDTO);
        newUser.setActivationToken(UUID.randomUUID().toString());
        newUser = userRepository.save(newUser);

        //send activation email
        String activationLink = "http://localhost:5454/api/v1.0/activate?token=" + newUser.getActivationToken();
        String subject = "Xác nhận tài khoản IT Job Portal của bạn";
        String body = "Nhấn vào đường link bên dưới để tiến hành xác nhận tài khoản của bạn " + activationLink;
        emailService.sendEmail(newUser.getEmail(),subject,body);
        return toDTO(newUser);
    }

    public boolean activeUser(String activationToken){
        return userRepository.findByActivationToken(activationToken)
                .map(user -> {
                    user.setIsActive(true);
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }

    public boolean isAccountActive(String email){
        return  userRepository.findByEmail(email)
                .map(User::getIsActive)
                .orElse(false);
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Profile not found with email: "+ authentication.getName()));
    }

    public UserDTO getPublicUser(String email){
        User currentUser = null;
        if (email == null ){
            currentUser = getCurrentUser();
        }else {
            currentUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Profile is not found with email: " + email));
        }

        return UserDTO.builder()
                .id(currentUser.getId())
                .fullName(currentUser.getFullName())
                .email(currentUser.getEmail())
                .profileImageUrl(currentUser.getProfileImageUrl())
                .role(currentUser.getRole())
                .companyId(currentUser.getCompany() != null ? currentUser.getCompany().getId() : null)
                .createdAt(currentUser.getCreatedAt())
                .updatedAt(currentUser.getUpdatedAt())
                .build();
    }

    public Map<String, Object> authenticateAndGenerateToke(AuthDTO authDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(),authDTO.getPassword()));
            //generate Token
            String token = jwtService.generateAccessToken(authDTO);
            return  Map.of(
                    "token",token,
                    "user", getPublicUser(authDTO.getEmail())
            );
        } catch (Exception e) {
            throw new RuntimeException("Invalid email or password");
        }
    }

    public UserDTO getMyProfile(){
        User myprofile = getCurrentUser();
        return toDTO(myprofile);
    }

}
