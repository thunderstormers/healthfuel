package com.thunderstormers.healthfuel.service;

import com.qkyrie.markdown2pdf.Markdown2PdfConverter;
import com.thunderstormers.healthfuel.models.DietMealsDTO;
import com.thunderstormers.healthfuel.models.UserDietDetailsRequest;
import com.thunderstormers.healthfuel.service.error.PdfCreationException;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Map;
import java.util.UUID;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DietPlanReportService {

    public static final String GENERATED_REPORT_DIRECTORY = "generated-reports";
    public static final String GENERATED_REPORT_FILENAME = "Diet Plan.pdf";

    ChatClient chat;

    public DietPlanReportService(ChatClient.Builder builder) {
        chat = builder.build();
    }

    @Value("classpath:/prompts/meals-in-markdown.st")
    private Resource markdownMealsPromptResource;

    public String exportDietMealsAsPdf(UserDietDetailsRequest inputs, DietMealsDTO meals) {
        var prompt = new PromptTemplate(markdownMealsPromptResource, Map.of(
                "userInputs", inputs,
                "dietMealPlan", meals)).create();

        var response = chat.prompt(prompt).call().content();

        var markdown = response.strip().replaceAll("(?s)^```markdown\n?|\n?```$", "");

        var id = UUID.randomUUID().toString();

        // Create PDF document
        var pdfDirectory = new File(GENERATED_REPORT_DIRECTORY, id);
        if (!pdfDirectory.exists()) {
            pdfDirectory.mkdirs();
        }


        try {
            var md = File.createTempFile(id, ".md");

            try (var fos = new FileOutputStream(md)) {
                fos.write(markdown.getBytes());
            }

            File pdf = new File(pdfDirectory, GENERATED_REPORT_FILENAME);
            try (FileOutputStream fos = new FileOutputStream(pdf)) {
                Markdown2PdfConverter
                        .newConverter()
                        .readFrom(() -> markdown)
                        .writeTo(pdfBytes -> {
                            try {
                                fos.write(pdfBytes);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        })
                        .doIt();
                log.info("PDF created successfully!");
            }
            return id;
        } catch (Exception e) {
            log.error("Error creating pdf: ", e);
            throw new PdfCreationException("Error creating pdf");
        }
    }

}
