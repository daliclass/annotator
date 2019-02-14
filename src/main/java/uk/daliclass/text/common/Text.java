package uk.daliclass.text.common;

import uk.daliclass.annotator.common.domain.Idable;

public class Text implements Idable {
    private String id;
    private String text;
    private Integer itemId;

    public Text() {}

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

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}
