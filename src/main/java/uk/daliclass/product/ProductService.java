package uk.daliclass.product;

import uk.daliclass.annotator.common.storage.FactFileSystemStorage;
import uk.daliclass.annotator.common.storage.Log;
import uk.daliclass.product.common.Product;
import uk.daliclass.product.creation.CreateProduct;

import java.nio.file.Path;
import java.util.List;

public class ProductService {

    private final ProductController productController;

    public ProductService(Path productPath) {
        Log<Product> productLog = new FactFileSystemStorage<>(productPath, Product.class);
        this.productController = new ProductController(
                new CreateProduct(productLog), new GetProducts(productLog)
        );
    }

    public void createProduct(Product product) {
        this.productController.createProduct(product);
    }

    public List<Product> getProducts() {
        return this.productController.getProducts();
    }

}
