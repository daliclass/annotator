package uk.daliclass.product;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.daliclass.product.common.Product;
import uk.daliclass.product.creation.CreateProduct;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProductControllerTest {

    @Mock
    CreateProduct createProduct;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void whenProvidedAProductThenCreateAProduct() {
        Product product = new Product();
        ProductController productController = new ProductController(createProduct);
        productController.createProduct(product);
        verify(createProduct, times(1)).accept(product);
    }
}
