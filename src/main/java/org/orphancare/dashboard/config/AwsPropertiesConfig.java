package org.orphancare.dashboard.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws")
@Data
public class AwsPropertiesConfig {
    private String accessKeyId;
    private String secretKey;
    private String region;
    private String endpoint;
    private String bucketName;
}
