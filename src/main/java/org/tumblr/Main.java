package org.tumblr;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by thor on 5/4/16.
 */
public class Main {
    private static final int MAX_THREADS = 10;
    public static ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
    private static final String OUTPUT_FILENAME = "output.csv";

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new RuntimeException("Pass the logs directory");
        }

        File dir = FileUtils.getFile(args[0]);
        if (!dir.isDirectory())
            throw new RuntimeException("Argument is not directory");

        final String[] extensions = new String[] {"log"};
        List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, false);

        List<Future> futures = new ArrayList<>();
        for (File file: files) {
            futures.add(executor.submit(new LogFileReader(file.getAbsolutePath())));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            LogCounter.LOG_COUNTER.printResult(OUTPUT_FILENAME);
        } catch (InterruptedException e) {

        }
    }
}
