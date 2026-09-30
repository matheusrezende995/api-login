package com.login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teste")
public class TesteController {

    @GetMapping
    public ResponseEntity<String> rotaProtegida() {
        return ResponseEntity.ok("Acesso liberado! Se você está vendo isso, seu token JWT é válido.");
    }
}