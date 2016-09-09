package sklep.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Order {

    private final int id;
    private static int nextId = 1;
    private ObservableList<Product> products;
    private double price;

    public Order(Basket basket)
    {
        id = nextId;
        nextId++;

        products = FXCollections.observableArrayList(basket.getProducts());

        price = basket.getPrice();
    }



    public void cancelOrder()
    {
        int ii = products.size();
        for(int i = 0; i < ii; i++)
        {
            products.remove(0);
        }
        price = 0;

    }

    public void payOrder(User user) throws Exception
    {
        double change = price * -1;

            if(price > 0) user.getAccount().changeBalance(change);
            else throw new Exception("Kwota zamówienia była mniejsza lub równa 0");
            cancelOrder();

        // wysyłanie produktów

    }

    public int getId()
    {
        return this.id;
    }

    public Double getPrice()
    {
        return this.price;
    }

    public ObservableList<Product> getProducts()
    {
        return products;
    }

}