package slogo.model;

//import java.util.*;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import slogo.controller.ModelController;
import slogo.model.tree.TreeNode;

/**
 * translates the string of all commands into a non-binary tree
 */
public class CommandParser implements Parser {

    // where to find resources specifically for this class
    private static final String RESOURCES_PACKAGE = CommandParserTest.class.getPackageName()+".resources.commands.";
    private static final String LANGUAGES_PACKAGE = CommandParserTest.class.getPackageName()+".resources.languages.";
    private static final List<String> ALL_LANGUAGES = new ArrayList<>(Arrays.asList("English", "Chinese", "French", "German",
                                                                                    "Italian","Portuguese", "Russian", "Spanish", "Urdu"));
    public static final String WHITESPACE = "\\s+";

    // "types" and the regular expression patterns that recognize those types
    // note, it is a list because order matters (some patterns may be more generic)
    private Map<String, String> parameters;
    private List<Entry<String, Pattern>> symbols;
    private TreeNode commandTree;
    private String userInput;
    private ModelController modelController;


    public CommandParser(String userInput, ModelController modelController){
        this.modelController = modelController;
        parameters = new HashMap<>();
        symbols = new ArrayList<>();
        for(String language : ALL_LANGUAGES) {
            addLangPatterns(language);
        }
        addParamCounts("Commands");
        commandTree = new TreeNode(null);
        this.userInput = userInput;

        System.out.println("Parameter Taken in by the parser" + userInput);


    }

    @Override
    public List<String> translateCommand(List<String> commandsBeforeTranslation) {
        List<String> translated = new ArrayList<>();
        for(String s : commandsBeforeTranslation){
            if(getCommandKey(s) == "NO MATCH"){
                translated.add(s);
            }
            else{
                translated.add(getCommandKey(s));
            }
        }
        return translated;
    }

    /**
     * Adds the given resource file to this language's recognized types
     */
    public void addParamCounts(String syntax) {
        ResourceBundle resources = ResourceBundle.getBundle(RESOURCES_PACKAGE + syntax);
        for (String key : Collections.list(resources.getKeys())) {
            parameters.put(key, resources.getString(key));
        }
    }

    /**
     * Adds the given resource file to this language's recognized types
     */
    public void addLangPatterns(String syntax) {
        ResourceBundle resources = ResourceBundle.getBundle(LANGUAGES_PACKAGE + syntax);
        for (String key : Collections.list(resources.getKeys())) {
            String regex = resources.getString(key);
            symbols.add(new SimpleEntry<>(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
        }
    }

    /**
     * makes the tree at the tree root node commandTree
     * @return
     */
    public TreeNode makeTree(){
        List<String> splitCommands = Arrays.asList(userInput.split(WHITESPACE));
        insertNodeRecursive(splitCommands, commandTree);
        return commandTree;
    }

//    public void makeTree(String allCommands){
//        List<String> splitCommands = Arrays.asList(allCommands.split(" "));
//        for(int n=0; n<splitCommands.size(); n++){
//            String currCommand = splitCommands.get(n);
//            TreeNode command = new TreeNode(currCommand, new ArrayList<>());
//            myCommandTree.addChild(command);
//            myCommandTree = myCommandTree.getChildren().get(0);
//            int numParam = Integer.parseInt(getSymbol(currCommand));
//            for(int p=1; p<=numParam; p++){
//                String child = splitCommands.get(n+p);
//                TreeNode childNode = new TreeNode(child, new ArrayList<>());
//                myCommandTree.addChild(command);
//            }
//        }
//    }

    //only call this if you are a command (check this and base case is if you're not a command)
    private TreeNode insertNodeRecursive(List<String> splitCommands, TreeNode root) {
        String childVal = splitCommands.get(0);
        TreeNode child = new TreeNode(childVal, new ArrayList<>());
        root.addChild(child);
        if(splitCommands.size() <= 1 || !isCommand(childVal)){
            //send exception if it's a command saying the last one is a command but shouldnt be
            return root;
        }
        //if it is, then you get the number of params
        int numParam = getParamCount(childVal);
        for(int i=1; i<=numParam; i++){
            //recursively insertNodeRecursive with the shorter splitCommands for each subsequent
            // new child and yourself as the root
            return insertNodeRecursive(splitCommands.subList(i, splitCommands.size()), child);
        }
        return root; //return the recursive call?
    }

    private boolean isCommand(String s){
        return parameters.containsKey(s);
    }

    /**
     * Returns respective parameter counts for the command specified
     * @param text String representation of the command
     * @return String rep of the number of params needed for command
     */
    public Integer getParamCount(String text) {
        final String ERROR = "NO MATCH";
        return Integer.parseInt(parameters.get(text));
    }

    /**
     * Returns key Command associated with the given text if one exists
     */
    public String getCommandKey (String text) {
        final String ERROR = "NO MATCH";
        for (Entry<String, Pattern> e : symbols) {
            if (match(text, e.getValue())) {
                return e.getKey();
            }
        }
        // FIXME: perhaps throw an exception instead
        return ERROR;
    }

    // Returns true if the given text matches the given regular expression pattern
    private boolean match (String text, Pattern regex) {
        return regex.matcher(text).matches();
    }

}