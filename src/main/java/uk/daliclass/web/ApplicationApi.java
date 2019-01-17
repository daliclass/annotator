package uk.daliclass.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.daliclass.annotator.common.AnnotatorService;
import uk.daliclass.annotator.common.domain.ItemSet;
import uk.daliclass.annotator.common.domain.requests.ItemToAnnotateRequest;
import uk.daliclass.annotator.common.domain.views.AnnotatorView;
import uk.daliclass.annotator.common.domain.ItemAnnotation;
import uk.daliclass.product.ProductService;
import uk.daliclass.product.common.Product;

import java.util.List;
import java.util.UUID;

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
    public AnnotatorView<Product> getProductToAnnotate(@PathVariable("uuid") String uuid,
                                                       @PathVariable("id") Integer itemId,
                                                       @PathVariable("annotator") String annotator) {
        return this.annotatorService.getItemToAnnotate(
                new ItemToAnnotateRequest(UUID.fromString(uuid), annotator, itemId));
    }

    @PostMapping("/product/annotation")
    public void addAnnotationsToProduct(ItemAnnotation itemAnnotation) {
        this.annotatorService.addAnnotationsToItem(itemAnnotation);
    }

    @PostMapping("/annotate/products")
    public void addProductsToAnnotate(ItemSet<Product> itemSet) {
        this.annotatorService.addItemsToAnnotate(new ItemSet<>("name", this.productService.getProducts()));
    }
}
