package sklep.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Basket {

    private double price = 0;
    private ObservableList<Product> products = FXCollections.observableArrayList();

    public Basket()
    {

    }

    public void addProduct(Product product)
    {

        try {
            if (product == null) throw new NullPointerException("product wskazuje null");
            products.add(product);
            this.setSummaryPrice();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public void removeProduct(int id)
    {
        Product[] x = new Product[products.size()];
        products.toArray(x);
        for(int i = 0; i < x.length; i++)
        {
            if(x[i].getId() == id)
            {
                products.remove(i);
                break;
            }
        }
        this.setSummaryPrice();
    }

    public void printBasket()
    {
        System.out.println("Twój koszyk");
        Product[] x = new Product[products.size()];
        products.toArray(x);
        for(Product element : x)
        {
            System.out.println(element.describe());
        }

        setSummaryPrice();
        System.out.println(this.getPrice());
    }

    public void setSummaryPrice()
    {
        price = 0;
        Product[] x = new Product[products.size()];
        products.toArray(x);
        for(Product element : x)
        {
            price += element.getPrice();
    }
        price *= 100;
        price = Math.round(price);
        price /= 100;
    }

    public double getPrice()
    {
        this.setSummaryPrice();
        return price;
    }

    public ObservableList<Product> getProducts()
    {
        return products;
    }



}
