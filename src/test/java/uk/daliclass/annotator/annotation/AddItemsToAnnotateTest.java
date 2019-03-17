package uk.daliclass.annotator.annotation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.daliclass.annotator.common.domain.ItemSet;
import uk.daliclass.annotator.common.storage.Log;
import uk.daliclass.text.common.Text;

import java.util.ArrayList;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class AddItemsToAnnotateTest {

    @Mock
    Log<ItemSet<Text>> log;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void whenProvidedAItemSetThenPopulateTheItemIds() {
        ItemSet<Text> itemSet = new ItemSet<>();
        itemSet.setItems(
                new ArrayList<>() {{
                    add(new Text("1", "some text 1"));
                    add(new Text("1", "some text 2"));
                }}
        );
        ItemSet<Text> expectedItemSet = new ItemSet<>();
        expectedItemSet.setItems(
                new ArrayList<>() {{
                    add(new Text("1", "some text 1", 0));
                    add(new Text("1", "some text 2", 1));
                }}
        );
        AddItemsToAnnotate<Text> addItemsToAnnotate = new AddItemsToAnnotate<>(log);
        addItemsToAnnotate.accept(itemSet);
        verify(log, times(1)).create(expectedItemSet);
    }
}
