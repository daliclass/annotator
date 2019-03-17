package uk.daliclass.text.common;

import lombok.EqualsAndHashCode;
import uk.daliclass.annotator.common.domain.Idable;

@EqualsAndHashCode
public class Text implements Idable {
    private String id;
    private String text;
    private Integer itemId;

    public Text() {
    }

    public Text(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public Text(String id, String text, Integer itemId) {
        this(id, text);
        this.itemId = itemId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Integer getItemId() {
        return itemId;
    }

    @Override
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}
