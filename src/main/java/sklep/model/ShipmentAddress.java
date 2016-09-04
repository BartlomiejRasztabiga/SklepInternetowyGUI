package sklep.model;


public class ShipmentAddress {


    private String street;
    private String city;
    private String zipCode;
    private String state;
    private String country;

    public ShipmentAddress(String str, String c, String code, String st, String cntr) {
        street = str;
        city = c;
        zipCode = code;
        state = st;
        country = cntr;
    }

    public String getStreet() { return this.street; }

    public String getCity() { return this.city; }

    public String getZipCode() { return this.zipCode; }

    public String getState() { return this.state; }

    public String getCountry() { return this.country; }

    public String toString() {
        return "ShipmentAddress[street=" + street
                + ",city=" + city
                + ",zipCode=" + zipCode
                + ",state=" + state
                + ",country=" + country
                + "]";

    }
}

