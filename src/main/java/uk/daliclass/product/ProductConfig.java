package uk.daliclass.product;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Paths;

@Configuration
public class ProductConfig {
    @Bean
    public ProductService productService() {
        return new ProductService(Paths.get("./products.log"));
    }
}
