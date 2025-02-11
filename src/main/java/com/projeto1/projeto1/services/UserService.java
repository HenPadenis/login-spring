package com.projeto1.projeto1.services;

import com.projeto1.projeto1.model.User;
import com.projeto1.projeto1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> listarUsuarios() {
        return userRepository.findAll();  // Retorna todos os usuários do banco de dados
    }

    public boolean validarLogin(String email, String senha){
        System.out.println("Email recebido: " + email);
        System.out.println("Senha recebida: " + senha);

        User user = userRepository.findByEmail(email);

        if (user != null) {
            System.out.println(user);
            System.out.println("Usuário encontrado: " + user.getEmail());
            System.out.println("Senha do usuário: " + user.getSenha());
        } else {
            System.out.println("Usuário não encontrado");
        }

        return user != null && user.getSenha().equals(senha);
    }







}
