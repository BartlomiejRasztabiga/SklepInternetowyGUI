package sklep.util;

public class DatabaseCredentials {

    private String url;
    private String user;
    private String password;

    public DatabaseCredentials(String url, String user, String password)
    {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public DatabaseCredentials(int id)
    {

        if (id == 1)
        {
            this.url = "jdbc:mysql://localhost:3306/SklepInternetowy";
            this.user = "root";
            this.password = "Gallendors5";
        }
        else if (id == 2)
        {
            this.url = "jdbc:mysql://mysql9.mydevil.net:3306/m1159_sklepinternetowy";
            this.user = "m1159_root";
            this.password = "Gallendors5";
        }
        else if (id == 3)
        {
            this.url = "jdbc:mariadb://localhost:3306/sklepinternetowy";
            this.user = "root";
            this.password = "Gallendors5";
        }
        else
        {
            this.url = "jdbc:mysql://localhost:3306/SklepInternetowy";
            this.user = "root";
            this.password = "Gallendors5";
        }

    }

    public String getUrl() { return url;}

    public String getUser() { return user;}

    public String getPassword() { return password;}


}
