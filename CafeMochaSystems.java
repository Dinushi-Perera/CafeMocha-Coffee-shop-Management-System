package CafeMochaSystems;

import java.io.*;
import java.util.*;

public class CafeMochaSystems {
    private static final String LOGIN_FILE = "users.txt";
    private static final String MENU_FILE = "menu.txt";
    private static final String ORDERS_FILE = "orders.txt";
    private static final String BILL_FILE = "bills.txt";
    
    private static final double TAX_RATE = 0.10;
    private static final double DISCOUNT_RATE = 0.05;

    private static List<String[]> userCredentials = new ArrayList<>();
    private static List<String[]> menu = new ArrayList<>();
    private static List<String[]> orders = new ArrayList<>();

    public static void main(String[] args) {
        displayWelcomeMessage();
        loadUserCredentials();
        loadMenuItems();
        loadOrders();

        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;

        while (!loggedIn) {
            loggedIn = login(scanner);
            if (!loggedIn) {
            } else {
                System.out.println("Login successful! Welcome to Café Mocha System!");
            }
        }

        showMenu(scanner);
        scanner.close();
    }
    
    private static boolean login(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        for (String[] credentials : userCredentials) {
            if (credentials[0].equals(username) && credentials[1].equals(password)) {
                return true;
            }
        }
        return false;
    }
    
    
    static void displayWelcomeMessage() {
        drawDoubleLine();
        System.out.println("||                                                                                         ||");
        System.out.println("||                                   Welcome to the Cafe Mocha                             ||");
        System.out.println("||                                    Order Management System                              ||");
        System.out.println("||                                                                                         ||");
        drawDoubleLine();
        System.out.println("Please login to continue...");
    }
    
    private static void printCoffeeCup() {
        System.out.println("     ( ( (    ");
        System.out.println("      ) ) )   ");
        System.out.println("     ( ( (    ");
        System.out.println("   ___)_)_)__ ");
        System.out.println("  |          |");
        System.out.println("  |          |\\\\");
        System.out.println("  |  Cafe'   | ||");
        System.out.println("  |  Mocha   |//");
        System.out.println("  |          |");
        System.out.println("   \\________/");
        drawDoubleLine();
    }
    
    static void drawDoubleLine() {
        System.out.println("=============================================================================================");
    }
    
    private static void showMenu(Scanner scanner) {
        while (true) {
            drawDoubleLine();
            printCoffeeCup();
            drawDoubleLine();
            System.out.println("1. View All Menu           7. Add New Order");
            System.out.println("2. Add New Menu Item       8. Update Order");
            System.out.println("3. Update Menu Item        9. Delete an Order");
            System.out.println("4. Delete Menu Item        10.Calculate and Print Bill");
            System.out.println("5. Search Menu Item        11.Help");
            System.out.println("6. View All Orders         12.Exit");
            drawDoubleLine();
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1: viewAllMenu(); break;
                case 2: addMenuItem(scanner); break;
                case 3: updateMenuItem(scanner); break;
                case 4: deleteMenuItem(scanner); break;
                case 5: searchItem(scanner); break;
                case 6: viewAllOrders(); break;
                case 7: addOrder(scanner); break;
                case 8: updateOrder(scanner); break;
                case 9: deleteOrder(scanner); break;
                case 10: calculateBill(scanner); break;
                case 11: showHelp(); break;
                case 12: System.out.println("Exiting the system."); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }
    
    private static void loadUserCredentials() {
        try (BufferedReader br = new BufferedReader(new FileReader(LOGIN_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                userCredentials.add(line.split(","));
            }
        } catch (IOException e) {
            System.out.println("Error loading user credentials.");
        }
    }
    
    private static void loadMenuItems() {
        try (BufferedReader br = new BufferedReader(new FileReader(MENU_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                menu.add(line.split(","));
            }
        } catch (IOException e) {
            System.out.println("Error loading menu items.");
        }
    }
    
    private static void loadOrders() {
        try (BufferedReader br = new BufferedReader(new FileReader(ORDERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                orders.add(line.split(",", 5));
            }
        } catch (IOException e) {
            System.out.println("Error loading orders.");
        }
    }
    
    private static void saveMenuItems() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MENU_FILE))) {
            for (String[] item : menu) {
                bw.write(String.join(",", item));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving menu items.");
        }
    }
    
    private static void saveOrders() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ORDERS_FILE))) {
            for (String[] order : orders) {
                bw.write(String.join(",", order));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving orders.");
        }
    }
    
    private static void viewAllMenu() {
        drawDoubleLine();
        System.out.println("||                                      MENU                                              ||");
        drawDoubleLine();
        System.out.printf("%-20s %-30s %-10s %-20s\n", "Item Name", "Description", "Price", "Category");
        drawDoubleLine();
        
        for (String[] item : menu) {
            System.out.printf("%-20s %-30s %-10.2f %-20s\n", item[0], item[1], Double.parseDouble(item[2]), item[3]);
        }
    }
    
    private static void addMenuItem(Scanner scanner) {
        drawDoubleLine();
        System.out.println("||                                   ADD NEW ITEM                                         ||");
        drawDoubleLine();

        System.out.print("Enter Item Name: ");
        String id = scanner.nextLine();
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        
        menu.add(new String[] {id, description, Double.toString(price), category});
        saveMenuItems();
        System.out.println("Menu item added.");
    }
    
    private static void updateMenuItem(Scanner scanner) {
        drawDoubleLine();
        System.out.println("||                                UPDATE ITEM                                             ||");
        drawDoubleLine();

        System.out.print("Enter Item Name to update: ");
        String id = scanner.nextLine();
        for (String[] item : menu) {
            if (item[0].equals(id)) {
                System.out.print("Enter new Description: ");
                String description = scanner.nextLine();
                System.out.print("Enter new Price: ");
                double price = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter new Category: ");
                String category = scanner.nextLine();
                
                item[1] = description;
                item[2] = Double.toString(price);
                item[3] = category;
                saveMenuItems();
                System.out.println("Menu item updated.");
                return;
            }
        }
        System.out.println("Menu item not found.");
    }
    
    private static void deleteMenuItem(Scanner scanner) {
        drawDoubleLine();
        System.out.println("||                                   DELETE ITEM                                           ||");
        drawDoubleLine();

        System.out.print("Enter Item Name to delete: ");
        String id = scanner.nextLine();
        for (Iterator<String[]> iterator = menu.iterator(); iterator.hasNext(); ) {
            String[] item = iterator.next();
            if (item[0].equals(id)) {
                iterator.remove();
                saveMenuItems();
                System.out.println("Menu item deleted.");
                return;
            }
        }
        System.out.println("Menu item not found.");
    }
    
    private static void searchItem(Scanner scanner) {
        drawDoubleLine();
        System.out.println("||                                 SEARCH ITEM                                            ||");
        drawDoubleLine();

        System.out.print("Enter Item Name to search: ");
        String id = scanner.nextLine();
        for (String[] item : menu) {
            if (item[0].equals(id)) {
                System.out.printf("%-20s %-30s %-10.2f %-20s\n", item[0], item[1], Double.parseDouble(item[2]), item[3]);
                return;
            }
        }
        System.out.println("Menu item not found.");
    }
 
private static void viewAllOrders() {
    drawDoubleLine();
    System.out.println("||                                        ORDERS                                            ||");
    drawDoubleLine();
    System.out.printf("%-10s %-15s %-20s %-15s %-30s\n", "Order No", "Customer", "Address", "Phone", "Items");
    drawDoubleLine();
    
    for (String[] order : orders) {
        if (order.length < 5) {
            continue;
        }
        String orderNumber = order[0];
        String customerName = order[1];
        String address = order[2];
        String phoneNumber = order[3];
        String items = order[4].replace(";", ", "); // Replacing semicolons with commas for better readability
        
        System.out.printf("%-10s %-15s %-20s %-15s %-30s\n", orderNumber, customerName, address, phoneNumber, items);
    }
}
   
private static void addOrder(Scanner scanner) {
    drawDoubleLine();
    System.out.println("||                                    ADD NEW ORDER                                        ||");
    drawDoubleLine();
    
    System.out.print("Enter Order Number: ");
    String orderNumber = scanner.nextLine();
    
    // Check if order number already exists
    for (String[] order : orders) {
        if (order[0].equals(orderNumber)) {
            System.out.println("This order number already exists.");
            return;
        }
    }
    
    System.out.print("Enter Customer Name: ");
    String customerName = scanner.nextLine();
    System.out.print("Enter Address: ");
    String address = scanner.nextLine();
    System.out.print("Enter Phone Number: ");
    String phoneNumber = scanner.nextLine();
    viewAllMenu();
    
    List<String> itemList = new ArrayList<>();
    while (true) {
        System.out.print("Enter Item Name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        String[] menuItem = findMenuItem(itemName);
        if (menuItem != null) {
            itemList.add(itemName + ":" + quantity);
        } else {
            System.out.println("Item not found in menu.");
        }
        
        System.out.print("Add another item? (y/n): ");
        String addMore = scanner.nextLine();
        if (!addMore.equalsIgnoreCase("y")) {
            break;
        }
    }
    
    // Join all items into a single string
    String items = String.join(";", itemList);
    
    // Add new order to the list
    orders.add(new String[] { orderNumber, customerName, address, phoneNumber, items });
    
    System.out.println("Order added.");
    saveOrders(); // Save the orders after adding a new one
}

    private static String[] findMenuItem(String itemId) {
    for (String[] item : menu) {
        if (item[0].equals(itemId)) {
            return item;
        }
    }
    return null; // Item not found
}
    
    
private static void updateOrder(Scanner scanner) {
    drawDoubleLine();
    System.out.println("||                                        UPDATE ORDER                                     ||");
    drawDoubleLine();
    
    System.out.print("Enter Order Number to Update: ");
    String orderNumberToUpdate = scanner.nextLine();
    
    // Find the order to update
    int orderIndex = -1;
    for (int i = 0; i < orders.size(); i++) {
        if (orders.get(i)[0].equals(orderNumberToUpdate)) {
            orderIndex = i;
            break;
        }
    }
    
    if (orderIndex == -1) {
        System.out.println("Order number not found.");
        return;
    }
    
    String[] orderToUpdate = orders.get(orderIndex);
    
    // Update fields
    System.out.print("Enter New Customer Name (leave blank to keep unchanged): ");
    String newCustomerName = scanner.nextLine();
    if (!newCustomerName.trim().isEmpty()) {
        orderToUpdate[1] = newCustomerName;
    }
    
    System.out.print("Enter New Address (leave blank to keep unchanged): ");
    String newAddress = scanner.nextLine();
    if (!newAddress.trim().isEmpty()) {
        orderToUpdate[2] = newAddress;
    }
    
    System.out.print("Enter New Phone Number (leave blank to keep unchanged): ");
    String newPhoneNumber = scanner.nextLine();
    if (!newPhoneNumber.trim().isEmpty()) {
        orderToUpdate[3] = newPhoneNumber;
    }
    
    // Update items
    List<String> itemList = new ArrayList<>();
    while (true) {
        System.out.print("Enter Item Name to Update (or type 'done' to finish): ");
        String itemName = scanner.nextLine();
        if (itemName.equalsIgnoreCase("done")) {
            break;
        }
        
        System.out.print("Enter New Quantity: ");
        int newQuantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        String[] menuItem = findMenuItem(itemName);
        if (menuItem != null) {
            itemList.add(itemName + ":" + newQuantity);
        } else {
            System.out.println("Item not found in menu.");
        }
    }
    
    // Join all items into a single string
    String updatedItems = String.join(";", itemList);
    orderToUpdate[4] = updatedItems;
    
    // Update the orders list
    orders.set(orderIndex, orderToUpdate);
    
    // Save updated orders to file
    saveOrders();
    
    System.out.println("Order updated.");
}


    
    private static void deleteOrder(Scanner scanner) {
        drawDoubleLine();
        System.out.println("||                                   DELETE ORDER                                        ||");
        drawDoubleLine();

        System.out.print("Enter Order Number to delete: ");
        String orderNumber = scanner.nextLine();
        for (Iterator<String[]> iterator = orders.iterator(); iterator.hasNext(); ) {
            String[] order = iterator.next();
            if (order[0].equals(orderNumber)) {
                iterator.remove();
                saveOrders();
                System.out.println("Order deleted.");
                return;
            }
        }
        System.out.println("Order not found.");
    }
    
private static void calculateBill(Scanner scanner) {
    drawDoubleLine();
    System.out.println("||                                  CAFE MOCHA BILL                                        ||");
    drawDoubleLine();
    
    System.out.print("Enter Order Number: ");
    String orderNumber = scanner.nextLine();
    
    double subtotal = 0;
    boolean discountApplied = false;
    boolean orderFound = false;
    
    StringBuilder billDetails = new StringBuilder();
    billDetails.append(String.format("Order Number: %s\n", orderNumber));
    billDetails.append("-------------------------------------------------\n");
    billDetails.append("Item Name      Quantity   Price   Total\n");
    billDetails.append("-------------------------------------------------\n");
    
    for (String[] order : orders) {
        if (order[0].equals(orderNumber)) {
            orderFound = true;
            String[] items = order[4].split(";"); // Split items by semicolon
            
            for (String item : items) {
                String[] itemDetails = item.split(":"); // Split item name and quantity
                String itemName = itemDetails[0];
                int quantity = Integer.parseInt(itemDetails[1]);
                
                // Find the price of the item from the menu
                String[] menuItem = findMenuItem(itemName);
                if (menuItem != null) {
                    double price = Double.parseDouble(menuItem[2]);
                    double itemTotal = quantity * price;
                    subtotal += itemTotal;
                    
                    // Append item details to the bill
                    billDetails.append(String.format("%-14s %-9d Rs.%-7.2f Rs.%.2f\n", itemName, quantity, price, itemTotal));
                    
                    // Apply discount if any item quantity exceeds 5
                    if (quantity > 5) {
                        discountApplied = true;
                    }
                }
            }
            break; // Exit loop after finding the correct order
        }
    }
    
    if (!orderFound) {
        System.out.println("Order not found.");
        return;
    }
    
    double discount = discountApplied ? subtotal * 0.05 : 0;
    double tax = subtotal * 0.10;
    double total = subtotal + tax - discount;
    
    // Append the summary of the bill
    billDetails.append("-------------------------------------------------\n");
    billDetails.append(String.format("Subtotal:                         Rs.%.2f\n", subtotal));
    if (discountApplied) {
        billDetails.append(String.format("Discount (5%%):                    Rs.-%.2f\n", discount));
    }
    billDetails.append(String.format("Tax (10%%):                        Rs.%.2f\n", tax));
    billDetails.append("-------------------------------------------------\n");
    billDetails.append(String.format("Total:                            Rs.%.2f\n", total));
    billDetails.append("-------------------------------------------------\n");
    
    // Print the bill details
    System.out.println(billDetails.toString());
    
    // Save the bill to a file
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(BILL_FILE, true))) {
        bw.write(billDetails.toString());
        bw.newLine();
    } catch (IOException e) {
        System.out.println("Error saving bill.");
    }
    
    System.out.println("Bill calculated and saved.");
    System.out.println("Thank You.");
    
}



    
    private static void showHelp() {
        drawDoubleLine();
        System.out.println("||                                         HELP                                            ||");
        drawDoubleLine();
        System.out.println("1. View All Menu: Displays all items in the menu.");
        System.out.println("2. Add New Menu Item: Allows you to add a new item to the menu.");
        System.out.println("3. Update Menu Item: Allows you to update an existing menu item.");
        System.out.println("4. Delete Menu Item: Allows you to delete a menu item.");
        System.out.println("5. Search Menu Item: Allows you to search for a specific menu item.");
        System.out.println("6. View All Orders: Displays all orders.");
        System.out.println("7. Add New Order: Allows you to add a new order.");
        System.out.println("8. Update Order: Allows you to update an existing order.");
        System.out.println("9. Delete an Order: Allows you to delete an order.");
        System.out.println("10.Bill: Calculates and prints the bill for a specific order.");
        System.out.println("11. Help: Displays this help menu.");
        System.out.println("12. Exit: Exits the system.");
        drawDoubleLine();
    }
}
