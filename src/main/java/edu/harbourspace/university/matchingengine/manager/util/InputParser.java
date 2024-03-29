package edu.harbourspace.university.matchingengine.manager.util;

import edu.harbourspace.university.matchingengine.manager.model.Order;
import edu.harbourspace.university.matchingengine.manager.model.Originator;
import edu.harbourspace.university.matchingengine.manager.model.Side;
import edu.harbourspace.university.matchingengine.manager.model.CancelMessage;

public class InputParser {

    public Object parse(String inputLine) {
        String[] parts = inputLine.trim().split("\\s+");

        try {
            if (parts.length == 6) {
                // Parse and return an Order object
                Originator originator = Originator.valueOf(parts[0]);
                Side side = Side.valueOf(parts[2]);
                int size = Integer.parseInt(parts[3]);
                double price = Double.parseDouble(parts[4]);
                return new Order(originator, parts[1], side, size, price, parts[5]);
            } else if (parts.length == 3 && "CANCEL".equals(parts[2])) {
                // Return cancel message information
                return new CancelMessage(parts[1]); // Assuming you have a CancelMessage class
            } else {
                // Handle FINISH or invalid input
                return inputLine; // Return the raw input for further handling
            }
        } catch (Exception e) {
            System.out.println("Error parsing input: " + inputLine);
            return null; // or handle this as needed for your application
        }
    }
}

