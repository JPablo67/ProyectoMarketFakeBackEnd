package com.Alejandro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.Alejandro.Service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> requestBody,
                        @RequestParam(name = "rol", required = false) String rol) {
        String email = requestBody.get("email");
        String password = requestBody.get("password");

        int userType = userService.findLogin(email, password);

        if (userType == 1 && "Cliente".equals(rol)) {
            return "clienteView";
        } else if (userType == 2 && "Empleado".equals(rol)) {
            return "empleadoView";
        } else if (userType == 3 && "Admin".equals(rol)) {
            return "adminView";
        } else {
            return "loginPage";
        }
    }
}
