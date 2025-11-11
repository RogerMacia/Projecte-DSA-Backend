package edu.upc.backend.classes;

public abstract class Item {
    static int maxDurability = 20;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDurabilidad() {
        return durabilidad;
    }

    public void setDurabilidad(int durabilidad) {
        this.durabilidad = durabilidad;
    }

    int id;
    int durabilidad;

    public Item() {}
    public Item(int id)
    {
        setId(id);
        setDurabilidad(Item.maxDurability);
    }

}
