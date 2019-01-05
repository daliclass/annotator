package uk.daliclass.annotator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.daliclass.annotator.common.AnnotatorService;
import uk.daliclass.product.common.Product;

import java.nio.file.Paths;

@Configuration
public class AnnotatorConfig {
    @Bean
    public AnnotatorService<Product> annotatorService() {
        return new AnnotatorService<>(
                Paths.get("./facts.log"),
                Paths.get("./facts.log"),
                Product.class);
    }
}
