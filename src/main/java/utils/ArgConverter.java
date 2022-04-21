package utils;


import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class ArgConverter {

    /**
     * command line input format: <url> <depth> <language>
     *
     * @param args command line args passed to the
     * **mandatory** @param {String} args[0] url of website to crawl, is argument
     * **optional** @param {Integer} args[1] depth of iteration - defaults to 0
     * **optional** @param {String} args[2] language to which we want to translate - defaults to `en`
     * @return formatted arguments
     */
    public static Args convert(String[] args) throws IllegalArgumentException, MalformedURLException {
        String url;
        Integer depth;
        String language;

        try{
            url = args[0];
        }catch(Exception e) {
            throw new IllegalArgumentException("no url provided");
        }
        try{
            depth = Integer.parseInt(args[1]);
        }catch(Exception e) {
            depth = 0;
        }
        try{
            language = args[2];
        }catch(Exception e) {
            language = "en";
        }

        return new Args(url, depth, language);
    }

}
