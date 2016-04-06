package org.tumblr;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by thor on 5/4/16.
 */
public class HttpAccessLog {
    // http://httpd.apache.org/docs/2.4/logs.html
    // LogFormat "%h %l %u %t \"%r\" %>s %b \"%{Referer}i\" \"%{User-agent}i\"" combined
    // ipaddress, clientIdentd, user, dt, "method url protocol", statusCode, size, referrer, user-agent
    private String dt;
    private Integer statusCode;

    private HttpAccessLog(String dt, String statusCode) {
        this.dt = dt;
        this.statusCode = Integer.parseInt(statusCode);
    }

    public String getDt() {
        return dt;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    // ^\S+ \S+ \S+ \[([^\]]+)\] "[A-Z]+[^"]*" (\d+) \d+ "[^"]*" "[^"]*"$
    // ipaddress, clientIdentd, user, dt, "method url protocol", statusCode, size, referrer, user-agent

    private static final String LOG_ENTRY_PATTERN =
            "^\\S+ \\S+ \\S+ \\[([^\\]]+)\\] \"[A-Z]+[^\"]*\" (\\d+) \\d+ \"[^\"]*\" \"[^\"]*\"$";
    private static final Pattern LOG_PATTERN = Pattern.compile(LOG_ENTRY_PATTERN);
    public static HttpAccessLog parseLogLine(String line) throws Exception{
        Matcher m = LOG_PATTERN.matcher(line);

        if (!m.find()) {
            throw new Exception("Error parsing log line");
        }

        return new HttpAccessLog(m.group(1), m.group(2));
    }

    @Override
    public String toString() {
        return String.format("%s %s", dt, statusCode);
    }
}
