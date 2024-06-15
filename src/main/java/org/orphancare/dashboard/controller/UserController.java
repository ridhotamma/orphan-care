package org.orphancare.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.PaginatedResponse;
import org.orphancare.dashboard.dto.UserRequestDto;
import org.orphancare.dashboard.dto.UserResponseDto;
import org.orphancare.dashboard.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public PaginatedResponse<UserResponseDto> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return userService.getAllUsers(page, size);
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto userDto) {
        return userService.createUser(userDto);
    }

    @PutMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable UUID id, @RequestBody UserRequestDto userDetails) {
        return userService.updateUser(id, userDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}
