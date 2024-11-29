package view;

import controller.MainController;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class MainUI {
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        // Display the welcome message
        System.out.println("______           _        _  ___  ___                                                  _     _____           _                 \n" +
                "| ___ \\         | |      | | |  \\/  |                                                 | |   /  ___|         | |                \n" +
                "| |_/ /___ _ __ | |_ __ _| | | .  . | __ _ _ __   __ _  __ _  ___ _ __ ___   ___ _ __ | |_  \\ `--. _   _ ___| |_ ___ _ __ ___  \n" +
                "|    // _ \\ '_ \\| __/ _` | | | |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '_ ` _ \\ / _ \\ '_ \\| __|  `--. \\ | | / __| __/ _ \\ '_ ` _ \\ \n" +
                "| |\\ \\  __/ | | | || (_| | | | |  | | (_| | | | | (_| | (_| |  __/ | | | | |  __/ | | | |_  /\\__/ / |_| \\__ \\ ||  __/ | | | | |\n" +
                "\\_| \\_\\___|_| |_|\\__\\__,_|_| \\_|  |_/\\__,_|_| |_|\\__,_|\\__, |\\___|_| |_| |_|\\___|_| |_|\\__| \\____/ \\__, |___/\\__\\___|_| |_| |_|\n" +
                "                                                        __/ |                                       __/ |                      \n" +
                "                                                       |___/                                       |___/                       ");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");

        while (MainController.isRunning()) {
            displayMainMenu();
            try {
                System.out.print("Enter your choice: ");
                if (scanner.hasNextLine()) {
                    int choice = Integer.parseInt(scanner.nextLine());
                    handleMainMenu(choice, scanner);
                } else {
                    System.out.println("No input found. Exiting the system.");
                    MainController.setRunning(false);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");
            } catch (NoSuchElementException e) {
                System.out.println("No input found. Exiting the system.");
                MainController.setRunning(false);
            }
        }

        MainController.saveDataToFile();
        System.out.println("Exiting the system. Goodbye!");
        scanner.close();
    }
    // Display the main menu
    private static void displayMainMenu() {
        System.out.println("\n--- Rental Management System ---");
        System.out.println("1. Instructions");
        System.out.println("2. View Tenants");
        System.out.println("3. View Hosts");
        System.out.println("4. View Owners");
        System.out.println("5. View Payments");
        System.out.println("6. View Properties");
        System.out.println("7. Manage Rental Agreements");
        System.out.println("8. Exit");
        System.out.println("---------------------------------");
    }

    // Handle the main menu choice
    private static void handleMainMenu(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                MainController.instructions();
                break;
            case 2:
                MainController.viewRole("Tenant");
                break;
            case 3:
                MainController.viewRole("Host");
                break;
            case 4:
                MainController.viewRole("Owner");
                break;
            case 5:
                MainController.viewPayments();
                break;
            case 6:
                MainController.viewProperties();
                break;
            case 7:
                MainController.manageRentalAgreements();
                break;
            case 8:
                MainController.setRunning(false);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}