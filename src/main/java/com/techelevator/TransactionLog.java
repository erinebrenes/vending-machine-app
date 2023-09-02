package com.techelevator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionLog {

    public List<String> transactions;

    public TransactionLog() {
        this.transactions = new ArrayList<>();
    }

    public void writeToFile(String transaction) {
        try {
            File logFile = new File("Log.txt");

            FileWriter writer = new FileWriter(logFile, true);
            writer.write(transaction + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to transaction log file");
        }
    }

    public void logTransaction(BigDecimal amount, BigDecimal newBalance, String description) {
        String transaction = getTimestamp() + " " + description + ": $" + amount + " $" + newBalance;
        transactions.add(transaction);
        writeToFile(transaction);
    }

    public void purchaseLogTransaction(String itemName, String slotID, BigDecimal cost, BigDecimal newBalance) {
        String purchaseTransaction = getTimestamp() + " " + itemName + " " + slotID + " " + cost + " " + newBalance;
        transactions.add(purchaseTransaction);
        writeToFile(purchaseTransaction);
    }

    public void giveChange(String itemName, BigDecimal amount, BigDecimal newBalance) {
        String changeTransaction = getTimestamp() + " " + itemName + ": $" + amount + " $" + newBalance;
        writeToFile(changeTransaction);
    }

    public String getTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        // new Date method returns Date object with current date and time
        return dateFormat.format(new Date());
    }
}