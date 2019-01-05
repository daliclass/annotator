package uk.daliclass.product.creation;

import uk.daliclass.annotator.common.storage.Log;
import uk.daliclass.product.common.Product;

import java.util.function.Consumer;

public class CreateProduct implements Consumer<Product> {

    private final Log<Product> productLog;

    public CreateProduct(Log<Product> productLog) {
        this.productLog = productLog;
    }

    public void accept(Product product) {
        if (this.productLog.read().contains(product)) {
            return;
        }
        this.productLog.create(product);
    }
}
