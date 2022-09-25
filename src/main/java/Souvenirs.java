import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Souvenirs implements Serializable {
    private ArrayList<Souvenirs> souvenirs = new ArrayList<>();
    private Souvenir souvenir;
    private Fabricator fabricator;
    public static final Scanner scanner = new Scanner(System.in);

    @Override
    public String toString() {
        return  "" +souvenir + '\'' +
                " " + fabricator;
    }


    public Souvenirs(Souvenir souvenir, Fabricator fabricator) {
        this.souvenir = souvenir;
        this.fabricator = fabricator;
    }

    private Souvenirs() {
    }


    private Souvenirs(ArrayList<Souvenirs> souvenirs) {
        this.souvenirs = souvenirs;

    }


    public static Souvenirs create() {
        Souvenirs souvenirs1 = new Souvenirs();
        try {
            FileInputStream fis = new FileInputStream("Souvenirs.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Souvenirs s = (Souvenirs) ois.readObject();
            souvenirs1 = new Souvenirs(s.getSouvenirsFromArray());
            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("List of souvenirs not found! Creating new...");
            createNewSouvenirs();
        }
        return souvenirs1;
    }

    private static void createNewSouvenirs(){
        Souvenirs souvenirs1 = new Souvenirs();
        try {
            FileOutputStream fos = new FileOutputStream("Souvenirs.bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(souvenirs1);
            create();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void start() {
        menu();
    }


    public void menu() {
        System.out.println("""
                1: Add the new souvenir
                2: Edit souvenirs
                3: Show all souvenirs and fabricators
                4: Filter
                5: Delete the fabricator and all his souvenirs
                0: Close""");
        int a1 = 0;
        try {
            a1 = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Print correct number!");
            menu();
        }
        switch (a1){
            case 1 -> {
                addNewSouvenir();
                menu();
            }
            case 2 -> editSouvenirs();
            case 3 -> {
                showAllSouvenirs();
                menu();
            }
            case 4 -> filterBy();
            case 5 -> {
                deleteFabricator();
                menu();}
            case 0 -> {}
            default -> {
                System.out.println("Print correct number!");
                menu();
            }
        }
    }

    private void deleteFabricator() {
        String fabricator = showListOfFabricators();
        souvenirs.removeIf(x-> x.fabricator.getFactoryName().equals(fabricator));
        System.out.println("Fabricator: " + fabricator + " and all his souvenirs were deleted");
        save();
    }

    private void filterBy() {
        System.out.println("""
                  Filter by
                1: Fabricator
                2: Country
                3: Price lower then
                4: Show info of fabricators who have prise less then
                5: Show the info about fabricators who are produced the souvenir in 1 year
                6: Get all fabricators info and products which they have produced
                7: Get all years and souvenirs which were produced in that year
                0: Close""");
        int a1 = 0;
        try {
            a1 = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Be correct");
            menu();
        }
        switch (a1){
            case 1 -> filterByFabricator();
            case 2 -> filterByCountry();
            case 3 -> priceLowerThen();
            case 4 -> filterFabricatorByPrice();
            case 5 -> filterBySouvenirAndYear();
            case 6 -> getFabricatorsAndTheirSouvenirs();
            case 7 -> getYearsAndSouvenirs();
            case 0 -> {}
            default -> menu();
        }
    }

    private void getFabricatorsAndTheirSouvenirs() {
        Map<Fabricator, List<Souvenirs>> souvenirMap = souvenirs.stream()
                .collect(Collectors.groupingBy(Souvenirs::getFabricator));
        for (Fabricator fabricator1 : souvenirMap.keySet()) {
            System.out.println(fabricator1);
            for (Souvenirs souvenirsList : souvenirMap.get(fabricator1)) {
                System.out.println("  " + souvenirsList.getSouvenir());
            }
        }
        menu();
    }

    private void getYearsAndSouvenirs() {
        Map<Integer, List<Souvenir>> mapOfYear = souvenirs.stream()
                .map(x->x.souvenir)
                .collect(Collectors.groupingBy(Souvenir::getYear));
        for (Integer integer : mapOfYear.keySet()) {
            System.out.println(integer);
            for (Souvenir value : mapOfYear.get(integer)) {
                    System.out.println(value.getName()+", price:"+ value.getPrice());
            }
        }
        menu();
    }

    private void filterBySouvenirAndYear() {
        String name = getOneSouvenir();
        System.out.println("Print the year");
        int year = 0;
        try {
            year = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Incorrect year");
            filterBy();
        }
        int finalYear = year;
        List<Fabricator> fabricatorList = souvenirs.stream()
                .filter(x -> x.souvenir.getName().equals(name))
                .filter(x -> x.souvenir.getYear() == finalYear)
                .map(x->x.fabricator)
                .toList();
        if (fabricatorList.isEmpty()){
            System.out.println("Incorrect year!");
            menu();
        }
        fabricatorList.forEach(System.out::println);
        menu();
    }

    private String getOneSouvenir(){
        List<String> souvenirNameList = souvenirs.stream()
                .map(s -> s.getSouvenir().getName())
                .distinct()
                .toList();

        return getString(souvenirNameList);

    }

    private void filterFabricatorByPrice() {
        System.out.println("Print the price filter");
        double n = scanner.nextDouble();
        List<Fabricator> collect = souvenirs.stream()
                .filter(x -> x.souvenir.getPrice()<n)
                .map(x->x.fabricator)
                .distinct()
                .toList();
        System.out.println("Fabricators with price less then:" + n);
        for (Fabricator s: collect) {
            System.out.println(s);
        }
        menu();

    }

    private void filterByFabricator() {
        String filter = showListOfFabricators();
        List<Souvenir> collect = souvenirs.stream()
                .filter(x -> x.fabricator.getFactoryName().equals(filter))
                .map(x->x.souvenir)
                .toList();
        System.out.println(filter);
        for (Souvenir s: collect) {
            System.out.println(s);
        }
        menu();
    }

    private String showListOfFabricators() {
        List<String> listOfName = souvenirs.stream()
                .map(s -> s.getFabricator().getFactoryName())
                .distinct()
                .toList();
        return getString(listOfName);
    }

    private void filterByCountry() {
        String filter = showListOfCountry();
        List<Souvenir> collect = souvenirs.stream()
                .filter(x -> x.fabricator.getCountry().equals(filter))
                .map(x->x.souvenir)
                .toList();
        System.out.println(filter);
        for (Souvenir s: collect) {
            System.out.println(s);
        }
        menu();
    }

    private String showListOfCountry() {
        List<String> countryList = souvenirs.stream()
                .map(s -> s.getFabricator().getCountry())
                .distinct()
                .toList();
        return getString(countryList);
    }

    private void priceLowerThen() {
        double n = 0;
        try {
            System.out.println("Print filter for price");
            n = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Print number");
            menu();
        }
        double finalN = n;
        List<Souvenir> souvenirsList = souvenirs.stream()
                .map(x->x.souvenir)
                .filter(x -> x.getPrice()< finalN)
                .sorted()
                .toList();
        if (souvenirsList.isEmpty()) {
            System.out.println("No one souvenir was found");
            menu();
        }
        for (Souvenir souvenir1 : souvenirsList) {
            System.out.println(souvenir1);
        }
    }

    private static String getString(List<String> listOfName) {
        for (int i = 0; i < listOfName.size(); i++) {
            System.out.println(i + ": " + listOfName.get(i));
        }
        int a1 = 0;
        try {
            a1 = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Print the number!");
        }

        return listOfName.get(a1);
    }

    private void editSouvenirs() {
        showAllSouvenirs();
        System.out.println("Which souvenir do you want to change?");
        int a1 = 0;
        try {
            a1 = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Print the number of souvenir which you want to change");
            editSouvenirs();
        }
        if(a1< souvenirs.size()){
            editSouvenirPart2(a1);
        }else{
            System.out.println("Print correct number");
            editSouvenirs();
        }

    }

    private void editSouvenirPart2(int a1){
        System.out.println("""
                            What do you want to change?
                            1.Souvenir
                            2.Fabricator
                            3.Both""");
        int a2 = 0;
        try {
            a2 = scanner.nextInt();
        } catch (Exception e) {
            editSouvenirPart2(a1);
        }
        switch (a2){
            case 1 -> editSouvenir(a1);
            case 2 -> editFabricator(a1);
            case 3 -> editBoth(a1);
            default -> editSouvenirs();
        }
        System.out.println("Complete");
        save();
        menu();
    }


    private void editSouvenir(int x) {
        souvenirs.get(x).getSouvenir().editSouvenir();
    }


    private void editFabricator(int x) {
        souvenirs.get(x).getFabricator().editFabricator();
    }

    private void editBoth(int x) {
        editSouvenir(x);
        editFabricator(x);
    }

    private void addNewSouvenir() {
        Souvenir souvenir1 = Souvenir.create();
        Fabricator fabricator1 = Fabricator.create();
        souvenirs.add(addNewSouvenir(souvenir1, fabricator1));
        System.out.println("The souvenir added successful!");
        save();
        menu();

    }

    private Souvenirs addNewSouvenir(Souvenir souvenir, Fabricator fabricator){
        return new Souvenirs(souvenir,fabricator);
    }

    public void showAllSouvenirs()  {
        for (int i = 0; i < souvenirs.size(); i++) {
            System.out.println(i+ ": " + souvenirs.get(i));
        }
    }

    public void save() {
        try {
            Souvenirs souvenirs = new Souvenirs(this.souvenirs);
            FileOutputStream fos = new FileOutputStream("Souvenirs.bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(souvenirs);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Souvenirs> getSouvenirsFromArray() {
        return souvenirs;
    }


    public Fabricator getFabricator() {
        return fabricator;
    }

    public Souvenir getSouvenir() {
        return souvenir;
    }
}