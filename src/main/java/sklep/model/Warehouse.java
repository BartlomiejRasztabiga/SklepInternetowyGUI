package sklep.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Warehouse {
    private ObservableList<Product> products = FXCollections.observableArrayList();

    public Warehouse()
    {

    }

    public Warehouse(ObservableList<Product> products)
    {
        this.products = products;
    }

    public Product getProduct(int id)
    {
        Product[] x = new Product[products.size()];
        products.toArray(x);
        for(int i = 0; i < x.length; i++)
        {
            if(x[i].getId() == id)
            {
                return products.get(i);
            }
        }
        return null;
    }

    public ObservableList<Product> getProducts()
    {
        return products;
    }
}
