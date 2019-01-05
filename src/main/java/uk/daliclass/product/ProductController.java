package uk.daliclass.product;

import uk.daliclass.product.common.Product;
import uk.daliclass.product.creation.CreateProduct;

import java.util.List;
import java.util.function.Supplier;

public class ProductController {

    private final CreateProduct createProduct;
    private final Supplier<List<Product>> getProducts;

    public ProductController(CreateProduct createProduct, Supplier<List<Product>> getProducts) {
        this.createProduct = createProduct;
        this.getProducts = getProducts;
    }

    public void createProduct(Product product) {
        this.createProduct.accept(product);
    }

    public List<Product> getProducts() {
        return this.getProducts.get();
    }
}
