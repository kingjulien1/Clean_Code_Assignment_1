package entities;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

/**
 * Represents a webpage with all headings & links
 */
public class Page {
    public final String url;
    private Heading[] headings;
    private Page[] subpages;

    public Page(String url) {
        this.url = url;
    }

    /**
     * gets all headings for this page, as well as for #level-subpages
     *
     * @param level to which we want to crawl for further subpages
     * @throws IOException
     */
    public void crawl(int level) throws IOException {
        // crawl for this page
        Document doc = Jsoup.connect(url).get();

        headings = getHeadings(doc);
        subpages = getSubpages(doc);

        // if we have reached the last level of recursion -> stop recursion
        if (level <= 0) return;

        // crawl subpages
        for (Page subpage : subpages) {
            if (!subpage.isLinkBroken()) subpage.crawl(level - 1);
        }
    }

    /**
     * converts all heading tags (h1 - h6) from current page document to {Heading} objects
     *
     * @param doc current page doc representing html dom
     * @return {Heading} objects
     */
    private Heading[] getHeadings(Document doc) {
        Elements allHeadingTags = doc.select("h1, h2, h3, h4, h5, h6");

        Heading[] headings = new Heading[allHeadingTags.size()];
        int index = 0;
        for (Element headingTag : allHeadingTags)
            headings[index++] = new Heading(headingTag.html(), headingTag.tagName());

        return headings;
    }

    /**
     * converts all heading tags (h1 - h6) from current page document to {Heading} objects
     *
     * @param doc current page doc representing html dom
     * @return {Heading} objects
     */
    private Page[] getSubpages(Document doc) {
        Elements allLinkTags = doc.select("a");

        Page[] subpages = new Page[allLinkTags.size()];
        int index = 0;
        for (Element linkTag : allLinkTags)
            subpages[index++] = new Page(linkTag.attr("href"));

        return subpages;
    }

    /**
     * checks if link of this page is broken
     *
     * @return {boolean}
     */
    public boolean isLinkBroken() {
        try {
            new URL(url);
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
