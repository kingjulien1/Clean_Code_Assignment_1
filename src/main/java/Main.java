import entities.Page;
import utils.CrawlParameters;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            CrawlParameters params = CrawlParameters.convert(args);

            //call crawler
            Page rootPage = new Page(params.url);
            rootPage.crawl(params.depth);
            System.out.println(rootPage.convertToMarkdown(params,""));

        } catch ( IOException e) {
            e.printStackTrace();
        }
    }
}
