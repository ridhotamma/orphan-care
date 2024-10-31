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
    private final DonationTypeRepository donationTypeRepository;

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
        initializeDonationTypes();
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
                createUnit("Rupiah (IDR)", "RUPIAH"),
                createUnit("Dollar (USD)", "DOLLAR"),
                createUnit("Meter", "LENGTH"),
                createUnit("Kilogram", "WEIGHT"),
                createUnit("Box", "BOX"),
                createUnit("Pcs", "QUANTITY")
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
                createDocumentType("Ijazah SD", "ELEMENTARY_SCHOOL_CERTIFICATE", false),
                createDocumentType("Ijazah SMP", "JUNIOR_HIGH_SCHOOL_CERTIFICATE", false),
                createDocumentType("Ijazah SMA", "SENIOR_HIGH_SCHOOL_CERTIFICATE", false),
                createDocumentType("Rekaman Medis", "MEDICAL_RECORD", false),
                createDocumentType("Data Rapot Siswa", "ACADEMIC_REPORT", false),
                createDocumentType("Kartu Keluarga", "FAMILY_IDENTIFICATION_ID", true),
                createDocumentType("Akta Kelahiran", "BIRTH_CERTIFICATE", false)
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
                createBedRoomType("Kamar Anak Asuh Laki-laki", "BEDROOM_CHILD_MALE"),
                createBedRoomType("Kamar Anak Asuh Perempuan", "BEDROOM_CHILD_FEMALE"),
                createBedRoomType("Kamar Pengasuh Laki-laki", "BEDROOM_CARETAKER_MALE"),
                createBedRoomType("Kamar Pengasuh Perempuan", "BEDROOM_CARETAKER_FEMALE"),
                createBedRoomType("Kamar Tamu", "BEDROOM_GUEST"),
                createBedRoomType("Kamar Sewa", "BEDROOM_RENTAL")
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
                createGuardianType("Ayah Kandung", "BIOLOGICAL_FATHER"),
                createGuardianType("Ibu Kandung", "BIOLOGICAL_MOTHER"),
                createGuardianType("Saudara Perempuan", "SISTER"),
                createGuardianType("Saudara Laki-laki", "BROTHER"),
                createGuardianType("Paman", "UNCLE"),
                createGuardianType("Bibi", "AUNT"),
                createGuardianType("Ayah Tiri", "STEP_FATHER"),
                createGuardianType("Ibu Tiri", "STEP_MOTHER"),
                createGuardianType("Lainnya", "OTHER")
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

    private void initializeDonationTypes() {
        List<DonationType> donationTypes = Arrays.asList(
                createDonationType("Uang", "MONETARY"),
                createDonationType("Makanan", "CONSUMABLE"),
                createDonationType("Pakaian", "WEARABLE"),
                createDonationType("Material Edukasi", "EDUCATIONAL"),
                createDonationType("Alat Kesehatan", "MEDICAL"),
                createDonationType("Furnitur", "FURNITURE"),
                createDonationType("Elektronik", "ELECTRONIC"),
                createDonationType("Mainan", "RECREATIONAL"),
                createDonationType("Lainnya", "OTHER")
        );

        for (DonationType donationType : donationTypes) {
            try {
                if (!donationTypeRepository.existsByNameOrType(donationType.getName(), donationType.getType())) {
                    donationTypeRepository.save(donationType);
                    System.out.println("Donation type created: " + donationType.getName());
                }
            } catch (Exception e) {
                System.out.println("Failed to create donation type " + donationType.getName() + ": " + e.getMessage());
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

    private DonationType createDonationType(String name, String type) {
        DonationType donationType = new DonationType();
        donationType.setName(name);
        donationType.setType(type);
        return donationType;
    }
}
