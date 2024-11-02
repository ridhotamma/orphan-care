package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.orphancare.dashboard.dto.*;
import org.orphancare.dashboard.entity.*;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.exception.DataAlreadyExistsException;
import org.orphancare.dashboard.mapper.AddressMapper;
import org.orphancare.dashboard.mapper.GuardianMapper;
import org.orphancare.dashboard.mapper.UserMapper;
import org.orphancare.dashboard.repository.*;
import org.orphancare.dashboard.specification.UserSpecification;
import org.orphancare.dashboard.util.RequestUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final BedRoomRepository bedRoomRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final GuardianMapper guardianMapper;
    private final RequestUtil requestUtil;
    private final GuardianTypeRepository guardianTypeRepository;
    private final GuardianRepository guardianRepository;

    public UserDto.UserWithProfileDto createUser(CreateUserDto createUserDto) {
        if (userRepository.existsByEmail(createUserDto.getEmail())) {
            throw new DataAlreadyExistsException("Email is already in use");
        }
        if (userRepository.existsByUsername(createUserDto.getUsername())) {
            throw new DataAlreadyExistsException("Username is already in use");
        }

        if (profileRepository.existsByNikNumber(createUserDto.getNikNumber())) {
            throw new DataAlreadyExistsException("Nik Number is already in use");
        }

        BedRoom bedRoom = bedRoomRepository.findById(createUserDto.getBedRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Bedroom not found with id " + createUserDto.getBedRoomId()));

        User user = userMapper.toEntity(createUserDto);
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        user.setActive(createUserDto.isActive());

        Address address = addressMapper.toEntity(createUserDto.getAddress());
        Profile profile = userMapper.toProfileEntity(createUserDto);

        Guardian guardian;
        if (createUserDto.getGuardian().getId() != null) {
            guardian = guardianRepository.findById(createUserDto.getGuardian().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Guardian not found with id " + createUserDto.getGuardian().getId()));

            guardian.setFullName(createUserDto.getGuardian().getFullName());
            guardian.setPhoneNumber(createUserDto.getGuardian().getPhoneNumber());

            if (createUserDto.getGuardian().getAddress() != null) {
                guardian.setAddress(addressMapper.toEntity(createUserDto.getGuardian().getAddress()));
            }
            profile.setGuardian(guardian);
        } else {
            guardian = guardianMapper.toEntity(createUserDto.getGuardian());
        }

        profile.setUser(user);
        profile.setBedRoom(bedRoom);
        profile.setAddress(address);
        profile.setGuardian(guardian);
        profile.setCareTaker(createUserDto.isCareTaker());
        profile.setAlumni(createUserDto.isAlumni());
        profile.setKkNumber(createUserDto.getKkNumber());
        profile.setNikNumber(createUserDto.getNikNumber());
        profile.setOrphanType(createUserDto.getOrphanType());

        user.setProfile(profile);

        User savedUser = userRepository.save(user);
        return userMapper.toUserWithProfileDto(savedUser);
    }

    public UserDto updateUser(UUID userId, UpdateUserDto updateUserDto) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        if (updateUserDto.getEmail() != null && !existingUser.getEmail().equals(updateUserDto.getEmail())
                && userRepository.existsByEmail(updateUserDto.getEmail())) {
            throw new DataAlreadyExistsException("Email is already in use");
        }

        if (updateUserDto.getUsername() != null && !existingUser.getUsername().equals(updateUserDto.getUsername())
                && userRepository.existsByUsername(updateUserDto.getUsername())) {
            throw new DataAlreadyExistsException("Username is already in use");
        }

        userMapper.updateUserFromDto(updateUserDto, existingUser);

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDto(updatedUser);
    }

    public UserDto changeUserPassword(UserPasswordChangeDto changePasswordUserDto) throws BadRequestException {
        String currentUsername = requestUtil.getCurrentUsername();
        User existingUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + currentUsername));

        if (!passwordEncoder.matches(changePasswordUserDto.getOldPassword(), existingUser.getPassword())) {
            throw new BadRequestException("Old password is wrong");
        }

        existingUser.setPassword(passwordEncoder.encode(changePasswordUserDto.getNewPassword()));
        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDto(updatedUser);
    }

    public UserDto changeUserPasswordByAdmin(UserPasswordChangeDto.AdminChange adminChange) {
        User existingUser = userRepository.findById(adminChange.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + adminChange.getUserId()));
        existingUser.setPassword(passwordEncoder.encode(adminChange.getNewPassword()));
        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDto(updatedUser);
    }

    public void deleteUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        userRepository.deleteById(userId);
    }

    public UserDto getUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        return userMapper.toDto(user);
    }

    public PaginatedResponse<List<UserDto.UserWithProfileDto>> getAllUsers(
            String search, Gender gender, Boolean isAlumni, Boolean isCareTaker, Boolean active,
            UUID bedRoomId, String sortBy, String sortDirection, int page, int perPage) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, perPage, sort);

        Page<User> userPage = userRepository.findAll(
                UserSpecification.withSearchCriteria(search, gender, isAlumni, isCareTaker, active, bedRoomId),
                pageable
        );

        List<UserDto.UserWithProfileDto> userDtos = userPage.getContent().stream()
                .map(userMapper::toUserWithProfileDto)
                .collect(Collectors.toList());

        PaginatedResponse.Meta meta = new PaginatedResponse.Meta(
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getTotalPages()
        );

        return new PaginatedResponse<>(userDtos, meta);
    }
}
