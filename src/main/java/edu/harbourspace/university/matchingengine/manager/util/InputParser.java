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
                Originator originator = Originator.valueOf(parts[0]);
                Side side = Side.valueOf(parts[2]);
                int size = Integer.parseInt(parts[3]);
                double price = Double.parseDouble(parts[4]);
                return new Order(originator, parts[1], side, size, price, parts[5]);
            } else if (parts.length == 3 && "CANCEL".equals(parts[2])) {
                return new CancelMessage(parts[1]);
            } else {
                return inputLine;
            }
        } catch (Exception e) {
            System.out.println("Error parsing input: " + inputLine);
            return null;
        }
    }
}

