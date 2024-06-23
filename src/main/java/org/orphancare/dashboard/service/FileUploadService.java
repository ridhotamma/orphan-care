package org.orphancare.dashboard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.config.AwsPropertiesConfig;
import org.orphancare.dashboard.dto.UploadResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class FileUploadService {

    private final S3Client s3Client;
    private final AwsPropertiesConfig awsProperties;

    public UploadResponse uploadFile(MultipartFile file) throws IOException {
        String uniqueFileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(awsProperties.getBucketName())
                .key(uniqueFileName)
                .acl("public-read")
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        URL fileUrl = s3Client.utilities().getUrl(builder -> builder.bucket(awsProperties.getBucketName()).key(uniqueFileName));
        return new UploadResponse(fileUrl.toString(), uniqueFileName);
    }
}
