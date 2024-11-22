package com.omer.candidate;

import com.omer.candidate.utility.FileUtils;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
    info = @Info(
        title = "Job-Vault Application REST API Documentation",
        description = "Job-Vault Application REST API Documentation",
        version = "v1",
        contact = @Contact(
            name = "Ömer ALTAN",
            email = "omeraltan66@gmail.com",
            url= "https://github.com/omeraltan"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://github.com/omeraltan"
        )
    ),
    externalDocs = @ExternalDocumentation(
        description = "Job-Vault Application REST API Documentation",
        url = "https://www.job-vault.com/swagger-ui.html"
    )
)
public class ApiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String resumesPath = System.getProperty("user.dir") + "/resumes";
        FileUtils.clearDirectory(resumesPath);
        System.out.println("Resumes directory cleared at startup.");

        String uploadDir = System.getProperty("user.dir") + "/resumes";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Veritabanı örnekleri ile PDF dosyalarını oluştur
        createPdf("ahmet_yilmaz.pdf", uploadDir);
        createPdf("ayse_kaya.pdf", uploadDir);
        createPdf("mehmet_demir.pdf", uploadDir);
        createPdf("fatma_celik.pdf", uploadDir);
        createPdf("ali_guzel.pdf", uploadDir);
        createPdf("zeynep_aydin.pdf", uploadDir);
        createPdf("burak_kurt.pdf", uploadDir);
        createPdf("elif_ozturk.pdf", uploadDir);
        createPdf("mustafa_sarac.pdf", uploadDir);
        createPdf("selma_turan.pdf", uploadDir);
    }

    private void createPdf(String fileName, String directoryPath) {
        try {
            String filePath = Paths.get(directoryPath, fileName).toString();
            String pdfContent = "This is a PDF file for " + fileName;
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                byte[] content = pdfContent.getBytes();
                fos.write(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
