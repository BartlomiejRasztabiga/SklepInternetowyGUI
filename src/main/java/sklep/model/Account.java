package sklep.model;


import sklep.util.AccountDatabase;

public class Account {

    private String number;
    private double balance;


    public Account(String number, double balance)
    {
        this.number = number;
        this.balance = balance;
    }

    public Account()
    {
        balance = 0;
    }


    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public void changeBalance(double change)
    {

        try {
            if((change *-1) <= balance) balance += change;
            else {
                throw new Exception("Nie wystarczająca ilość pieniędzy na koncie");
            }

            balance *= 100;
            balance = Math.round(balance);
            balance /= 100;

            AccountDatabase.setBalance(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getNumber() { return number;}

    public String  toString()
    {
        return "Account[number=" + number
                + ",balance=" + balance
                + "]";
    }

}
