package uk.daliclass.product;

import uk.daliclass.product.common.Product;
import uk.daliclass.product.creation.CreateProduct;

public class ProductController {

    private final CreateProduct createProduct;

    public ProductController(CreateProduct createProduct) {
        this.createProduct = createProduct;
    }

    public void createProduct(Product product) {
        this.createProduct.accept(product);
    }
}
