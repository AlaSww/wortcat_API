package com.Alasww.wortcat_API.auth;

import com.Alasww.wortcat_API.config.JwtService;
import com.Alasww.wortcat_API.stats.Stat;
import com.Alasww.wortcat_API.stats.StatRepo;
import com.Alasww.wortcat_API.user.User;
import com.Alasww.wortcat_API.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo repo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final StatRepo statRepo;

    public AuthenticationResponse register(RegisterRequest request) {
        var user= User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .build();
        repo.save(user);
        Stat userStat=new Stat(user);
        statRepo.save(userStat);
        var jwtToken= jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
         authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(
                         request.getEmail(),
                         request.getPassword()
                 )
         );
         var user= repo.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken= jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
