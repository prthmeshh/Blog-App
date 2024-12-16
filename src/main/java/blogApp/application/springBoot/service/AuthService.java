package blogApp.application.springBoot.service;

import blogApp.application.springBoot.payload.LoginDto;
import blogApp.application.springBoot.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
