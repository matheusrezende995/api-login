package com.login.repository;

import com.login.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método utilitário para buscar usuário por e-mail na hora do login
    Optional<Usuario> findByEmail(String email);

    // Método utilitário para verificar se o e-mail já foi cadastrado
    boolean existsByEmail(String email);
}