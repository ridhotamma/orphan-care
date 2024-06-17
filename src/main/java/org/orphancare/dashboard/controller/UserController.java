package org.orphancare.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.CreateUserDto;
import org.orphancare.dashboard.dto.UpdateUserDto;
import org.orphancare.dashboard.dto.ChangePasswordUserDto;
import org.orphancare.dashboard.dto.UserDto;
import org.orphancare.dashboard.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        UserDto createdUser = userService.createUser(createUserDto);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable UUID id, @Valid @RequestBody UpdateUserDto updateUserDto) {
        UserDto updatedUser = userService.updateUser(id, updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<UserDto> changeUserPassword(@PathVariable UUID id, @Valid @RequestBody ChangePasswordUserDto changePasswordUserDto) {
        UserDto updatedUser = userService.changeUserPassword(id, changePasswordUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        UserDto user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        UserDto user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }
}
