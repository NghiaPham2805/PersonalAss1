package controller;
import model.*;

import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// MainController class to manage the main functionality of the Rental Management System
public class MainController {
    private static boolean running = true;
    private static List<Person> personList = readPersonFile(); // List of all persons from the data file and save it to the list
    private static List<Property> propertyList=readPropertyFile();
    private static List<Payment> paymentList = readPaymentFile();
    private static List<RentalAgreement> rentalAgreementList = readRentalAgreementFile();

    // Main method to run the program
    public static boolean isRunning() {
        return running;
    }

    public static void setRunning(boolean running) {
        MainController.running = running;
    }


    public static List<Person> getPersonList() {
        return personList;
    }

    public static List<Property> getPropertyList() {
        return propertyList;
    }

    public static List<Payment> getPaymentList() {
        return paymentList;
    }

    public static List<RentalAgreement> getRentalAgreementList() {
        return rentalAgreementList;
    }

    // Read data from Persontext files
    private static List<Person> readPersonFile() {
        List<Person> persons = new ArrayList<>();
        File file = new File("src/data/person.txt");

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                String line;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Format for parsing dates

                while ((line = reader.readLine()) != null) {
                    // Assuming the data format: ID|Full Name|Birth Date|Email|Role
                    String[] parts = line.split("\\|");
                    if (parts.length >= 5) {
                        String id = parts[0].trim();
                        String fullName = parts[1].trim();
                        Date birthDate = null;
                        try {
                            birthDate = dateFormat.parse(parts[2].trim());
                        } catch (ParseException e) {
                            System.err.println("Invalid date format for: " + parts[2].trim());
                        }
                        String contactInfo = parts[3].trim();
                        String role = parts[4].trim();

                        // Create and add the Person object
                        if (birthDate != null) {
                            persons.add(new Person(id, fullName, birthDate, contactInfo, role));
                        }
                    } else {
                        System.out.println("Invalid line format: " + line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }
        } else {
            System.out.println("File not found: " + file.getAbsolutePath());
        }

        return persons;
    }

    // Read data from Properties text files
    private static List<Property> readPropertyFile() {
        List<Property> properties = new ArrayList<>();
        File file = new File("src/data/properties.txt");

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    // Assuming the data format: ID|Type|Address|Price|Status|OwnerID|[HostIDs]
                    String[] parts = line.split("\\|");
                    if (parts.length >= 6) {
                        String propertyId = parts[0].trim();
                        String type = parts[1].trim();
                        String address = parts[2].trim();
                        double pricing = Double.parseDouble(parts[3].trim());
                        String status = parts[4].trim();
                        String ownerId = parts[5].trim();

                        // Parse the list of host IDs from the last column (e.g., [P002,P003])
                        List<String> hosts = new ArrayList<>();
                        if (parts.length > 6) {
                            String hostIds = parts[6].trim();
                            if (hostIds.startsWith("[") && hostIds.endsWith("]")) {
                                String[] hostIdArray = hostIds.substring(1, hostIds.length() - 1).split(",");
                                for (String hostId : hostIdArray) {
                                    hosts.add(hostId.trim());
                                }
                            }
                        }

                        // Create and add the Property object
                        Property property = new Property(propertyId, type, address, pricing, status, ownerId);
                        for (String hostId : hosts) {
                            property.addHost(hostId);
                        }

                        properties.add(property);
                    } else {
                        System.out.println("Invalid line format: " + line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }
        } else {
            System.out.println("File not found: " + file.getAbsolutePath());
        }

        return properties;
    }

    // Read data from Payments text files
    private static List<Payment> readPaymentFile() {
        List<Payment> payments = new ArrayList<>();
        File file = new File("src/data/payments.txt");

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                String line;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Format for parsing dates

                while ((line = reader.readLine()) != null) {
                    // Assuming the data format: PaymentID|TenantID|RentalAgreementID|Price|Date|PaymentMethod
                    String[] parts = line.split("\\|");
                    if (parts.length >= 6) {
                        String paymentId = parts[0].trim();
                        String tenantId = parts[1].trim();
                        String rentalAgreementId = parts[2].trim();
                        double price;
                        try {
                            price = Double.parseDouble(parts[3].trim());
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid price format for: " + parts[3].trim());
                            continue;
                        }
                        Date paymentDate = null;
                        try {
                            paymentDate = dateFormat.parse(parts[4].trim());
                        } catch (ParseException e) {
                            System.err.println("Invalid date format for: " + parts[4].trim());
                            continue;
                        }
                        String paymentMethod = parts[5].trim();

                        // Create and add the Payment object
                        if (paymentDate != null) {
                            payments.add(new Payment(paymentId, tenantId, rentalAgreementId, price, paymentDate, paymentMethod));
                        }
                    } else {
                        System.out.println("Invalid line format: " + line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }
        } else {
            System.out.println("File not found: " + file.getAbsolutePath());
        }

        return payments;
    }

    // Read data from RentalAgreements text files
    private static List<RentalAgreement> readRentalAgreementFile() {
        List<RentalAgreement> rentalAgreements = new ArrayList<>();
        File file = new File("src/data/rental_agreements.txt");

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                String line;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Format for parsing dates

                while ((line = reader.readLine()) != null) {
                    // Assuming the data format: AgreementID|PropertyID|MainTenantID|SubTenantIDs|OwnerID|HostID|Period|ContractDate|RentingFee|Status
                    String[] parts = line.split("\\|");
                    if (parts.length >= 10) {
                        String agreementId = parts[0].trim();
                        String propertyId = parts[1].trim();
                        String mainTenantId = parts[2].trim();
                        List<String> subTenantIds = Arrays.asList(parts[3].trim().split(","));
                        String ownerId = parts[4].trim();
                        String hostId = parts[5].trim();
                        String period = parts[6].trim();
                        Date contractDate = null;
                        try {
                            contractDate = dateFormat.parse(parts[7].trim());
                        } catch (ParseException e) {
                            System.err.println("Invalid date format for: " + parts[7].trim());
                            continue;
                        }
                        double rentingFee;
                        try {
                            rentingFee = Double.parseDouble(parts[8].trim());
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid renting fee format for: " + parts[8].trim());
                            continue;
                        }
                        String status = parts[9].trim();

                        // Create and add the RentalAgreement object
                        rentalAgreements.add(new RentalAgreement(agreementId, propertyId, mainTenantId, subTenantIds, ownerId, hostId, period, contractDate, rentingFee, status));
                    } else {
                        System.out.println("Invalid line format: " + line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }
        } else {
            System.out.println("File not found: " + file.getAbsolutePath());
        }

        return rentalAgreements;
    }

    // Save data to text files
    public static void saveDataToFile() {
        savePersonListToFile();
        savePropertyListToFile();
        savePaymentListToFile();
        saveRentalAgreementListToFile();
    }

    // Save data to Person text files
    private static void savePersonListToFile() {
        File file = new File("src/data/person.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Person person : personList) {
                writer.write(person.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to person file: " + e.getMessage());
        }
    }

    // Save data to Property text files
    private static void savePropertyListToFile() {
        File file = new File("src/data/properties.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Property property : propertyList) {
                writer.write(property.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to property file: " + e.getMessage());
        }
    }

    // Save data to Payment text files
    private static void savePaymentListToFile() {
        File file = new File("src/data/payments.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Payment payment : paymentList) {
                writer.write(payment.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to payment file: " + e.getMessage());
        }
    }

    // Save data to RentalAgreements text files
    private static void saveRentalAgreementListToFile() {
        File file = new File("src/data/rental_agreements.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (RentalAgreement agreement : rentalAgreementList) {
                writer.write(agreement.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to rental agreement file: " + e.getMessage());
        }
    }


    //VIEW TENANT, HOST, OWNER
    public static void viewRole(String role) {
        System.out.println("Choose sorting option:");
        System.out.println("1. Sort by ID");
        System.out.println("2. Sort by Full Name");
        System.out.print("Enter your choice: ");

        Scanner scanner = new Scanner(System.in);
        int choice = Integer.parseInt(scanner.nextLine());

        List<Person> filteredList = new ArrayList<>();
        for (Person person : personList) {
            if (role.equalsIgnoreCase(person.getRole())) {
                filteredList.add(person);
            }
        }

        switch (choice) {
            case 1:
                filteredList.sort(Comparator.comparing(Person::getId));
                break;
            case 2:
                filteredList.sort(Comparator.comparing(Person::getFullName));
                break;
            default:
                System.out.println("Invalid choice. Displaying unsorted data.");
        }

        System.out.println(role + " Information:");
        System.out.println("===================================================================================");
        System.out.printf("%-10s %-20s %-15s %-25s%n", "ID", "Full Name", "Birth Date", "Contact Info");
        System.out.println("===================================================================================");
        for (Person person : filteredList) {
            System.out.printf("%-10s %-20s %-15s %-25s%n",
                    person.getId(), person.getFullName(), new SimpleDateFormat("yyyy-MM-dd").format(person.getBirthDate()), person.getContactInfo());
        }
        System.out.println("===================================================================================");
    }
    //VIEW PAYMENT
    public static void viewPayments() {
        System.out.println("Choose sorting option:");
        System.out.println("1. Sort by Payment ID");
        System.out.println("2. Sort by Tenant ID");
        System.out.println("3. Sort by Rental Agreement ID");
        System.out.println("4. Sort by Price");
        System.out.print("Enter your choice: ");

        Scanner scanner = new Scanner(System.in);
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                paymentList.sort(Comparator.comparing(Payment::getPaymentId));
                break;
            case 2:
                paymentList.sort(Comparator.comparing(Payment::getTenantId));
                break;
            case 3:
                paymentList.sort(Comparator.comparing(Payment::getRentalAgreementId));
                break;
            case 4:
                paymentList.sort(Comparator.comparing(Payment::getAmount));
                break;
            default:
                System.out.println("Invalid choice. Displaying unsorted data.");
        }

        System.out.println("Payment Information:");
        System.out.println("=========================================================================================");
        System.out.printf("%-15s %-10s %-20s %-10s %-15s %-15s%n", "Payment ID", "Tenant ID", "Rental Agreement ID", "Price", "Date", "Payment Method");
        System.out.println("=========================================================================================");
        for (Payment payment : paymentList) {
            System.out.printf("%-15s %-10s %-20s %-10.2f %-15s %-15s%n",
                    payment.getPaymentId(), payment.getTenantId(), payment.getRentalAgreementId(), payment.getAmount(), new SimpleDateFormat("yyyy-MM-dd").format(payment.getDate()), payment.getPaymentMethod());
        }
        System.out.println("=========================================================================================");
    }

    //VIEW PROPERTY
    public static void viewProperties() {
        System.out.println("Choose sorting option:");
        System.out.println("1. Sort by Property ID");
        System.out.println("2. Sort by Type");
        System.out.println("3. Sort by Address");
        System.out.println("4. Sort by Price");
        System.out.println("5. Sort by Status");
        System.out.println("6. Sort by Owner ID");
        System.out.print("Enter your choice: ");

        Scanner scanner = new Scanner(System.in);
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                propertyList.sort(Comparator.comparing(Property::getPropertyId));
                break;
            case 2:
                propertyList.sort(Comparator.comparing(Property::getType));
                break;
            case 3:
                propertyList.sort(Comparator.comparing(Property::getAddress));
                break;
            case 4:
                propertyList.sort(Comparator.comparing(Property::getPricing));
                break;
            case 5:
                propertyList.sort(Comparator.comparing(Property::getStatus));
                break;
            case 6:
                propertyList.sort(Comparator.comparing(Property::getOwnerId));
                break;
            default:
                System.out.println("Invalid choice. Displaying unsorted data.");
        }

        System.out.println("Property Information:");
        System.out.println("===================================================================================");
        System.out.printf("%-10s %-20s %-15s %-10s %-15s %-10s%n", "Property ID", "Type", "Address", "Price", "Status", "Owner ID");
        System.out.println("===================================================================================");
        for (Property property : propertyList) {
            System.out.printf("%-10s %-20s %-15s %-10.2f %-15s %-10s%n",
                    property.getPropertyId(), property.getType(), property.getAddress(), property.getPricing(), property.getStatus(), property.getOwnerId());
        }
        System.out.println("===================================================================================");
    }
    //VIEW RENTAL AGREEMENT
    private static void viewRentalAgreements() {
        System.out.println("\n"+ "Choose sorting option:");
        System.out.println("1. Sort by Agreement ID");
        System.out.println("2. Sort by Property ID");
        System.out.println("3. Sort by Main Tenant ID");
        System.out.println("4. Sort by Owner ID");
        System.out.println("5. Sort by Host ID");
        System.out.println("6. Sort by Renting Fee");
        System.out.println("7. Sort by Status");
        System.out.print("Enter your choice: ");

        Scanner scanner = new Scanner(System.in);
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                rentalAgreementList.sort(Comparator.comparing(RentalAgreement::getAgreementId));
                break;
            case 2:
                rentalAgreementList.sort(Comparator.comparing(RentalAgreement::getPropertyId));
                break;
            case 3:
                rentalAgreementList.sort(Comparator.comparing(RentalAgreement::getMainTenantId));
                break;
            case 4:
                rentalAgreementList.sort(Comparator.comparing(RentalAgreement::getOwnerId));
                break;
            case 5:
                rentalAgreementList.sort(Comparator.comparing(RentalAgreement::getHostId));
                break;
            case 6:
                rentalAgreementList.sort(Comparator.comparing(RentalAgreement::getRentingFee));
                break;
            case 7:
                rentalAgreementList.sort(Comparator.comparing(RentalAgreement::getStatus));
                break;
            default:
                System.out.println("Invalid choice. Displaying unsorted data.");
        }

        System.out.println("Rental Agreement Information:");
        System.out.println("==================================================================================================================================================================================");
        System.out.printf("%-15s %-15s %-15s %-20s %-15s %-15s %-15s %-20s %-15s %-15s%n",
                "Agreement ID", "Property ID", "Main Tenant ID", "Sub-Tenant IDs", "Owner ID", "Host ID", "Period", "Contract Date", "Renting Fee", "Status");
        System.out.println("==================================================================================================================================================================================");
        for (RentalAgreement agreement : rentalAgreementList) {
            System.out.printf("%-15s %-15s %-15s %-20s %-15s %-15s %-15s %-20s %-15.2f %-15s%n",
                    agreement.getAgreementId(), agreement.getPropertyId(), agreement.getMainTenantId(),
                    String.join(",", agreement.getSubTenantIds()), agreement.getOwnerId(), agreement.getHostId(),
                    agreement.getPeriod(), new SimpleDateFormat("yyyy-MM-dd").format(agreement.getContractDate()),
                    agreement.getRentingFee(), agreement.getStatus());
        }
        System.out.println("==================================================================================================================================================================================");
    }

    //MANAGE TENANT
    private static void manageTenant(Scanner scanner) {
        System.out.println("\n--- Manage Tenant ---");
        System.out.println("1. Add Tenant");
        System.out.println("2. Update Tenant");
        System.out.println("3. Delete Tenant");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter your choice: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Adding a new tenant...");
                    addTenant(scanner);
                    break;
                case 2:
                    System.out.println("Updating an existing tenant...");
                    updateTenant(scanner);
                    break;
                case 3:
                    System.out.println("Deleting a tenant...");
                    deleteTenant(scanner);
                    break;
                case 4:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }



    //ADD TENANT
    private static void addTenant(Scanner scanner) {
        System.out.println("Enter tenant's full name:");
        String fullName = scanner.nextLine();

        System.out.println("Enter tenant's date of birth (YYYY-MM-DD):");
        String dob = scanner.nextLine();
        while (!isValidDate(dob)) {
            System.out.println("Invalid date format. Please enter again (YYYY-MM-DD):");
            dob = scanner.nextLine();
        }

        System.out.println("Enter tenant's email address:");
        String email = scanner.nextLine();
        while (!isValidEmail(email)) {
            System.out.println("Invalid email format. Please enter again:");
            email = scanner.nextLine();
        }

        String tenantId = generatePersonId(new File("src/data/person.txt"));
        Date birthDate = null;
        try {
            birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }

        if (birthDate != null) {
            Person newTenant = new Person(tenantId, fullName, birthDate, email, "Tenant");
            personList.add(newTenant);
            System.out.println("Tenant added successfully to the list!");
        } else {
            System.out.println("Failed to add tenant due to invalid birth date.");
        }
    }
    //update tenant
    private static void updateTenant(Scanner scanner) {
        System.out.println("Enter the ID of the tenant to update:");
        String tenantId = scanner.nextLine();

        boolean tenantFound = false;

        for (Person person : personList) {
            if (person.getId().equals(tenantId) && "Tenant".equalsIgnoreCase(person.getRole())) {
                tenantFound = true;
                System.out.println("Updating information for tenant " + person.getFullName());

                System.out.println("Enter new full name (leave blank to keep current):");
                String fullName = scanner.nextLine();
                if (!fullName.isEmpty()) {
                    person.setFullName(fullName);
                }

                System.out.println("Enter new date of birth (YYYY-MM-DD, leave blank to keep current):");
                String dob = scanner.nextLine();
                if (!dob.isEmpty()) {
                    while (!isValidDate(dob)) {
                        System.out.println("Invalid date format. Please enter again (YYYY-MM-DD):");
                        dob = scanner.nextLine();
                    }
                    try {
                        person.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse(dob));
                    } catch (ParseException e) {
                        System.out.println("Error parsing date: " + e.getMessage());
                    }
                }

                System.out.println("Enter new email address (leave blank to keep current):");
                String email = scanner.nextLine();
                if (!email.isEmpty()) {
                    while (!isValidEmail(email)) {
                        System.out.println("Invalid email format. Please enter again:");
                        email = scanner.nextLine();
                    }
                    person.setContactInfo(email);
                }

                System.out.println("Tenant information updated successfully.");
                break;
            }
        }

        if (!tenantFound) {
            System.out.println("Tenant with ID " + tenantId + " not found.");
        }
    }

    //delete tenant
    private static void deleteTenant(Scanner scanner) {
        System.out.println("Enter the ID of the tenant to delete:");
        String tenantId = scanner.nextLine();

        boolean tenantFound = false;
        Iterator<Person> iterator = personList.iterator();

        while (iterator.hasNext()) {
            Person person = iterator.next();
            if (person.getId().equals(tenantId) && "Tenant".equalsIgnoreCase(person.getRole())) {
                tenantFound = true;
                iterator.remove();
                System.out.println("Tenant " + person.getFullName() + " deleted successfully.");
                break;
            }
        }

        if (!tenantFound) {
            System.out.println("Tenant with ID " + tenantId + " not found.");
        }
    }


    //MANAGE HOST
    private static void manageHost(Scanner scanner) {
        System.out.println("\n--- Manage Host ---");
        System.out.println("1. Add Host");
        System.out.println("2. Update Host");
        System.out.println("3. Delete Host");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter your choice: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Adding a new host...");
                    addHost(scanner);
                    break;
                case 2:
                    System.out.println("Updating an existing host...");
                    updateHost(scanner);
                    break;
                case 3:
                    System.out.println("Deleting a host...");
                    deleteHost(scanner);
                    break;
                case 4:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
    // Add Host
    private static void addHost(Scanner scanner) {
        System.out.println("Enter host's full name:");
        String fullName = scanner.nextLine();

        System.out.println("Enter host's date of birth (YYYY-MM-DD):");
        String dob = scanner.nextLine();
        while (!isValidDate(dob)) {
            System.out.println("Invalid date format. Please enter again (YYYY-MM-DD):");
            dob = scanner.nextLine();
        }

        System.out.println("Enter host's email address:");
        String email = scanner.nextLine();
        while (!isValidEmail(email)) {
            System.out.println("Invalid email format. Please enter again:");
            email = scanner.nextLine();
        }

        String hostId = generatePersonId(new File("src/data/person.txt"));
        Date birthDate = null;
        try {
            birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }

        if (birthDate != null) {
            Person newHost = new Person(hostId, fullName, birthDate, email, "Host");
            personList.add(newHost);
            System.out.println("Host added successfully to the list!");
        } else {
            System.out.println("Failed to add host due to invalid birth date.");
        }
    }

    // Update Host
    private static void updateHost(Scanner scanner) {
        System.out.println("Enter the ID of the host to update:");
        String hostId = scanner.nextLine();

        boolean hostFound = false;

        for (Person person : personList) {
            if (person.getId().equals(hostId) && "Host".equalsIgnoreCase(person.getRole())) {
                hostFound = true;
                System.out.println("Updating information for host " + person.getFullName());

                System.out.println("Enter new full name (leave blank to keep current):");
                String fullName = scanner.nextLine();
                if (!fullName.isEmpty()) {
                    person.setFullName(fullName);
                }

                System.out.println("Enter new date of birth (YYYY-MM-DD, leave blank to keep current):");
                String dob = scanner.nextLine();
                if (!dob.isEmpty()) {
                    while (!isValidDate(dob)) {
                        System.out.println("Invalid date format. Please enter again (YYYY-MM-DD):");
                        dob = scanner.nextLine();
                    }
                    try {
                        person.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse(dob));
                    } catch (ParseException e) {
                        System.out.println("Error parsing date: " + e.getMessage());
                    }
                }

                System.out.println("Enter new email address (leave blank to keep current):");
                String email = scanner.nextLine();
                if (!email.isEmpty()) {
                    while (!isValidEmail(email)) {
                        System.out.println("Invalid email format. Please enter again:");
                        email = scanner.nextLine();
                    }
                    person.setContactInfo(email);
                }

                System.out.println("Host information updated successfully.");
                break;
            }
        }

        if (!hostFound) {
            System.out.println("Host with ID " + hostId + " not found.");
        }
    }

    // Delete Host
    private static void deleteHost(Scanner scanner) {
        System.out.println("Enter the ID of the host to delete:");
        String hostId = scanner.nextLine();

        boolean hostFound = false;
        Iterator<Person> iterator = personList.iterator();

        while (iterator.hasNext()) {
            Person person = iterator.next();
            if (person.getId().equals(hostId) && "Host".equalsIgnoreCase(person.getRole())) {
                hostFound = true;
                iterator.remove();
                System.out.println("Host " + person.getFullName() + " deleted successfully.");
                break;
            }
        }

        if (!hostFound) {
            System.out.println("Host with ID " + hostId + " not found.");
        }
    }

    //MANAGE OWNER
    private static void manageOwner(Scanner scanner) {
        System.out.println("\n--- Manage Owner ---");
        System.out.println("1. Add Owner");
        System.out.println("2. Update Owner");
        System.out.println("3. Delete Owner");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter your choice: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Adding a new owner...");
                    addOwner(scanner);
                    break;
                case 2:
                    System.out.println("Updating an existing owner...");
                    updateOwner(scanner);
                    break;
                case 3:
                    System.out.println("Deleting an owner...");
                    deleteOwner(scanner);
                    break;
                case 4:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    // Add Owner
    private static void addOwner(Scanner scanner) {
        System.out.println("Enter owner's full name:");
        String fullName = scanner.nextLine();

        System.out.println("Enter owner's date of birth (YYYY-MM-DD):");
        String dob = scanner.nextLine();
        while (!isValidDate(dob)) {
            System.out.println("Invalid date format. Please enter again (YYYY-MM-DD):");
            dob = scanner.nextLine();
        }

        System.out.println("Enter owner's email address:");
        String email = scanner.nextLine();
        while (!isValidEmail(email)) {
            System.out.println("Invalid email format. Please enter again:");
            email = scanner.nextLine();
        }

        String ownerId = generatePersonId(new File("src/data/person.txt"));
        Date birthDate = null;
        try {
            birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }

        if (birthDate != null) {
            Person newOwner = new Person(ownerId, fullName, birthDate, email, "Owner");
            personList.add(newOwner);
            System.out.println("Owner added successfully to the list!");
        } else {
            System.out.println("Failed to add owner due to invalid birth date.");
        }
    }

    // Update Owner
    private static void updateOwner(Scanner scanner) {
        System.out.println("Enter the ID of the owner to update:");
        String ownerId = scanner.nextLine();

        boolean ownerFound = false;

        for (Person person : personList) {
            if (person.getId().equals(ownerId) && "Owner".equalsIgnoreCase(person.getRole())) {
                ownerFound = true;
                System.out.println("Updating information for owner " + person.getFullName());

                System.out.println("Enter new full name (leave blank to keep current):");
                String fullName = scanner.nextLine();
                if (!fullName.isEmpty()) {
                    person.setFullName(fullName);
                }

                System.out.println("Enter new date of birth (YYYY-MM-DD, leave blank to keep current):");
                String dob = scanner.nextLine();
                if (!dob.isEmpty()) {
                    while (!isValidDate(dob)) {
                        System.out.println("Invalid date format. Please enter again (YYYY-MM-DD):");
                        dob = scanner.nextLine();
                    }
                    try {
                        person.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse(dob));
                    } catch (ParseException e) {
                        System.out.println("Error parsing date: " + e.getMessage());
                    }
                }

                System.out.println("Enter new email address (leave blank to keep current):");
                String email = scanner.nextLine();
                if (!email.isEmpty()) {
                    while (!isValidEmail(email)) {
                        System.out.println("Invalid email format. Please enter again:");
                        email = scanner.nextLine();
                    }
                    person.setContactInfo(email);
                }

                System.out.println("Owner information updated successfully.");
                break;
            }
        }

        if (!ownerFound) {
            System.out.println("Owner with ID " + ownerId + " not found.");
        }
    }

    // Delete Owner
    private static void deleteOwner(Scanner scanner) {
        System.out.println("Enter the ID of the owner to delete:");
        String ownerId = scanner.nextLine();

        boolean ownerFound = false;
        Iterator<Person> iterator = personList.iterator();

        while (iterator.hasNext()) {
            Person person = iterator.next();
            if (person.getId().equals(ownerId) && "Owner".equalsIgnoreCase(person.getRole())) {
                ownerFound = true;
                iterator.remove();
                System.out.println("Owner " + person.getFullName() + " deleted successfully.");
                break;
            }
        }

        if (!ownerFound) {
            System.out.println("Owner with ID " + ownerId + " not found.");
        }
    }

    //ID generator for person
    private static String generatePersonId(File file) {
        int maxId = 0;
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length > 0 && parts[0].startsWith("P")) {
                    try {
                        int id = Integer.parseInt(parts[0].substring(1));
                        if (id > maxId) {
                            maxId = id;
                        }
                    } catch (NumberFormatException e) {
                        // Ignore invalid IDs
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file for ID generation: " + e.getMessage());
        }
        return String.format("P%03d", maxId + 1);
    }

    //MANAGE PROPERTY
    private static void manageProperty(Scanner scanner) {
        System.out.println("\n--- Manage Property ---");
        System.out.println("1. Add Property");
        System.out.println("2. Update Property");
        System.out.println("3. Delete Property");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter your choice: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Adding a new property...");
                    addProperty(scanner);
                    break;
                case 2:
                    System.out.println("Updating an existing property...");
                    updateProperty(scanner);
                    break;
                case 3:
                    System.out.println("Deleting a property...");
                    deleteProperty(scanner);
                    break;
                case 4:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    // Add Property
    private static void addProperty(Scanner scanner) {
        System.out.println("Enter property type (e.g., Residential, Commercial):");
        String type = scanner.nextLine();

        System.out.println("Enter property address:");
        String address = scanner.nextLine();

        System.out.println("Enter property price:");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.println("Enter property status (e.g., Available, Rented, Under Maintenance):");
        String status = scanner.nextLine();

        System.out.println("Enter owner ID:");
        String ownerId = scanner.nextLine();

        // Check if the owner ID is valid and corresponds to an owner
        boolean isValidOwner = false;
        for (Person person : personList) {
            if (person.getId().equals(ownerId) && "Owner".equalsIgnoreCase(person.getRole())) {
                isValidOwner = true;
                break;
            }
        }

        if (!isValidOwner) {
            System.out.println("Invalid owner ID. Please enter a valid owner ID.");
            return;
        }

        String propertyId = generatePropertyId(new File("src/data/properties.txt"));

        Property newProperty = new Property(propertyId, type, address, price, status, ownerId);
        propertyList.add(newProperty);
        System.out.println("Property added successfully to the list!");
    }

    // Update Property
    private static void updateProperty(Scanner scanner) {
        System.out.println("Enter the ID of the property to update:");
        String propertyId = scanner.nextLine();

        boolean propertyFound = false;

        for (Property property : propertyList) {
            if (property.getPropertyId().equals(propertyId)) {
                propertyFound = true;
                System.out.println("Updating information for property " + property.getAddress());

                System.out.println("Enter new type (leave blank to keep current):");
                String type = scanner.nextLine();
                if (!type.isEmpty()) {
                    property.setType(type);
                }

                System.out.println("Enter new address (leave blank to keep current):");
                String address = scanner.nextLine();
                if (!address.isEmpty()) {
                    property.setAddress(address);
                }

                System.out.println("Enter new price (leave blank to keep current):");
                String priceStr = scanner.nextLine();
                if (!priceStr.isEmpty()) {
                    double price = Double.parseDouble(priceStr);
                    property.setPricing(price);
                }

                System.out.println("Enter new status (leave blank to keep current):");
                String status = scanner.nextLine();
                if (!status.isEmpty()) {
                    property.setStatus(status);
                }

                System.out.println("Enter new owner ID (leave blank to keep current):");
                String ownerId = scanner.nextLine();
                if (!ownerId.isEmpty()) {
                    // Check if the owner ID is valid and corresponds to an owner
                    boolean isValidOwner = false;
                    for (Person person : personList) {
                        if (person.getId().equals(ownerId) && "Owner".equalsIgnoreCase(person.getRole())) {
                            isValidOwner = true;
                            break;
                        }
                    }

                    if (!isValidOwner) {
                        System.out.println("Invalid owner ID. Please enter a valid owner ID.");
                        return;
                    }

                    property.setOwnerId(ownerId);
                }

                System.out.println("Property information updated successfully.");
                break;
            }
        }

        if (!propertyFound) {
            System.out.println("Property with ID " + propertyId + " not found.");
        }
    }

    // Delete Property
    private static void deleteProperty(Scanner scanner) {
        System.out.println("Enter the ID of the property to delete:");
        String propertyId = scanner.nextLine();

        boolean propertyFound = false;
        Iterator<Property> iterator = propertyList.iterator();

        while (iterator.hasNext()) {
            Property property = iterator.next();
            if (property.getPropertyId().equals(propertyId)) {
                propertyFound = true;
                iterator.remove();
                System.out.println("Property " + property.getAddress() + " deleted successfully.");
                break;
            }
        }

        if (!propertyFound) {
            System.out.println("Property with ID " + propertyId + " not found.");
        }
    }

    // ID generator for Property
    private static String generatePropertyId(File file) {
        int maxId = 0;
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length > 0 && parts[0].startsWith("PR")) {
                    try {
                        int id = Integer.parseInt(parts[0].substring(2));
                        if (id > maxId) {
                            maxId = id;
                        }
                    } catch (NumberFormatException e) {
                        // Ignore invalid IDs
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file for ID generation: " + e.getMessage());
        }
        return String.format("PR%03d", maxId + 1);
    }



    //MANAGE RENTAL AGREEMENT
    public static void manageRentalAgreements() {
        System.out.println("\n--- Manage Rental Agreements ---");
        System.out.println("1. Manage Tenant ");
        System.out.println("2. Manage Host ");
        System.out.println("3. Manage Owner ");
        System.out.println("4. Manage Property");
        System.out.println("5. View and edit Rental Agreement");
        System.out.println("6. Back to Main Menu");
        System.out.print("Enter your choice: ");
        try {
              Scanner scanner = new Scanner(System.in);
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Manage Tenant");
                    // addRentalAgreement(scanner);
                    manageTenant(scanner);
                    break;
                case 2:
                    System.out.println("Manage Host");
                    // updateRentalAgreement(scanner);
                    manageHost(scanner);
                    break;
                case 3:
                    System.out.println("Manage Owner");
                    // deleteRentalAgreement(scanner);
                    manageOwner(scanner);
                    break;
                case 4:
                    System.out.println("Manage Property");
                    manageProperty(scanner);
                    break;
                case 5:
                    System.out.println("View and edit Rental Agreement");
                    EditRentalAgreements(scanner);
                    break;
                case 6:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    //VIEW AND EDIT RENTAL AGREEMENT
    private static void EditRentalAgreements(Scanner scanner) {
        viewRentalAgreements();
        System.out.println("\n--- Manage Rental Agreements ---");
        System.out.println("1. Add Rental Agreement");
        System.out.println("2. Update Rental Agreement");
        System.out.println("3. Delete Rental Agreement");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter your choice: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Adding a new rental agreement...");
                    addRentalAgreement(scanner);
                    break;
                case 2:
                    System.out.println("Updating an existing rental agreement...");
                    updateRentalAgreement(scanner);
                    break;
                case 3:
                    System.out.println("Deleting a rental agreement...");
                    deleteRentalAgreement(scanner);
                    break;
                case 4:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    // Add Rental Agreement
    private static void addRentalAgreement(Scanner scanner) {
        String propertyId;
        while (true) {
            System.out.println("Enter property ID:");
            propertyId = scanner.nextLine();
            if (isValidPropertyId(propertyId)) break;
            System.out.println("Invalid property ID. Please enter a valid property ID.");
        }

        String mainTenantId;
        while (true) {
            System.out.println("Enter main tenant ID:");
            mainTenantId = scanner.nextLine();
            if (isValidMainTenantId(mainTenantId)) break;
            System.out.println("Invalid main tenant ID. Please enter a valid main tenant ID.");
        }

        List<String> subTenantIds = new ArrayList<>();
        System.out.println("Enter sub-tenant IDs (comma-separated, press enter to skip):");
        String subTenantIdsStr = scanner.nextLine();
        if (!subTenantIdsStr.isEmpty()) {
            subTenantIds = Arrays.asList(subTenantIdsStr.split(","));
            while (!isValidSubTenantIds(subTenantIds)) {
                System.out.println("Invalid sub-tenant IDs. Please enter valid sub-tenant IDs (comma-separated, press enter to skip):");
                subTenantIdsStr = scanner.nextLine();
                if (subTenantIdsStr.isEmpty()) {
                    subTenantIds = new ArrayList<>();
                    break;
                }
                subTenantIds = Arrays.asList(subTenantIdsStr.split(","));
            }
        }

        String ownerId;
        while (true) {
            System.out.println("Enter owner ID:");
            ownerId = scanner.nextLine();
            if (isValidOwnerId(ownerId)) break;
            System.out.println("Invalid owner ID. Please enter a valid owner ID.");
        }

        String hostId;
        while (true) {
            System.out.println("Enter host ID:");
            hostId = scanner.nextLine();
            if (isValidHostId(hostId)) break;
            System.out.println("Invalid host ID. Please enter a valid host ID.");
        }

        System.out.println("Enter rental period (e.g., Monthly, Weekly):");
        String period = scanner.nextLine();

        Date contractDate;
        while (true) {
            System.out.println("Enter contract date (YYYY-MM-DD):");
            String contractDateStr = scanner.nextLine();
            if (isValidDate(contractDateStr)) {
                try {
                    contractDate = new SimpleDateFormat("yyyy-MM-dd").parse(contractDateStr);
                    break;
                } catch (ParseException e) {
                    System.out.println("Invalid date format. Please enter again (YYYY-MM-DD):");
                }
            } else {
                System.out.println("Invalid date format. Please enter again (YYYY-MM-DD):");
            }
        }

        double mainTenantRentingFee;
        while (true) {
            System.out.println("Enter main tenant renting fee:");
            String rentingFeeStr = scanner.nextLine();
            if (isValidRentingFee(rentingFeeStr)) {
                mainTenantRentingFee = Double.parseDouble(rentingFeeStr);
                break;
            } else {
                System.out.println("Invalid renting fee. Please enter a valid number.");
            }
        }

        Date mainTenantPaymentDate;
        while (true) {
            System.out.println("Enter main tenant payment date (YYYY-MM-DD):");
            String paymentDateStr = scanner.nextLine();
            if (isValidDate(paymentDateStr)) {
                try {
                    mainTenantPaymentDate = new SimpleDateFormat("yyyy-MM-dd").parse(paymentDateStr);
                    break;
                } catch (ParseException e) {
                    System.out.println("Invalid date format. Please enter again (YYYY-MM-DD):");
                }
            } else {
                System.out.println("Invalid date format. Please enter again (YYYY-MM-DD):");
            }
        }

        System.out.println("Enter main tenant payment method:");
        String mainTenantPaymentMethod = scanner.nextLine();

        List<Double> subTenantRentingFees = new ArrayList<>();
        List<Date> subTenantPaymentDates = new ArrayList<>();
        List<String> subTenantPaymentMethods = new ArrayList<>();
        for (String subTenantId : subTenantIds) {
            double subTenantRentingFee;
            while (true) {
                System.out.println("Enter renting fee for sub-tenant " + subTenantId + ":");
                String rentingFeeStr = scanner.nextLine();
                if (isValidRentingFee(rentingFeeStr)) {
                    subTenantRentingFee = Double.parseDouble(rentingFeeStr);
                    subTenantRentingFees.add(subTenantRentingFee);
                    break;
                } else {
                    System.out.println("Invalid renting fee. Please enter a valid number.");
                }
            }

            Date subTenantPaymentDate;
            while (true) {
                System.out.println("Enter payment date for sub-tenant " + subTenantId + " (YYYY-MM-DD):");
                String paymentDateStr = scanner.nextLine();
                if (isValidDate(paymentDateStr)) {
                    try {
                        subTenantPaymentDate = new SimpleDateFormat("yyyy-MM-dd").parse(paymentDateStr);
                        subTenantPaymentDates.add(subTenantPaymentDate);
                        break;
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please enter again (YYYY-MM-DD):");
                    }
                } else {
                    System.out.println("Invalid date format. Please enter again (YYYY-MM-DD):");
                }
            }

            System.out.println("Enter payment method for sub-tenant " + subTenantId + ":");
            String subTenantPaymentMethod = scanner.nextLine();
            subTenantPaymentMethods.add(subTenantPaymentMethod);
        }

        System.out.println("Enter status (e.g., Active, New):");
        String status = scanner.nextLine();

        String agreementId = generateRentalAgreementId(new File("src/data/rental_agreements.txt"));

        RentalAgreement newAgreement = new RentalAgreement(agreementId, propertyId, mainTenantId, subTenantIds, ownerId, hostId, period, contractDate, mainTenantRentingFee, status);
        rentalAgreementList.add(newAgreement);
        System.out.println("Rental agreement added successfully to the list!");

        // Add payments to paymentList
        String mainTenantPaymentId = generatePaymentId(new File("src/data/payments.txt"));
        Payment mainTenantPayment = new Payment(mainTenantPaymentId, mainTenantId, agreementId, mainTenantRentingFee, mainTenantPaymentDate, mainTenantPaymentMethod);
        paymentList.add(mainTenantPayment);

        for (int i = 0; i < subTenantIds.size(); i++) {
            String subTenantPaymentId = generatePaymentId(new File("src/data/payments.txt"));
            Payment subTenantPayment = new Payment(subTenantPaymentId, subTenantIds.get(i), agreementId, subTenantRentingFees.get(i), subTenantPaymentDates.get(i), subTenantPaymentMethods.get(i));
            paymentList.add(subTenantPayment);
        }
    }

    //Update Rental Agreement
    private static void updateRentalAgreement(Scanner scanner) throws ParseException {
        System.out.println("Enter the ID of the rental agreement to update:");
        String agreementId = scanner.nextLine();

        boolean agreementFound = false;

        for (RentalAgreement agreement : rentalAgreementList) {
            if (agreement.getAgreementId().equals(agreementId)) {
                agreementFound = true;
                System.out.println("Updating information for rental agreement " + agreement.getAgreementId());

                System.out.println("Enter new property ID (leave blank to keep current):");
                String propertyId = scanner.nextLine();
                if (!propertyId.isEmpty() && isValidPropertyId(propertyId)) {
                    agreement.setPropertyId(propertyId);
                }

                System.out.println("Enter new main tenant ID (leave blank to keep current):");
                String mainTenantId = scanner.nextLine();
                if (!mainTenantId.isEmpty() && isValidMainTenantId(mainTenantId)) {
                    agreement.setMainTenantId(mainTenantId);

                    // Update main tenant payment details
                    System.out.println("Enter new main tenant renting fee:");
                    double mainTenantRentingFee = Double.parseDouble(scanner.nextLine());
                    System.out.println("Enter new main tenant payment date (YYYY-MM-DD):");
                    Date mainTenantPaymentDate = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());
                    System.out.println("Enter new main tenant payment method:");
                    String mainTenantPaymentMethod = scanner.nextLine();

                    // Add new payment to paymentList
                    String mainTenantPaymentId = generatePaymentId(new File("src/data/payments.txt"));
                    Payment mainTenantPayment = new Payment(mainTenantPaymentId, mainTenantId, agreementId, mainTenantRentingFee, mainTenantPaymentDate, mainTenantPaymentMethod);
                    paymentList.add(mainTenantPayment);
                }

                System.out.println("Enter new sub-tenant IDs (comma-separated, leave blank to keep current):");
                String subTenantIdsStr = scanner.nextLine();
                if (!subTenantIdsStr.isEmpty()) {
                    List<String> subTenantIds = Arrays.asList(subTenantIdsStr.split(","));
                    if (isValidSubTenantIds(subTenantIds)) {
                        agreement.setSubTenantIds(subTenantIds);

                        // Update sub-tenant payment details
                        for (String subTenantId : subTenantIds) {
                            System.out.println("Enter new renting fee for sub-tenant " + subTenantId + ":");
                            double subTenantRentingFee = Double.parseDouble(scanner.nextLine());
                            System.out.println("Enter new payment date for sub-tenant " + subTenantId + " (YYYY-MM-DD):");
                            Date subTenantPaymentDate = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());
                            System.out.println("Enter new payment method for sub-tenant " + subTenantId + ":");
                            String subTenantPaymentMethod = scanner.nextLine();

                            // Add new payment to paymentList
                            String subTenantPaymentId = generatePaymentId(new File("src/data/payments.txt"));
                            Payment subTenantPayment = new Payment(subTenantPaymentId, subTenantId, agreementId, subTenantRentingFee, subTenantPaymentDate, subTenantPaymentMethod);
                            paymentList.add(subTenantPayment);
                        }
                    }
                }

                System.out.println("Enter new owner ID (leave blank to keep current):");
                String ownerId = scanner.nextLine();
                if (!ownerId.isEmpty() && isValidOwnerId(ownerId)) {
                    agreement.setOwnerId(ownerId);
                }

                System.out.println("Enter new host ID (leave blank to keep current):");
                String hostId = scanner.nextLine();
                if (!hostId.isEmpty() && isValidHostId(hostId)) {
                    agreement.setHostId(hostId);
                }

                System.out.println("Enter new rental period (leave blank to keep current):");
                String period = scanner.nextLine();
                if (!period.isEmpty()) {
                    agreement.setPeriod(period);
                }

                System.out.println("Enter new contract date (YYYY-MM-DD, leave blank to keep current):");
                String contractDateStr = scanner.nextLine();
                if (!contractDateStr.isEmpty() && isValidDate(contractDateStr)) {
                    try {
                        agreement.setContractDate(new SimpleDateFormat("yyyy-MM-dd").parse(contractDateStr));
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Keeping current contract date.");
                    }
                }

                System.out.println("Enter new renting fee (leave blank to keep current):");
                String rentingFeeStr = scanner.nextLine();
                if (!rentingFeeStr.isEmpty() && isValidRentingFee(rentingFeeStr)) {
                    agreement.setRentingFee(Double.parseDouble(rentingFeeStr));
                }

                System.out.println("Enter new status (leave blank to keep current):");
                String status = scanner.nextLine();
                if (!status.isEmpty()) {
                    agreement.setStatus(status);
                }

                System.out.println("Rental agreement information updated successfully.");
                break;
            }
        }

        if (!agreementFound) {
            System.out.println("Rental agreement with ID " + agreementId + " not found.");
        }
    }



    //delete rental agreement
    private static void deleteRentalAgreement(Scanner scanner) {
        System.out.println("Enter the ID of the rental agreement to delete:");
        String agreementId = scanner.nextLine();

        boolean agreementFound = false;
        Iterator<RentalAgreement> agreementIterator = rentalAgreementList.iterator();

        while (agreementIterator.hasNext()) {
            RentalAgreement agreement = agreementIterator.next();
            if (agreement.getAgreementId().equals(agreementId)) {
                agreementFound = true;
                agreementIterator.remove();
                System.out.println("Rental agreement " + agreement.getAgreementId() + " deleted successfully.");

                // Remove associated payments
                Iterator<Payment> paymentIterator = paymentList.iterator();
                while (paymentIterator.hasNext()) {
                    Payment payment = paymentIterator.next();
                    if (payment.getRentalAgreementId().equals(agreementId)) {
                        paymentIterator.remove();
                    }
                }
                break;
            }
        }

        if (!agreementFound) {
            System.out.println("Rental agreement with ID " + agreementId + " not found.");
        }
    }

    // ID generator for Rental Agreement
    private static String generateRentalAgreementId(File file) {
        int maxId = 0;
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length > 0 && parts[0].startsWith("RA")) {
                    try {
                        int id = Integer.parseInt(parts[0].substring(2));
                        if (id > maxId) {
                            maxId = id;
                        }
                    } catch (NumberFormatException e) {
                        // Ignore invalid IDs
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file for ID generation: " + e.getMessage());
        }
        return String.format("RA%03d", maxId + 1);
    }

    // ID generator for Payment
    private static String generatePaymentId(File file) {
        int maxId = 0;
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length > 0 && parts[0].startsWith("PM")) {
                    try {
                        int id = Integer.parseInt(parts[0].substring(2));
                        if (id > maxId) {
                            maxId = id;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing payment ID: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file for ID generation: " + e.getMessage());
        }
        return String.format("PM%03d", maxId + 1);
    }

    // Validation methods
    // Check if property ID is valid
    private static boolean isValidPropertyId(String propertyId) {
        for (Property property : propertyList) {
            if (property.getPropertyId().equals(propertyId)) {
                return true;
            }
        }
        return false;
    }

    // Check if main tenant ID is valid
    private static boolean isValidMainTenantId(String mainTenantId) {
        for (Person person : personList) {
            if (person.getId().equals(mainTenantId) && "Tenant".equalsIgnoreCase(person.getRole())) {
                return true;
            }
        }
        return false;
    }

    // Check if sub-tenant IDs are valid
    private static boolean isValidSubTenantIds(List<String> subTenantIds) {
        for (String subTenantId : subTenantIds) {
            boolean isValidSubTenant = false;
            for (Person person : personList) {
                if (person.getId().equals(subTenantId.trim()) && "Tenant".equalsIgnoreCase(person.getRole())) {
                    isValidSubTenant = true;
                    break;
                }
            }
            if (!isValidSubTenant) {
                return false;
            }
        }
        return true;
    }

    // Check if owner ID is valid
    private static boolean isValidOwnerId(String ownerId) {
        for (Person person : personList) {
            if (person.getId().equals(ownerId) && "Owner".equalsIgnoreCase(person.getRole())) {
                return true;
            }
        }
        return false;
    }

    // Check if host ID is valid
    private static boolean isValidHostId(String hostId) {
        for (Person person : personList) {
            if (person.getId().equals(hostId) && "Host".equalsIgnoreCase(person.getRole())) {
                return true;
            }
        }
        return false;
    }

    // Check if renting fee is valid
    private static boolean isValidRentingFee(String rentingFeeStr) {
        try {
            Double.parseDouble(rentingFeeStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Check if date is valid
    private static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // Check if email is valid
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(emailRegex);
    }

    //See instructions
    public static void instructions() {
        System.out.println("\n------------------------------------------------------------------ Instructions ------------------------------------------------------------------");
        System.out.println("Welcome to the instruction of the Rental Management System console app. Here are a few quick tips if you just started using the program");
        System.out.println("1. Please use the view function to get information on the IDs of tenants, hosts, owners, payments and properties");
        System.out.println("2. In the Manage Rental Agreements option (7), you will be able to edit tenants, hosts, owners and properties");
        System.out.println("3. There are no individual edit function for payments, as they are automatically generated or edited when a rental agreement is created or updated");
        System.out.println("5. All data will be saved automatically before the program exits.");
        System.out.println("6. Enjoy using the program!");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
    }
}

