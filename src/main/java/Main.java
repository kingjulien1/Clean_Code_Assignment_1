import entities.Page;
import utils.ArgConverter;
import utils.Args;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(Args.getUsage());
            Args params = ArgConverter.convert(args);

            //call crawler
            Page rootPage = new Page(params.url);
            rootPage.crawl(params.depth);
            System.out.println(rootPage.convertToMarkdown(params,""));


        } catch ( IOException e) {
            e.printStackTrace();
        }
    }
}
