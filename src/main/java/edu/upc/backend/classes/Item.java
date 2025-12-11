package edu.upc.backend.classes;

public class Item {

    int id;
    String name;
    int durability;
    int price;
    String emoji;
    String description;
    int quantity;

    public Item() {}
    /*public Item(int id, String name, int durability, int price)
    {
        setId(id);
        setDurability(Item.maxDurability);
    }

     */

    public Item(String name, int durability, int price, String emoji, String description, int quantity)
    {
        this.id = 0;
        this.name = name;
        this.durability = durability;
        this.price = price;
        this.emoji = emoji;
        this.description = description;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(Integer durability) {
        this.durability = durability;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("Item with id: %d is %s for only %d . Look it is a great deal!", getId(), getName(), getPrice());
    }
}
