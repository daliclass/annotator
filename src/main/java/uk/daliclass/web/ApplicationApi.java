package uk.daliclass.web;

import org.springframework.web.bind.annotation.*;
import uk.daliclass.annotator.common.AnnotatorService;
import uk.daliclass.annotator.common.domain.ItemAnnotation;
import uk.daliclass.annotator.common.domain.ItemSet;
import uk.daliclass.annotator.common.domain.requests.AnnotationsRequest;
import uk.daliclass.annotator.common.domain.requests.ItemToAnnotateRequest;
import uk.daliclass.annotator.common.domain.views.AnnotatorView;
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

    @GetMapping("/itemset/{uuid}/item/{id}/annotation/{annotator}")
    public AnnotatorView<Text> getProductToAnnotate(@PathVariable("uuid") String uuid,
                                                    @PathVariable("id") Integer itemId,
                                                    @PathVariable("annotator") String annotator) {
        return this.textAnnotatorService.getItemToAnnotate(
                new ItemToAnnotateRequest(UUID.fromString(uuid), annotator, itemId));
    }

    @PostMapping(value = "/text/annotation", consumes = "application/json", produces = "application/json")
    public void addAnnotationsToItem(@RequestBody ItemAnnotation itemAnnotation) {
        this.textAnnotatorService.addAnnotationsToItem(itemAnnotation);
    }

    @RequestMapping(value = "/annotate/text", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public void addProductsToAnnotate(@RequestBody ItemSet<Text> itemSet) {
        this.textAnnotatorService.addItemsToAnnotate(itemSet);
    }

    @GetMapping("/annotation")
    public void getAnnotations(@RequestBody AnnotationsRequest annotationsRequest) {
        this.textAnnotatorService.getAnnotations(annotationsRequest);
    }

    @GetMapping("/itemsets")
    public List<ItemSetView> getTextItemSets() {
        return this.textAnnotatorService.getItemSets();
    }
}