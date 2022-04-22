import entities.Heading;
import entities.Page;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PageTest {

    @Test
    void testMarkdownWrappers() {
        String value = "value";
        // test link-wrapper
        Assertions.assertEquals("<a></a>", Page.wrapInATag(""));
        Assertions.assertEquals("<a>" + value + "</a>", Page.wrapInATag(value));

        // test br - append
        Assertions.assertEquals("<br>", Page.prependBrTag(""));
        Assertions.assertEquals("<br>" + value, Page.prependBrTag(value));
    }

    @Test
    void testMarkdownHeadingConversion() {
        String headingValue = "heading";
        // test valid conversion
        for (int i = 1; i <= 6; i++) {
            Heading heading = new Heading(headingValue, "h" + i);
            Assertions.assertEquals("#".repeat(i) + " " + headingValue, Page.convertToMarkdownHeading(heading));
        }
    }

    @Test
    void testGetHeadingsFromDocument() {
        Document doc = new Document(null);
        doc.append("<h1>test</h1>");

        Heading[] headings = Page.getHeadings(doc);
        // should get 1 heading from this document
        Assertions.assertEquals(1, headings.length);
        Assertions.assertEquals("test", headings[0].value);
        Assertions.assertEquals("h1", headings[0].tagName);
    }

    @Test
    void testGetSubPagesFromDocument() {
        Document doc = new Document(null);
        doc.append("<a href='invalid'>test</a>");
        doc.append("<a href='https://google.at'>test</a>");

        Page[] headings = Page.getSubpages(doc);
        // should get 2 subpages from this document
        Assertions.assertEquals(2, headings.length);
        // link 1 should be broken, link 2 should be valid
        Assertions.assertTrue(headings[0].isLinkBroken());
        Assertions.assertFalse(headings[1].isLinkBroken());
    }

    @Test
    void testConvertToMarkdown(){

    }
}
