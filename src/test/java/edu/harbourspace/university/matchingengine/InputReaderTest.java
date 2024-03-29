package edu.harbourspace.university.matchingengine;

import edu.harbourspace.university.matchingengine.manager.util.InputReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.*;

class InputReaderTest {

    private final InputStream originalIn = System.in;
    private InputReader inputReader;

    @BeforeEach
    void setUp() {
        String simulatedUserInput = "DF MSG123 BUY 100 50.0 ProductABC\n" +
                "VE MSG124 SELL 50 45.0 ProductXYZ\n" +
                "FINISH\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        inputReader = new InputReader();
    }

    @Test
    void getNextLine_ShouldReturnCorrectLines() {
        assertEquals("DF MSG123 BUY 100 50.0 ProductABC", inputReader.getNextLine());
        assertEquals("VE MSG124 SELL 50 45.0 ProductXYZ", inputReader.getNextLine());
        assertEquals("FINISH", inputReader.getNextLine());
    }

    @AfterEach
    void restoreSystemIn() {
        System.setIn(originalIn);
        inputReader.close();
    }
}


