package uk.daliclass.product;

import uk.daliclass.annotator.common.storage.Log;
import uk.daliclass.product.common.Product;

import java.util.List;
import java.util.function.Supplier;

public class GetProducts implements Supplier<List<Product>> {

    private final Log<Product> productLog;

    public GetProducts(Log<Product> productLog) {
        this.productLog = productLog;
    }

    @Override
    public List<Product> get() {
        return productLog.read();
    }
}
