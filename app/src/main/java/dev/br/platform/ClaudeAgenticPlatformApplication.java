package dev.br.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "dev.br.platform")
@EnableJpaRepositories(basePackages = "dev.br.platform.repository")
@EntityScan(basePackages = "dev.br.platform.domain")
public class ClaudeAgenticPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClaudeAgenticPlatformApplication.class, args);
    }
}