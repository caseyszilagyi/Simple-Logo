package slogo.model;

import java.util.*;
import slogo.controller.ModelController;
import slogo.model.tree.TreeNode;

/**
 * translates the string of all commands into a non-binary tree
 */
public class CommandParser implements Parser {

    // where to find resources specifically for this class
    private static final String RESOURCES_PACKAGE = CommandParserTest.class.getPackageName()+".resources.commands.";
    private static final String LANGUAGES_PACKAGE = CommandParserTest.class.getPackageName()+".resources.languages.";

    // "types" and the regular expression patterns that recognize those types
    // note, it is a list because order matters (some patterns may be more generic)
    private Map<String, String> parameters;
    private TreeNode commandTree;
    private String userInput;
    private ModelController modelController;


    public CommandParser(String userInput, ModelController modelController){
        this.modelController = modelController;
        parameters = new HashMap<>();
        addPatterns("Commands");
        commandTree = new TreeNode(null);
        this.userInput = userInput;

        System.out.println("Parameter Taken in by the parser" + userInput);
        commandParamCount(Arrays.asList(userInput.split(" ")));


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
            parameters.put(key, resources.getString(key));
        }
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
        int numParam = getSymbol(childVal);
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
     * gets the respective parameter counts for the command specified
     * @param lines
     * @return
     */
    public String commandParamCount(List<String> lines) {
        String ret = "";
        for (String line : lines) {
            ret = ret+ getSymbol(line)+" ";
        }
        return ret;
    }

    /**
     * Returns respective parameter counts for the command specified
     * @param text String representation of the command
     * @return String rep of the number of params needed for command
     */
    public Integer getSymbol (String text) {
        final String ERROR = "NO MATCH";
        return Integer.parseInt(parameters.get(text));
    }
}