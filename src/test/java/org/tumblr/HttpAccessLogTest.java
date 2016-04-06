package org.tumblr;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by thor on 5/4/16.
 */
public class HttpAccessLogTest {

    @Test
    public void testLog1() {
        String sampleLog = "123.65.150.10 - - [23/Aug/2010:03:50:59 +0000] " +
                "\"POST /wordpress3/wp-admin/admin-ajax.php HTTP/1.1\" " +
                "200 2 \"http://www.example.com/wordpress3/wp-admin/post-new.php\" " +
                "\"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_4; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.25 Safari/534.3\"";

        try {
            HttpAccessLog r = HttpAccessLog.parseLogLine(sampleLog);
            Assert.assertEquals(r.getStatusCode(), new Integer(200));
            Assert.assertEquals(r.getDt(), "23/Aug/2010:03:50:59 +0000");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testError() {
        String sampleLog = "150.10 - - [23/Aug/2010:03:50:59 +0000] " +
                "\"POST /wordpress3/wp-admin/admin-ajax.php HTTP/1.1\" " +
                "200 2 \"http://www.example.com/wordpress3/wp-admin/post-new.php\" ";

        try {
            HttpAccessLog r = HttpAccessLog.parseLogLine(sampleLog);
        } catch (Exception e) {
            // e.printStackTrace();
            return;
        }
        Assert.fail();
    }
}
