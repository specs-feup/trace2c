import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class Tester {

    @Test
    public void hypothenusesLvl1() throws IOException {
        File expectedOutput = new File("..\\resources\\hypotenuses\\output.txt");
        File calculatedOutput = new File("..\\resources\\hypotenuses\\outputLvl1.txt");
        assertTrue("The files differ!", FileUtils.contentEquals(expectedOutput, calculatedOutput));
    }
}
