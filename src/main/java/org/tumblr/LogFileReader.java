package org.tumblr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by thor on 5/4/16.
 */
public class LogFileReader implements Runnable {

    private final String filename;

    LogFileReader(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        File file = new File(filename);

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                // process the line.
                try {
                    HttpAccessLog r = HttpAccessLog.parseLogLine(line);
                    SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z");
                    inputDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    Date date = inputDateFormat.parse(r.getDt());
                    SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
                    outputDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    Long dt = Long.parseLong(outputDateFormat.format(date));
                    LogCounter.LOG_COUNTER.increment(dt, r.getStatusCode());
                } catch (Exception e) {
                    // System.err.println(line);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
