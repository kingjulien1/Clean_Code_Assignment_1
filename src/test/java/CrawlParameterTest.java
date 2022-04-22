import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.CrawlParameters;

import java.net.MalformedURLException;

public class CrawlParameterTest {

    @Test
    void testUrl(){
        // null
        Assertions.assertTrue(CrawlParameters.isLinkBroken(null));
        // invalid string
        Assertions.assertTrue(CrawlParameters.isLinkBroken("invalid"));
        // no protocol, link should be broken
        Assertions.assertTrue(CrawlParameters.isLinkBroken("www.google.at"));
        // valid url
        Assertions.assertFalse(CrawlParameters.isLinkBroken("https://www.google.at"));
    }
    @Test
    void testDepth(){
        // null
        Assertions.assertFalse(CrawlParameters.isPositiveNumeric(null));
        // non - numeric string
        Assertions.assertFalse(CrawlParameters.isPositiveNumeric("s"));
        // negative numeric
        Assertions.assertFalse(CrawlParameters.isPositiveNumeric("-1"));
        // valid positive numeric
        Assertions.assertTrue(CrawlParameters.isPositiveNumeric("4"));
    }
    @Test
    void testLanguage(){
        // null
        Assertions.assertFalse(CrawlParameters.isValidLanguage(null));
        // invalid string
        Assertions.assertFalse(CrawlParameters.isValidLanguage("invalid"));
        // valid language locale
        Assertions.assertTrue(CrawlParameters.isValidLanguage("en"));
    }
    @Test
    void testConvert(){
        // only 1 argument passed
        Assertions.assertThrows(IllegalArgumentException.class, () -> CrawlParameters.convert(new String[]{"https://google.com"}));
        // only 2 argument passed
        Assertions.assertThrows(IllegalArgumentException.class, () -> CrawlParameters.convert(new String[]{"https://google.com","1"}));
        // invalid arguments
        Assertions.assertThrows(MalformedURLException.class, () -> CrawlParameters.convert(new String[]{"invalid","1","en"}));
        Assertions.assertThrows(IllegalArgumentException.class, () -> CrawlParameters.convert(new String[]{"https://google.com","a","en"}));
        // valid arguments
        Assertions.assertDoesNotThrow(() -> CrawlParameters.convert(new String[]{"https://google.com","1","en"}));
    }
}
