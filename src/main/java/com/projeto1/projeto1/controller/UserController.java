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

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*") //permite que qualquer fonte acesse a API
public class UserController {

    @Autowired
    private UserRepository userRepository; //instancia o UserRepository

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> listarUsuarios() {
        List<User> usuarios = userService.listarUsuarios();

        if (usuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null); // Retorna 204 No Content se não houver usuários
        }

        return ResponseEntity.ok(usuarios); // Retorna 200 OK com a lista de usuários
    }

    @PostMapping //cadastrar
    public ResponseEntity<String> cadastrarUsuario(@RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.ok("{\"message\": \"Usuário cadastrado com sucesso!\"}");
    }


    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> deleteUser(@PathVariable Long codigo){
        Optional<User> optionalUser = userRepository.findById(codigo);

        if(optionalUser.isPresent()){ //isPresent() é uma função da library Optional

            userRepository.deleteById(codigo);

            return ResponseEntity.ok("{\"message\": \"Usuário deletado com sucesso.\"}");
        }
        else{
            return ResponseEntity.notFound().build();
        }


    }

    @PutMapping("/{codigo}")
    public ResponseEntity<User> updateUser(@PathVariable Long codigo, @RequestBody User user){ //PathVariable captura o valor direto da URL e mapeia para um parâmetro
        /*Optional serve aqui pois pode encontrar ou não um usuário. Se não utilizar o Optional,
        ele retorna null caso não encontre o usuário e isso causará um erro de NullPointerException.
        */
        Optional<User> optionalUser = userRepository.findById(codigo);

        //existingUser é o usuario existente no banco de dados. user são as infos do usuário passados no body da requisição.

        if(optionalUser.isPresent()){ //isPresent() é uma função da library Optional
            User existingUser = optionalUser.get();

            existingUser.setEmail(user.getEmail());
            existingUser.setSenha(user.getSenha());
            existingUser.setCel(user.getCel());
            existingUser.setNome(user.getNome());
            existingUser.setSobrenome(user.getSobrenome());

            userRepository.save(existingUser); //atualiza de fato as informações no banco de dados
            return ResponseEntity.ok(existingUser);
        }
        else{
            return ResponseEntity.notFound().build();
        }

    }
}
