package slogo.model;

import java.util.List;
import java.util.regex.Pattern;

public class CommandParserTest implements Parser{

    // where to find resources specifically for this class
    private static final String RESOURCES_PACKAGE = ProgramParser.class.getPackageName() + ".resources.languages.";
    // "types" and the regular expression patterns that recognize those types
    // note, it is a list because order matters (some patterns may be more generic)
    private List<Entry<String, Pattern>> mySymbols;



    @Override
    public void translateCommand(List<String> commandsBeforeTranslation) {

    }

    // Returns true if the given text matches the given regular expression pattern
    private boolean match (String text, Pattern regex) {
        // THIS IS THE OTHER IMPORTANT LINE
        return regex.matcher(text).matches();
    }

}
