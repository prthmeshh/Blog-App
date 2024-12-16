package blogApp.application.springBoot.service.impl;

import blogApp.application.springBoot.entity.Role;
import blogApp.application.springBoot.entity.User;
import blogApp.application.springBoot.exception.BlogAPIException;
import blogApp.application.springBoot.payload.LoginDto;
import blogApp.application.springBoot.payload.RegisterDto;
import blogApp.application.springBoot.repository.RoleRepository;
import blogApp.application.springBoot.repository.UserRepository;
import blogApp.application.springBoot.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return "User logged-in successfully!";

    }

    @Override
    public String register(RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Username already exists!");
        }
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Email already exists");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles=new HashSet<>();
        Role roleUser = roleRepository.findByName("ROLE_USER").get();
        roles.add(roleUser);
        user.setRoles(roles);

        userRepository.save(user);

        return "User Registered Successfully!";

    }
}
