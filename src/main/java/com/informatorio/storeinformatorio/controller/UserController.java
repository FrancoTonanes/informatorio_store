package com.informatorio.storeinformatorio.controller;

import com.informatorio.storeinformatorio.entity.User;
import com.informatorio.storeinformatorio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(UserController.URL)
public class UserController {

    public final static String URL =  "api/v1/user";

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postUser(@Valid @RequestBody User user){
        return new ResponseEntity<>(userService.postUser(user), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<?> putUser(@RequestParam(name = "id") Long id,
                                     @RequestBody @Valid User user){

        User userDB = userService.putUser(id, user);

        if (userDB == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDB, HttpStatus.OK);
    }

    @PatchMapping
    // Modifica al usuario parcialmente
    public ResponseEntity<?> patchUser(@RequestParam(name = "id") Long id,
                                       @RequestBody Map<String, String> user){

        User userDB = userService.patchUser(id, user);

        if (userDB == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDB, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam (name = "id") Long id){
        User userDeleted = userService.deleteUser(id);
        if (userDeleted == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDeleted, HttpStatus.OK);
    }

    @GetMapping("/resistencia")
    public ResponseEntity<List<User>> findUsersCityName(){

        return new ResponseEntity<>(userService.findAllUsersResistencia(), HttpStatus.OK);
    }

    @GetMapping("/fecha")
    public ResponseEntity<?> findAllUsers(@RequestParam (name = "alta") String fecha){
        LocalDate date = LocalDate.parse(fecha);
        return new ResponseEntity<>(userService.findAllUsersByFechaAlta(date), HttpStatus.OK);
    }

}
