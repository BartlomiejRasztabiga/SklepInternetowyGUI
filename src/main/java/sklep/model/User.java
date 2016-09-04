package sklep.model;


import sklep.util.AccountDatabase;
import sklep.util.ShipmentAddressDatabase;

public class User {

    private int id;
    private static int nextId;
    private String firstName;
    private String lastName;
    private Account account;
    private ShipmentAddress address;
    private ContactDetails contactDetails;
    private UserCredentials usrCred;


    public User(int id, String firstName, String lastName, String accNumber, String username, String password, String phoneNumber, String emailAddress)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactDetails = new ContactDetails(phoneNumber, emailAddress);
        this.account = AccountDatabase.getAccount(accNumber);
        this.address = ShipmentAddressDatabase.getAddress(id);
        this.usrCred = new UserCredentials(username, password);
    }

    public Account getAccount() {
        if(account != null) return account;
        else {
            System.out.println("Pole account wskazuje null");
            return null;
        }
    }

    public UserCredentials getUserCredentials() { return usrCred; }

    public ShipmentAddress getAddress() {
        return address;
    }

    public ContactDetails getContactDetails() { return contactDetails; }

    public String getName() { return firstName + " " + lastName; }

    public int getId() { return this.id; }

    public String toString()
    {
        //try
        //{
        return "User[id=" + id
                + ",firstName=" + firstName
                + ",lastName=" + lastName
                + ",account=" + account.toString()
                + "address=" + address.toString()
                + "]";
        //}
        //catch (NullPointerException e)
        //{
        // e.getStackTrace();
        //return null;
        //}
    }



}
