package org.mozilla.magnet.net.scanner;

import android.content.Context;

import junit.framework.TestCase;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mozilla.magnet.net.scanner.btle.BTLEScanner;
import org.mozilla.magnet.net.scanner.mdns.MDNSScanner;

import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class ScannerTest extends TestCase {

    @Mock private Map<String, BaseScanner> scannersList;
    @InjectMocks private MagnetScanner magnetScanner;
    @Mock private Context context;
    @Mock private BTLEScanner btleScanner;
    @Mock private MDNSScanner mdnsScanner;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        magnetScanner.useBTLE(btleScanner).usemDNS(mdnsScanner);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void start() {
        MagnetScannerCallback cb = new MagnetScannerCallback() {
            @Override
            public void onItemFound(JSONObject obj) {

            }
        };
        magnetScanner.start(cb);

        verify(btleScanner).start(cb);
        verify(mdnsScanner).start(cb);

        verify(scannersList, calls(2));
    }

    @Test
    public void stop() {
        magnetScanner.stop();

        verify(btleScanner).stop();
        verify(mdnsScanner).stop();
    }

}
