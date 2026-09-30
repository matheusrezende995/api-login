package com.login.service;

import com.login.dto.AuthResponse;
import com.login.dto.LoginRequest;
import com.login.dto.RegisterRequest;
import com.login.model.Role;
import com.login.model.Usuario;
import com.login.repository.UsuarioRepository;
import com.login.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse registrar(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new RuntimeException("E-mail já cadastrado no sistema.");
        }

        var usuario = Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .senha(passwordEncoder.encode(request.senha())) // Criptografa a senha com BCrypt
                .role(Role.USER)
                .build();

        usuarioRepository.save(usuario);
        var jwtToken = jwtService.generateToken(usuario);
        return new AuthResponse(jwtToken);
    }

    public AuthResponse login(LoginRequest request) {
        // Valida as credenciais (lança exceção automaticamente se a senha estiver incorreta)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.senha()
                )
        );

        var usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        var jwtToken = jwtService.generateToken(usuario);
        return new AuthResponse(jwtToken);
    }
}