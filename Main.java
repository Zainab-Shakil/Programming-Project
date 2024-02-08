import java.io.*;
import java.util.Scanner;

public class Main {
    File CAR_FILE = new File("cars.txt");
    File CUSTOMER_FILE = new File("customers.txt");
    File RENT_FILE = new File("rents.txt");

    public static void main(String[] args) {
        Main main = new Main();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nCar Rental System Menu:");
            System.out.println("1. Add a car");
            System.out.println("2. Show car details");
            System.out.println("3. Availability of car");
            System.out.println("4. Delete a car");
            System.out.println("5. Rent a car");
            System.out.println("6. New customers");
            System.out.println("7. All customers");
            System.out.println("8. Customer rental details");
            System.out.println("9. Clear customers rent");
            System.out.println("10. Remove customer");
            System.out.println("11. Show all rents");
            System.out.println("12. Show all customers details");
            System.out.println("13. Rent time");
            System.out.println("14. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    main.addCar();
                    break;
                case 2:
                    main.showCarDetails();
                    break;
                case 3:
                    main.checkCarAvailability();
                    break;
                case 4:
                    main.deleteCar();
                    break;
                case 5:
                    main.rentCar();
                    break;
                case 6:
                    main.addCustomer();
                    break;
                case 7:
                    main.showAllCustomers();
                    break;
                case 8:
                    main.showCustomerRentalDetails();
                    break;
                case 9:
                    main.clearCustomerRent();
                    break;
                case 10:
                    main.removeCustomer();
                    break;
                case 11:
                    main.showAllRents();
                    break;
                case 12:
                    main.showAllCustomersDetails();
                    break;
                case 13:
                    main.showRentTime();
                    break;
                case 14:
                    System.out.println("Exiting Car Rental System. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    public void addCar() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter car model: ");
        String model = scanner.next();
        System.out.print("Enter car seater: ");
        int seater = scanner.nextInt();
        System.out.print("Enter car color: ");
        String color = scanner.next();

        try (PrintWriter writer = new PrintWriter(new FileWriter(CAR_FILE, true))) {
            writer.write(model + "," + seater + "," + color + ",available\n");
            System.out.println("Car added successfully!");
        } catch (IOException e) {
            System.out.println("Error adding car: " + e.getMessage());
        }
    }
    public void showCarDetails() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CAR_FILE))) {
            String line;
            System.out.println("Car Details:");
            while ((line = reader.readLine()) != null) {
                String[] carData = line.split(",");
                System.out.println("Model: " + carData[0] + ", Seater: " + carData[1] + ", Color: " + carData[2] + ", Availability: " + carData[3]);
            }
        } catch (IOException e) {
            System.out.println("Error showing car details: " + e.getMessage());
        }
    }

    public static void checkCarAvailability() {
        System.out.print("Enter car model to check availability: ");
        Scanner scanner = new Scanner(System.in);
        String checkModel = scanner.next();

        try (BufferedReader reader = new BufferedReader(new FileReader(CAR_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] carData = line.split(",");
                if (carData[0].equals(checkModel) && carData[3].equals("available")) {
                    System.out.println("Car is available for rent.");
                    return;
                }
            }
            System.out.println("Car not found or not available for rent.");
        } catch (IOException e) {
            System.out.println("Error checking car availability: " + e.getMessage());
        }
    }

    public static void deleteCar() {
        System.out.print("Enter car model to delete: ");
        Scanner scanner = new Scanner(System.in);
        String deleteModel = scanner.next();

        java.util.List<String> carDataList = new java.util.ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CAR_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] carData = line.split(",");
                if (!carData[0].equals(deleteModel)) {
                    carDataList.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error deleting car: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAR_FILE))) {
            for (String carData : carDataList) {
                writer.write(carData + "\n");
            }
            System.out.println("Car deleted successfully!");
        } catch (IOException e) {
            System.out.println("Error deleting car: " + e.getMessage());
        }
    }

    public static void rentCar() {
        showCarDetails(); // Display available cars
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter customer name: ");
        String customerName = scanner.next();
        System.out.print("Enter car model to rent: ");
        String rentCarModel = scanner.next();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMER_FILE, true))) {
            writer.write(customerName + "," + rentCarModel + "\n");
            System.out.println("Car rented successfully!");
        } catch (IOException e) {
            System.out.println("Error renting car: " + e.getMessage());
        }
    }

    public static void addCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new customer name: ");
        String newCustomerName = scanner.next();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMER_FILE, true))) {
            writer.write(newCustomerName + "\n");
            System.out.println("Customer added successfully!");
        } catch (IOException e) {
            System.out.println("Error adding customer: " + e.getMessage());
        }
    }

    public static void showAllCustomers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMER_FILE))) {
            System.out.println("Customer Details:");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] customerData = line.split(",");
                System.out.println("Name: " + customerData[0] + ", Rented Car: " + customerData[1]);
            }
        } catch (IOException e) {
            System.out.println("Error showing customer details: " + e.getMessage());
        }
    }

    public static void showCustomerRentalDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter customer name to view rental details: ");
        String customerName = scanner.next();

        try (BufferedReader reader = new BufferedReader(new FileReader(RENT_FILE))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] rentData = line.split(",");
                if (rentData[0].equals(customerName)) {
                    found = true;
                    System.out.println("Rented Car: " + rentData[1] + ", Rent Time: " + rentData[2] + " hours");
                    break;
                }
            }
            if (!found) {
                System.out.println("Customer not found.");
            }
        } catch (IOException e) {
            System.out.println("Error showing customer rental details: " + e.getMessage());
        }
    }

    public static void clearCustomerRent() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter customer name to clear rent: ");
        String customerName = scanner.next();

        try (BufferedReader reader = new BufferedReader(new FileReader(RENT_FILE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(RENT_FILE + ".tmp"))) {

            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] rentData = line.split(",");
                if (rentData[0].equals(customerName)) {
                    found = true;
                    System.out.println("Customer rent cleared successfully!");
                } else {
                    writer.write(line + "\n");
                }
            }

            if (!found) {
                System.out.println("Customer not found.");
            }

        } catch (IOException e) {
            System.out.println("Error clearing customer rent: " + e.getMessage());
        }

        // Replace the original file with the temporary file
        File originalFile = new File(RENT_FILE);
        File tempFile = new File(RENT_FILE + ".tmp");
        tempFile.renameTo(originalFile);
    }

    public static void removeCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter customer name to remove: ");
        String customerName = scanner.next();

        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMER_FILE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMER_FILE + ".tmp"))) {

            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] customerData = line.split(",");
                if (customerData[0].equals(customerName)) {
                    found = true;
                    System.out.println("Customer removed successfully!");
                } else {
                    writer.write(line + "\n");
                }
            }

            if (!found) {
                System.out.println("Customer not found.");
            }

        } catch (IOException e) {
            System.out.println("Error removing customer: " + e.getMessage());
        }

        // Replace the original file with the temporary file
        File originalFile = new File(CUSTOMER_FILE);
        File tempFile = new File(CUSTOMER_FILE + ".tmp");
        tempFile.renameTo(originalFile);
    }

    public static void showAllRents() {
        try (BufferedReader reader = new BufferedReader(new FileReader(RENT_FILE))) {
            String line;
            System.out.println("All Rents:");

            while ((line = reader.readLine()) != null) {
                String[] rentData = line.split(",");
                System.out.println("Customer: " + rentData[0] + ", Rent Time: " + rentData[1] + " hours");
            }

        } catch (IOException e) {
            System.out.println("Error showing all rents: " + e.getMessage());
        }
    }

    public static void showAllCustomersDetails() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMER_FILE))) {
            System.out.println("Customer Details:");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] customerData = line.split(",");
                System.out.println("Name: " + customerData[0] + ", Rented Car: " + customerData[1]);
            }
        } catch (IOException e) {
            System.out.println("Error showing customer details: " + e.getMessage());
        }
    }
    public void showRentTime() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter customer name to check rent time: ");
        String customerName = scanner.next();

        try (BufferedReader reader = new BufferedReader(new FileReader(RENT_FILE))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] rentData = line.split(",");
                if (rentData[0].equals(customerName)) {
                    found = true;
                    System.out.println("Rent Time for " + customerName + ": " + rentData[2] + " hours");
                    break;
                }
            }
            if (!found) {
                System.out.println("Customer not found.");
            }
        } catch (IOException e) {
            System.out.println("Error showing rent time: " + e.getMessage());
        }
    }
}
