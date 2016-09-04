package sklep.model;


public class ContactDetails {

    private String phoneNumber;
    private String emailAddress;

    public ContactDetails(String phoneNumber, String emailAddress)
    {
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() { return this.phoneNumber; }

    public String getEmailAddress() { return this.emailAddress; }
}
