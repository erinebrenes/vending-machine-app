package com.techelevator;

import java.math.BigDecimal;

public class Product implements Sellable {

    private String name;
    private BigDecimal price;
    private String slotId;
    private String snackType;
    protected String message;
    public int quantity;

    public Product(String slotId, String name, BigDecimal price, String snackType) {
        this.slotId = slotId;
        this.name = name;
        this.price = price;
        this.snackType = snackType;
        this.quantity = 5;
    }

    public Product() {}

    public String getMessage() {
        String message = "";

        switch (snackType){
            case "Chip":
                message = "Crunch Crunch, Yum!";
                break;
            case "Candy":
                message = "Munch Much, Yum!";
                break;
            case "Drink":
                message = "Glug Glug, Yum!";
                break;
            case "Gum":
                message = "Chew Chew,Yum!";
                break;
            default:
                message = "This product type is not recognized";
                break;
        }
        return message;
    }

    public int decrement(){
        if(quantity > 0) {
            quantity = quantity - 1;
        }
        return quantity;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSlotId() {
        return slotId;
    }

    public String getSnackType() {
        return snackType;
    }

    public int getQuantity() {
        return quantity;
    }
}