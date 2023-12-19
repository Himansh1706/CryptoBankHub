package com.cbh.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cbh.userservice.models.User;
import com.cbh.userservice.requestdto.AddUser;
import com.cbh.userservice.requestdto.DoKyc;
import com.cbh.userservice.service.UserService;

import lombok.RequiredArgsConstructor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller class for managing user-related operations.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Api(tags = "User Controller", value = "Operations related to users")
public class UserController {

    private final UserService userService;

    /**
     * Adds a new user.
     *
     * @param addUser The user information to be added.
     * @return ResponseEntity containing the added user.
     */
    @PostMapping("")
    @ApiOperation(value = "Add a new user")
    public ResponseEntity<User> add(@RequestBody AddUser addUser) {
        User user = userService.addUser(addUser);
        return ResponseEntity.ok(user);
    }

    /**
     * Performs KYC (Know Your Customer) for a user.
     *
     * @param doKyc The KYC information to be processed.
     * @return ResponseEntity containing the user after KYC.
     */
    @PostMapping("/kyc")
    @ApiOperation(value = "Perform KYC for a user")
    public ResponseEntity<User> kyc(@RequestBody DoKyc doKyc) {
        User user = userService.kyc(doKyc);
        return ResponseEntity.ok(user);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return ResponseEntity containing the user with the specified ID.
     */
    @GetMapping("/{userId}")
    @ApiOperation(value = "Get user by ID")
    public ResponseEntity<User> userById(@PathVariable String userId) {
        return ResponseEntity.ok(userService.fetchUserById(userId));
    }
    
}

