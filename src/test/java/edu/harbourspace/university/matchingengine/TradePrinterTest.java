package edu.harbourspace.university.matchingengine;

import edu.harbourspace.university.matchingengine.manager.model.Side;
import edu.harbourspace.university.matchingengine.manager.model.Trade;
import edu.harbourspace.university.matchingengine.manager.util.TradePrinter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TradePrinterTest {

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final TradePrinter tradePrinter = new TradePrinter();

    @BeforeEach
    void setUpStream() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void printTrades_ShouldPrintTradeInformation() {
        // Arrange
        List<Trade> trades = List.of(
                new Trade(Side.BUY, 100, 50.0, "ProductABC"),
                new Trade(Side.SELL, 150, 55.0, "ProductXYZ")
        );

        // Act
        tradePrinter.printTrades(trades);

        // Assert
        String expectedOutput = trades.stream()
                .map(Trade::toString)
                .collect(Collectors.joining("\n", "", "\n\n")); // Assuming toString is properly overridden in Trade class
        assertEquals(expectedOutput, outContent.toString());
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }
}
