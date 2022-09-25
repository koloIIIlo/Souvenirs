import java.io.Serializable;
import java.util.HashSet;

public class Souvenir implements Serializable {
    private String name;
    private double price;
    private int year;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Souvenir{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", year=" + year +
                '}';
    }

    public Souvenir(String name, double price, int year) {
        this.name = name;
        this.price = price;
        this.year = year;
    }

    public void editSouvenir() {
        System.out.println("Print new name:");
        String voided = Souvenirs.scanner.nextLine();
        String name = Souvenirs.scanner.nextLine();
        setName(name);

        System.out.println("New price:");
        double price = 0;
        try {
            price = Souvenirs.scanner.nextDouble();
        } catch (Exception e) {
            price=0;
            System.out.println("Price set as 0. After creating a souvenir you can edit this.");
        }
        setPrice(price);

        System.out.println("Set year:");
        int year1 = 0;
        try {
            year1 = Souvenirs.scanner.nextInt();
        } catch (Exception e) {
            year1 = 2000;
            System.out.println("Year of creating set as 2000. After creating a souvenir you can edit this.");
        }
        if (year1<1950){
            year1 = 1950;
            System.out.println("Year of creating set as 1950. After creating a souvenir you can edit this.");
        }
        setYear(year1);
    }

    public static Souvenir create() {
        System.out.println("Print the souvenir name!");
        Souvenirs.scanner.nextLine();
        String name1 = Souvenirs.scanner.nextLine();

        System.out.println("Price");
        double price = Souvenirs.scanner.nextDouble();

        System.out.println("Year");
        int year = Souvenirs.scanner.nextInt();

        return new Souvenir(name1, price, year);
    }

}
