package uk.daliclass.product.common;


import uk.daliclass.annotator.common.domain.Idable;


public class Product implements Idable {
    private Integer itemId;
    private String name;
    private String description;
    private Double cost;
    private String imageUrl;

    public Product() {
    }

    public Product(Integer itemId, String name, String description, Double cost, String imageUrl) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.imageUrl = imageUrl;
    }

    public Double getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (!(o instanceof Product)) return false;
        Product other = (Product) o;
        if (this.itemId != other.itemId) return false;
        if (!(this.cost == null && other.cost == null)) {
            if (!this.cost.equals(other.cost)) return false;
        }
        if (this.description != other.description) return false;
        if (this.imageUrl != other.imageUrl) return false;
        if (this.name != other.name) return false;
        return true;
    }
}
