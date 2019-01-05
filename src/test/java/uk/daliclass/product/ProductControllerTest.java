package uk.daliclass.product;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.daliclass.product.common.Product;
import uk.daliclass.product.creation.CreateProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProductControllerTest {

    @Mock
    CreateProduct createProduct;

    @Mock
    Supplier<List<Product>> getProducts;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void whenProvidedAProductThenCreateAProduct() {
        Product product = new Product();
        ProductController productController = new ProductController(createProduct, getProducts);
        productController.createProduct(product);
        verify(createProduct, times(1)).accept(product);
    }

    @Test
    public void whenGettingProductsThenGetAllProducts() {
        Product product = new Product();
        ProductController productController = new ProductController(createProduct, getProducts);
        when(getProducts.get()).thenReturn(new ArrayList<>() {{ add(product); }});
        productController.getProducts();
    }
}
