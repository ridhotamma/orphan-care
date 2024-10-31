package org.orphancare.dashboard.config;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.entity.*;
import org.orphancare.dashboard.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@RequiredArgsConstructor
public class DataInitializerConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UnitRepository unitRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final BedRoomTypeRepository bedRoomTypeRepository;
    private final GuardianTypeRepository guardianTypeRepository;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.profile.fullName}")
    private String adminFullName;

    @Value("${admin.profile.picture}")
    private String adminProfilePicture;

    @Value("${admin.profile.isCareTaker}")
    private boolean adminIsCareTaker;

    @Value("${admin.profile.isAlumni}")
    private boolean adminIsAlumni;

    @Value("${admin.profile.gender}")
    private String adminGender;

    @Override
    @Transactional
    public void run(String... args) {
        initializeAdminUser();
        initializeUnits();
        initializeDocumentTypes();
        initializeBedRoomTypes();
        initializeGuardianTypes();
    }

    private void initializeAdminUser() {
        Optional<User> existingUserByEmail = userRepository.findByEmail(adminEmail);
        Optional<User> existingUserByUsername = userRepository.findByUsername(adminUsername);

        if (existingUserByEmail.isEmpty() && existingUserByUsername.isEmpty()) {
            User user = new User();
            user.setEmail(adminEmail);
            user.setPassword(passwordEncoder.encode(adminPassword));
            user.setUsername(adminUsername);
            user.setActive(true);

            Set<RoleType> roles = new HashSet<>();
            roles.add(RoleType.ROLE_ADMIN);
            user.setRoles(roles);

            Profile profile = new Profile();
            profile.setFullName(adminFullName);
            profile.setGender(Gender.valueOf(adminGender));
            profile.setUser(user);
            profile.setProfilePicture(adminProfilePicture);
            profile.setAlumni(adminIsAlumni);
            profile.setCareTaker(adminIsCareTaker);
            user.setProfile(profile);

            try {
                userRepository.save(user);
                System.out.println("Admin user created successfully");
            } catch (Exception e) {
                System.out.println("Failed to create admin user: " + e.getMessage());
            }
        } else {
            System.out.println("Admin user already exists");
        }
    }

    private void initializeUnits() {
        List<Unit> units = Arrays.asList(
                createUnit("Meters", "LENGTH"),
                createUnit("Kilograms", "WEIGHT"),
                createUnit("Square Meters", "AREA"),
                createUnit("Celsius", "TEMPERATURE"),
                createUnit("Pieces", "QUANTITY")
        );

        for (Unit unit : units) {
            try {
                if (!unitRepository.existsByNameOrType(unit.getName(), unit.getType())) {
                    unitRepository.save(unit);
                    System.out.println("Unit created: " + unit.getName());
                }
            } catch (Exception e) {
                System.out.println("Failed to create unit " + unit.getName() + ": " + e.getMessage());
            }
        }
    }

    private void initializeDocumentTypes() {
        List<DocumentType> documentTypes = Arrays.asList(
                createDocumentType("National ID", "IDENTIFICATION", true),
                createDocumentType("Birth Certificate", "BIRTH_CERT", true),
                createDocumentType("Medical Records", "MEDICAL", true),
                createDocumentType("School Records", "EDUCATION", false),
                createDocumentType("Guardian Documents", "LEGAL", true)
        );

        for (DocumentType docType : documentTypes) {
            try {
                if (!documentTypeRepository.existsByNameOrType(docType.getName(), docType.getType())) {
                    documentTypeRepository.save(docType);
                    System.out.println("Document type created: " + docType.getName());
                }
            } catch (Exception e) {
                System.out.println("Failed to create document type " + docType.getName() + ": " + e.getMessage());
            }
        }
    }

    private void initializeBedRoomTypes() {
        List<BedRoomType> bedRoomTypes = Arrays.asList(
                createBedRoomType("Single Bed", "SINGLE"),
                createBedRoomType("Double Bed", "DOUBLE"),
                createBedRoomType("Dormitory", "MULTIPLE"),
                createBedRoomType("Special Needs", "SPECIAL"),
                createBedRoomType("Isolation", "MEDICAL")
        );

        for (BedRoomType bedRoomType : bedRoomTypes) {
            try {
                if (!bedRoomTypeRepository.existsByNameOrType(bedRoomType.getName(), bedRoomType.getType())) {
                    bedRoomTypeRepository.save(bedRoomType);
                    System.out.println("Bedroom type created: " + bedRoomType.getName());
                }
            } catch (Exception e) {
                System.out.println("Failed to create bedroom type " + bedRoomType.getName() + ": " + e.getMessage());
            }
        }
    }

    private void initializeGuardianTypes() {
        List<GuardianType> guardianTypes = Arrays.asList(
                createGuardianType("Biological Parent", "BIOLOGICAL"),
                createGuardianType("Foster Parent", "FOSTER"),
                createGuardianType("Legal Guardian", "LEGAL"),
                createGuardianType("Temporary Guardian", "TEMPORARY"),
                createGuardianType("Institution", "INSTITUTION")
        );

        for (GuardianType guardianType : guardianTypes) {
            try {
                if (!guardianTypeRepository.existsByNameOrType(guardianType.getName(), guardianType.getType())) {
                    guardianTypeRepository.save(guardianType);
                    System.out.println("Guardian type created: " + guardianType.getName());
                }
            } catch (Exception e) {
                System.out.println("Failed to create guardian type " + guardianType.getName() + ": " + e.getMessage());
            }
        }
    }

    private Unit createUnit(String name, String type) {
        Unit unit = new Unit();
        unit.setName(name);
        unit.setType(type);
        return unit;
    }

    private DocumentType createDocumentType(String name, String type, boolean isMandatory) {
        DocumentType documentType = new DocumentType();
        documentType.setName(name);
        documentType.setType(type);
        documentType.setMandatory(isMandatory);
        return documentType;
    }

    private BedRoomType createBedRoomType(String name, String type) {
        BedRoomType bedRoomType = new BedRoomType();
        bedRoomType.setName(name);
        bedRoomType.setType(type);
        return bedRoomType;
    }

    private GuardianType createGuardianType(String name, String type) {
        GuardianType guardianType = new GuardianType();
        guardianType.setName(name);
        guardianType.setType(type);
        return guardianType;
    }
}
