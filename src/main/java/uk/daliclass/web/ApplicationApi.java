package uk.daliclass.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.daliclass.annotator.common.AnnotatorService;
import uk.daliclass.annotator.common.domain.AnnotatorView;
import uk.daliclass.annotator.common.domain.ItemAnnotation;
import uk.daliclass.product.ProductService;
import uk.daliclass.product.common.Product;

import java.util.List;

@RestController
public class ApplicationApi {

    private final AnnotatorService<Product> annotatorService;
    private final ProductService productService;

    public ApplicationApi(
            AnnotatorService<Product> annotatorService,
            ProductService productService
    ) {
        this.annotatorService = annotatorService;
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return this.productService.getProducts();
    }

    @PostMapping("/product")
    public void createProduct(Product product) {
        this.productService.createProduct(product);
    }

    @GetMapping("/product/{id}/annotation/{annotator}")
    public AnnotatorView<Product> getProductToAnnotate(@PathVariable("id") Integer itemId, @PathVariable("annotator") String annotator) {
        return this.annotatorService.getItemToAnnotate(annotator, itemId);
    }

    @PostMapping("/product/annotation")
    public void annotateProduct(ItemAnnotation itemAnnotation) {
        this.annotatorService.addAnnotationsToItem(itemAnnotation);
    }

    @PostMapping("/annotate/products")
    public Integer annotateProducts(List<Product> products) {
        Integer itemId = products.get(0).getItemId();
        this.annotatorService.addItemsToAnnotate(this.productService.getProducts());
        return itemId;
    }
}
