package org.orphancare.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.orphancare.dashboard.dto.*;
import org.orphancare.dashboard.entity.Gender;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto.UserWithProfileDto> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        UserDto.UserWithProfileDto createdUser = userService.createUser(createUserDto);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/admin/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> updateUser(@PathVariable UUID id, @Valid @RequestBody UpdateUserDto updateUserDto) {
        UserDto updatedUser = userService.updateUser(id, updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/admin/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaginatedResponse<List<UserDto.UserWithProfileDto>>> getAllUsers(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) String roles,
            @RequestParam(required = false) Boolean isAlumni,
            @RequestParam(required = false) Boolean isCareTaker,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) UUID bedRoomId,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int perPage) {
        PaginatedResponse<List<UserDto.UserWithProfileDto>> users = userService.getAllUsers(
                search, gender, roles, isAlumni, isCareTaker, active, bedRoomId, sortBy, sortDirection, page, perPage);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/public/users/change-password")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<UserDto> changeUserPassword(@Valid @RequestBody UserPasswordChangeDto userPasswordChangeDto) throws BadRequestException {
        UserDto updatedUser = userService.changeUserPassword(userPasswordChangeDto);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/admin/users/change-password")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> changeUserPasswordByAdmin(@Valid @RequestBody UserPasswordChangeDto.AdminChange userPasswordChangeDto) {
        UserDto updatedUser = userService.changeUserPasswordByAdmin(userPasswordChangeDto);
        return ResponseEntity.ok(updatedUser);
    }
}
