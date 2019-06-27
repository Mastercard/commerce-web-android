package com.us.masterpass.merchantapp.data.device;

import java.io.Serializable;
import java.util.Date;

/**
 * Object to save local data on a model that represent the shopping cart.
 * <p>
 * Created by Sebastian Farias on 10-10-17.
 */
public class CartLocalObject implements Serializable {

    private static final long serialVersionUID = -285346889504335579L;
    private String itemId;
    private int totalCount;
    private double totalPrice;
    private String productId;
    private String name;
    private double price;
    private double salePrice;
    private String image;
    private String description;
    private Date dateAdded;
    private boolean selected;

    /**
     * Gets item id of selected product on shopping cart.
     *
     * @return the item id
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * Sets item id to item on shopping cart.
     *
     * @param itemId the item id
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * Gets total count of products on shopping cart.
     *
     * @return the total count
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * Sets total count to item on shopping cart.
     *
     * @param totalCount the total count
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * Gets total price of products on shopping cart.
     *
     * @return the total price
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets total price to item on shopping cart.
     *
     * @param totalPrice the total price
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Gets product id of selected product on shopping cart.
     *
     * @return the product id
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Sets product id to item on shopping cart.
     *
     * @param productId the product id
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * Gets name of selected product on shopping cart.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name to item on shopping cart.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets price of selected product on shopping cart.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets price to item on shopping cart.
     *
     * @param price the price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets sale price of selected product on shopping cart.
     *
     * @return the sale price
     */
    public double getSalePrice() {
        return salePrice;
    }

    /**
     * Sets sale price to item on shopping cart.
     *
     * @param salePrice the sale price
     */
    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * Gets image of selected product on shopping cart.
     *
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * Set image to item on shopping cart.
     *
     * @param image the image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets description of selected product on shopping cart.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description to item on shopping cart.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets date added of selected product on shopping cart.
     *
     * @return the date added
     */
    public Date getDateAdded() {
        return dateAdded;
    }

    /**
     * Set date added to item on shopping cart.
     *
     * @param dateAdded the date added
     */
    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    /**
     * Check if is already selected on shopping cart.
     *
     * @return boolean with value of selection
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Set selected to item on shopping cart.
     *
     * @param selected the selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
