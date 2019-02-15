package uk.daliclass.annotator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.daliclass.annotator.common.AnnotatorService;
import uk.daliclass.text.common.Text;

import java.nio.file.Paths;

@Configuration
public class AnnotatorConfig {
    @Bean
    public AnnotatorService<Text> annotatorService() {
        return new AnnotatorService<>(
                Paths.get("./textItemSets.log"),
                Paths.get("./textFacts.log"),
                Text.class);
    }
}