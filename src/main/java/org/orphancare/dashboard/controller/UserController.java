package org.orphancare.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.orphancare.dashboard.dto.UserPasswordChangeDto;
import org.orphancare.dashboard.dto.CreateUserDto;
import org.orphancare.dashboard.dto.UpdateUserDto;
import org.orphancare.dashboard.dto.UserDto;
import org.orphancare.dashboard.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/admin/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        UserDto createdUser = userService.createUser(createUserDto);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/admin/users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> updateUser(@PathVariable UUID id, @Valid @RequestBody UpdateUserDto updateUserDto) {
        UserDto updatedUser = userService.updateUser(id, updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/admin/users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin/users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDto.UserWithProfileDto>> getAllUsersWithShortProfile() {
        List<UserDto.UserWithProfileDto> users = userService.getAllUsersWithShortProfile();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/public/users/change-password")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<UserDto> changeUserPassword(@Valid @RequestBody UserPasswordChangeDto userPasswordChangeDto) throws BadRequestException {
        UserDto updatedUser = userService.changeUserPassword(userPasswordChangeDto);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/admin/users/change-password")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> changeUserPasswordByAdmin(@Valid @RequestBody UserPasswordChangeDto.AdminChange userPasswordChangeDto) {
        UserDto updatedUser = userService.changeUserPasswordByAdmin(userPasswordChangeDto);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/admin/users/current-user")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<UserDto.CurrentUserDto> getCurrentUser() {
        UserDto.CurrentUserDto updatedUser = userService.getCurrentUserWithProfile();
        return ResponseEntity.ok(updatedUser);
    }
}
