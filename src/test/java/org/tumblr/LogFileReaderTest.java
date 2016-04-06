package org.tumblr;

import org.junit.Test;

import java.io.IOException;

/**
 * Created by thor on 5/4/16.
 */
public class LogFileReaderTest {
    @Test
    public void testReader() throws IOException{
        LogFileReader logFileReader = new LogFileReader("src/test/resources/access.log");
        logFileReader.run();
        LogCounter.LOG_COUNTER.printResult("testOut.csv");
    }
}
