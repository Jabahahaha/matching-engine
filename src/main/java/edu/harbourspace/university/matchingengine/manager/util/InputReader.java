package edu.harbourspace.university.matchingengine.manager.util;

import java.util.Scanner;

public class InputReader {

    private final Scanner scanner;

    public InputReader() {
        this.scanner = new Scanner(System.in);
    }

    public String getNextLine() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        } else {
            return null;
        }
    }

    public void close() {
        scanner.close();
    }
}
