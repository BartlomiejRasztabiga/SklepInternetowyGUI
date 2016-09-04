package sklep.model;

import javafx.beans.property.*;

public class Product
{
    private StringProperty name;
    private DoubleProperty price;
    private IntegerProperty id;

    public Product(int id, String n, double p)
    {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(n);
        this.price = new SimpleDoubleProperty(p);
    }


    public StringProperty name()
    {
        return name;
    }

    public String getName()
    {
        return name.get();
    }

    public void setName(String name) { this.name.set(name);}

    public DoubleProperty price()
    {
        return price;
    }

    public double getPrice()
    {
        return price.get();
    }

    public void setPrice(double price) { this.price.set(price);}

    public IntegerProperty id()
    {
        return id;
    }

    public int getId()
    {
        return id.get();
    }

    public void setId(int id) { this.id.set(id);}

    public String describe()
    {
        return "name=" + this.getName() + " id=" + this.getId() + " price=" + this.getPrice();
    }
}
