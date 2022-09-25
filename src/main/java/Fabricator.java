import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;

public class Fabricator implements Serializable {
    private String factoryName;

    private String country;
    public Fabricator(String factoryName, String country) {
        this.factoryName = factoryName;
        this.country = country;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int compare(Fabricator o1, Fabricator o2) {
        return o1.getCountry().compareTo(o2.getCountry());
    }

    @Override
    public String toString() {
        return "Fabricator{" +
                "factoryName='" + factoryName + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public void editFabricator(){
        System.out.println("Print factory name:");
        String voided = Souvenirs.scanner.nextLine();
        String factoryName = Souvenirs.scanner.nextLine();
        setFactoryName(factoryName);
        System.out.println("Print country:");
        String country = Souvenirs.scanner.nextLine();
        setCountry(country);
    }

    public static Fabricator create() {
        System.out.println("Print the Factory name!");
        Souvenirs.scanner.nextLine();
        String factoryName = Souvenirs.scanner.nextLine();

        System.out.println("Print the Country where souvenir was produced!");
        String factoryLocated = Souvenirs.scanner.nextLine();

        return new Fabricator(factoryName, factoryLocated);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fabricator that = (Fabricator) o;
        return Objects.equals(factoryName, that.factoryName) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(factoryName, country);
    }
}
