package com.projeto1.projeto1.controller;

import com.projeto1.projeto1.model.User;
import com.projeto1.projeto1.repository.UserRepository;
import com.projeto1.projeto1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController //identifica que essa classe será o controller da API
@RequestMapping("/login") //endpoint para acessar
@CrossOrigin(origins = "*") //permite que qualquer fonte acesse a API
public class LoginController {

    @Autowired
    private UserService userService; //instancia o UserService com a anotação @Autowired

    //Método para validar login a partir do email e senha recebidos
    @GetMapping
    public ResponseEntity<String> fazerLogin(@RequestParam String email, @RequestParam String senha){
        if(userService.validarLogin(email, senha)){
            return ResponseEntity.ok("Login bem-sucedido!");
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha ao realizar login.");
        }
    }

    @Autowired
    private UserRepository userRepository; //instancia o UserRepository

    @PostMapping
    public ResponseEntity<String> fazerLogin(@RequestBody User user){
        if(userService.validarLogin(user.getEmail(), user.getSenha())){
            return ResponseEntity.ok("{\"message\": \"Login bem-sucedido!\"}");
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha ao realizar login.");
        }
    }


}
