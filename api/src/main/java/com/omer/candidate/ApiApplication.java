package com.omer.candidate;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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
        String filePath = Paths.get(directoryPath, fileName).toString();
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.add(new Paragraph("This is a PDF file for " + fileName));
            System.out.println("PDF created: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
