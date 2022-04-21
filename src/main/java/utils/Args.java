package utils;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;

/**
 * class to hold parameters for webcrawler
 */
public class Args {
    public final String url;
    public final Integer depth;
    public final Locale language;


    public Args(String url, Integer depth, String language) throws IllegalArgumentException,  MalformedURLException {
        // check for valid depth
        if(depth < 0) throw new IllegalArgumentException("depth must be greater or equal to 0.");
        // check for valid uri
        new URL(url);

        this.url = url;
        this.depth = depth;
        this.language = new Locale(language);
    }

    @Override
    public String toString() {
        return "Args{" +
                "url=" + url +
                ", depth=" + depth +
                ", language=" + language +
                '}';
    }

    public static String getUsage(){
        return """
               input format: 
                    <url> <depth> <language> 
                    ** note that url is mandatory, all other arguments are optional and set to defaults (depth = 0, language = en)
                """;
    }

}
