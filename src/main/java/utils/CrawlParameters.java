package utils;

import entities.Page;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.MissingResourceException;

/**
 * class to hold parameters for webcrawler
 */
public class CrawlParameters {
    public final String url;
    public final Integer depth;
    public final Locale language;


    private CrawlParameters(String url, Integer depth, String language) throws IllegalArgumentException, MalformedURLException {
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

    /**
     * converts {String} args, checks them & creates a new {CrawlParameters} object
     *
     * @param args
     * @return
     * @throws IllegalArgumentException
     * @throws MalformedURLException
     */
    public static CrawlParameters convert(String[] args) throws IllegalArgumentException, MalformedURLException {

        if (args.length != 3) throw new IllegalArgumentException("not enough arguments.");

        // validate
        if (isLinkBroken(args[0])) throw new MalformedURLException("not a valid url.");
        if (!isPositiveNumeric(args[1]))
            throw new IllegalArgumentException("not a valid depth. depth must be numeric and greater or equal to zero.");
        if (!isValidLanguage(args[2]))
            throw new IllegalArgumentException("not a valid language. language must be a valid locale string.");

        return new CrawlParameters(args[0], Integer.parseInt(args[1]), args[2]);
    }

    public static boolean isPositiveNumeric(String value) {
        try {
            return Integer.parseInt(value) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidLanguage(String languageString) {
        try {
            Locale locale = new Locale(languageString);
            return locale.getISO3Language() != null && locale.getISO3Country() != null;
        } catch (MissingResourceException | NullPointerException e) {
            return false;
        }
    }

    public static boolean isLinkBroken(String link) {
        try {
            new URL(link);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

}
