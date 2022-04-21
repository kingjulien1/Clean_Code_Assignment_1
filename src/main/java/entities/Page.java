package entities;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.Args;

import java.io.IOException;
import java.net.URL;

/**
 * Represents a webpage with all headings & subpages
 */
public class Page {
    public final String url;
    private Heading[] headings;
    private Page[] subpages;

    public Page(String url) {
        this.url = url;
    }

    /**
     * gets all headings for this page, as well as for further subpages
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

    private String convertToMarkdown(int level) {
        StringBuilder stringBuilder = new StringBuilder();

        // print headings
        for (Heading heading : headings) stringBuilder.append(convertToMarkdownHeading(heading) + "\n");

        // print subpages
        for (Page subpage : subpages) {
            // print subpage heading
            if (subpage.isLinkBroken()) stringBuilder.append(prependBrTag("broken link" + wrapLinkInATag(subpage.url) + "\n"));
            else stringBuilder.append(prependBrTag("--> link to:" + wrapLinkInATag(subpage.url) + "\n"));

            // as long as we are not at the end of recursion, print subpages too
            if (level > 0 && !subpage.isLinkBroken()) stringBuilder.append(subpage.convertToMarkdown(level - 1));
        }
        return stringBuilder.toString();

    }

    public String convertToMarkdown(Args args, String filename) {
        StringBuilder stringBuilder = new StringBuilder();
        // prepend metadata
        stringBuilder.append("input: " + wrapLinkInATag(args.url) + "\n");
        stringBuilder.append(prependBrTag("depth: ") + args.depth + "\n");
        stringBuilder.append(prependBrTag("source language: ") + args.language + "\n");
        stringBuilder.append(prependBrTag("target language: ") + args.language + "\n");
        stringBuilder.append(prependBrTag("summary:" + "\n"));

        // add all contents of this and further subpages
        stringBuilder.append(convertToMarkdown(args.depth));
        return stringBuilder.toString();
    }

    private String wrapLinkInATag(String link) {
        return "<a>" + link + "</a>";
    }

    private String prependBrTag(String text) {
        return "<br>" + text;
    }

    private String convertToMarkdownHeading(Heading heading) {
        String[] markDownHeadingLevels = new String[]{"#", "##", "###", "####", "#####", "######"};
        int headingLevel = Character.getNumericValue(heading.tagName.charAt(1));
        return markDownHeadingLevels[headingLevel - 1] + " " + heading.value;
    }


}
