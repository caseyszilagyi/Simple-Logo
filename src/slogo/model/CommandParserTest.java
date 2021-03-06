package slogo.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class CommandParserTest implements Parser{

    // where to find resources specifically for this class
    private static final String RESOURCES_PACKAGE = CommandParserTest.class.getPackageName()+".resources.commands.";
    // "types" and the regular expression patterns that recognize those types
    // note, it is a list because order matters (some patterns may be more generic)
    private Map<String, String> myParameters;


    public CommandParserTest(){
        myParameters = new HashMap<>();
        addPatterns("Commands");
    }

    @Override
    public void translateCommand(List<String> commandsBeforeTranslation) {

    }

    /**
     * Adds the given resource file to this language's recognized types
     */
    public void addPatterns (String syntax) {
        ResourceBundle resources = ResourceBundle.getBundle(RESOURCES_PACKAGE + syntax);
        for (String key : Collections.list(resources.getKeys())) {
            myParameters.put(key, resources.getString(key));
        }
    }


    public String parseText (List<String> lines) {
        String ret = "";
        for (String line : lines) {
            ret = ret+ getSymbol(line)+" ";
        }
        return ret;
    }

    /**
     * Returns language's type associated with the given text if one exists
     */
    public String getSymbol (String text) {
        final String ERROR = "NO MATCH";
        return myParameters.get(text);
    }


    public static void main(String[] args) {
        // NO static methods needed!


        // set up the parser, which checks for matches in order given
        CommandParserTest test = new CommandParserTest();
        // these are more specific, so add them first to ensure they are checked first
//        test.addPatterns("English");
        // general checks, added last
        test.addPatterns("Commands");

        // try against different kinds of inputs
//        m.parseText(lang, m.examples);
//        String userInput = "fd 50 rt 90 BACK :distance Left :angle";
        String userInput = "Forward";
//        // note, this simple "algorithm" will not handle SLogo comments
        test.parseText(Arrays.asList(userInput.split(" ")));
//        String fileInput = m.readFileToString(Main.class.getClassLoader().getResource("square.logo").toExternalForm());
        // instead it will "comment out" the remainder of the program!
//        m.parseText(lang, Arrays.asList(fileInput.split(WHITESPACE)));
    }
}
