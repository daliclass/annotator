package uk.daliclass.web;

import org.springframework.web.bind.annotation.*;
import uk.daliclass.annotator.common.AnnotatorService;
import uk.daliclass.annotator.common.domain.ItemSet;
import uk.daliclass.annotator.common.domain.requests.ItemToAnnotateRequest;
import uk.daliclass.annotator.common.domain.views.AnnotatorView;
import uk.daliclass.annotator.common.domain.ItemAnnotation;
import uk.daliclass.annotator.common.domain.views.ItemSetView;
import uk.daliclass.text.common.Text;

import java.util.List;
import java.util.UUID;

@RestController
public class ApplicationApi {

    private final AnnotatorService<Text> textAnnotatorService;

    public ApplicationApi(AnnotatorService<Text> textAnnotatorService) {
        this.textAnnotatorService = textAnnotatorService;
    }

    @GetMapping("/product/{id}/annotation/{annotator}")
    public AnnotatorView<Text> getProductToAnnotate(@PathVariable("uuid") String uuid,
                                                       @PathVariable("id") Integer itemId,
                                                       @PathVariable("annotator") String annotator) {
        return this.textAnnotatorService.getItemToAnnotate(
                new ItemToAnnotateRequest(UUID.fromString(uuid), annotator, itemId));
    }

    @PostMapping("/text/annotation")
    public void addAnnotationsToProduct(ItemAnnotation itemAnnotation) {
        this.textAnnotatorService.addAnnotationsToItem(itemAnnotation);
    }

    @RequestMapping(value = "/annotate/text", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public void addProductsToAnnotate(@RequestBody ItemSet<Text> itemSet) {
        this.textAnnotatorService.addItemsToAnnotate(itemSet);
    }

    @GetMapping("/itemsets")
    public List<ItemSetView> getTextItemSets() {
        return this.textAnnotatorService.getItemSets();
    }
}