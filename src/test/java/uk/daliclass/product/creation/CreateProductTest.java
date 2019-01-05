package uk.daliclass.product.creation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.daliclass.annotator.common.storage.Log;
import uk.daliclass.product.common.Product;

import java.util.ArrayList;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreateProductTest {

    @Mock
    Log<Product> productLog;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void whenProvidedAUniqueProductThenSaveTheProduct() {
        CreateProduct createProduct = new CreateProduct(productLog);
        Product product = new Product();
        createProduct.accept(product);
        verify(productLog, times(1)).create(product);
    }

    @Test
    public void whenProvidedADuplicateProductThenDoNotSaveProduct() {
        CreateProduct createProduct = new CreateProduct(productLog);
        Product productOne = new Product();
        Product productTwo = new Product();
        when(productLog.read()).thenReturn(new ArrayList<>() {{ add(productOne); }});
        createProduct.accept(productTwo);
        verify(productLog, times(0)).create(productTwo);
    }
}
