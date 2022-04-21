package utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

/**
 * class to hold parameters for webcrawler
 */
public class Args {
    public final URI url;
    public final Integer depth;
    public final Locale language;


    public Args(String url, Integer depth, String language) throws IllegalArgumentException, URISyntaxException {
        if(depth < 0) throw new IllegalArgumentException("depth must be greater or equal to 0.");
        this.url = new URI(url);
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
