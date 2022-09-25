import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();

    }

    private void run(){
        System.out.println("""
                1: Launch program
                2: Close""");
        int s = Souvenirs.scanner.nextInt();
        Souvenirs souvenirs = Souvenirs.create();
        switch (s) {
            case 1 -> souvenirs.start();
            case 2 -> System.out.println("Closing the program...");
            default -> System.out.println("Print the correct number!");
        }
    }
}
