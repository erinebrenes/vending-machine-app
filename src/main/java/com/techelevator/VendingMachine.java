package com.techelevator;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class VendingMachine {
    public List<Product> itemArray = new ArrayList<>();
    public Map<String, Product> inventory = new HashMap<>();
    private BigDecimal currentBalance;

    public VendingMachine() {
        this.currentBalance = BigDecimal.ZERO;
        this.fillInventory();
    }

    public void fillInventory() {
        String fileName = "vendingmachine.csv";
        Map<String, Product> keymap = new HashMap<>();

        String key = null;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                BigDecimal price = new BigDecimal(parts[2]);
                key = parts[0];
                String snackType = parts[3];
                Product value = new Product(key, parts[1], price, snackType);
                keymap.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        inventory = keymap;
    }


    public void displayItems() {

        itemArray.clear();

        File inputFile = new File("vendingmachine.csv");
        try (Scanner fileScanner = new Scanner(inputFile.getAbsoluteFile())) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                System.out.println(line);
                String[] itemData = line.split("\\|");
                String slotId = itemData[0];
                String name = itemData[1];
                BigDecimal price = new BigDecimal(itemData[2]);
                String product = itemData[3];

                if (product.equals("Chip")) {
                    itemArray.add(new Chip(slotId, name, price, product));
                } else if (product.equals("Candy")) {
                    itemArray.add(new Candy(slotId, name, price, product));
                } else if (product.equals("Drink")) {
                    itemArray.add(new Drink(slotId, name, price, product));
                } else if (product.equals("Gum")) {
                    itemArray.add(new Gum(slotId, name, price, product));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public String purchaseHelper(){

        Scanner input = new Scanner(System.in);
//        printCenteredWord("Enter Slot Code", 30);
        System.out.println("Enter Slot Code");
        System.out.print("Slot Id: ");
        String Input = input.nextLine();

        String caseInsensitiveInput = Input.toUpperCase();

        if (inventory.containsKey(caseInsensitiveInput)) {
            System.out.println("You have selected: " + caseInsensitiveInput + ". Is this correct? (Y\\N)");
            Scanner scanner = new Scanner(System.in);
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("Y")) {
                this.purchase(caseInsensitiveInput);
            }

        } else {
            System.out.println("INVALID SLOT ID");
        }
        return caseInsensitiveInput;
    }

    public void addMoney(BigDecimal amount) {
        currentBalance = currentBalance.add(amount);
    }

    public BigDecimal purchase(String userInput) {
        Product item = inventory.get(userInput);

                if (currentBalance.compareTo(item.getPrice()) < 0) {
                    System.out.println("INSUFFICIENT FUNDS");
                    return currentBalance;
                } else {
                    if (item.getQuantity() <= 0) {
                        System.out.println("OUT OF STOCK");
                    } else {
                        System.out.println("------------------------------");
                        currentBalance = currentBalance.subtract(item.getPrice());
                        System.out.println("Remaining Balance: $" + currentBalance);
                        System.out.println(item.getName() + " | Item price: $" + item.getPrice());
                        System.out.println(item.getMessage());
                        System.out.println("------------------------------");
                        item.decrement();

                        TransactionLog purchaseLog = new TransactionLog();
                        purchaseLog.purchaseLogTransaction(item.getName(), item.getSlotId(), item.getPrice(), currentBalance);

                    }
                }
        return currentBalance;
    }

    public Map<String, Integer> returnChange() {
        BigDecimal balance = currentBalance;

        Map<String, Integer> coins = new HashMap<>();
        coins.put("quarters", 0);
        coins.put("dimes", 0);
        coins.put("nickels", 0);

        BigDecimal quarterValue = new BigDecimal("0.25");
        BigDecimal dimeValue = new BigDecimal("0.10");
        BigDecimal nickelValue = new BigDecimal("0.05");

        while (balance.compareTo(BigDecimal.ZERO) > 0) {
            if (balance.compareTo(quarterValue) >= 0) {
                balance = balance.subtract(quarterValue);
                coins.put("quarters", coins.get("quarters") + 1);
            } else if (balance.compareTo(dimeValue) >= 0) {
                balance = balance.subtract(dimeValue);
                coins.put("dimes", coins.get("dimes") + 1);
            } else {
                balance = balance.subtract(nickelValue);
                coins.put("nickels", coins.get("nickels") + 1);
            }
        }

        currentBalance = BigDecimal.ZERO;
        return coins;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

//    public void printCenteredPhrase(String format, int containerWidth) {
//        String word = String.format(format);
//        int padding = (containerWidth - word.length()) / 2;
//
//        System.out.printf("%" + (padding + word.length()) + "s%n", word);
//    }
//
//    public void printCenteredWord(String word, int containerWidth) {
//        int padding = (containerWidth - word.length()) / 2;
//        String dashes = "-".repeat(containerWidth);
//
//        System.out.println(dashes);
//        System.out.printf("%" + (padding + word.length()) + "s%n", word);
//        System.out.println(dashes);
//    }
//
//    public void printCenteredClosing(String word, int containerWidth) {
//        int padding = (containerWidth - word.length()) / 2;
//        String dashes = "-".repeat(containerWidth);
//
//        System.out.printf("%" + (padding + word.length()) + "s%n", word);
//        System.out.println(dashes);
//    }
}