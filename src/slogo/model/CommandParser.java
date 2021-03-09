package slogo.model;

//import java.util.*;
import com.sun.source.tree.Tree;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import slogo.controller.ModelController;
import slogo.model.tree.TreeNode;

/**
 * translates the string of all commands into a non-binary tree
 * @author Jin Cho
 * @author Ji Yun Hyo
 */
public class CommandParser implements Parser {

    // where to find resources specifically for this class
    private static final String RESOURCES_PACKAGE = CommandParser.class.getPackageName()+".resources.commands.";
    private static final String LANGUAGES_PACKAGE = CommandParser.class.getPackageName()+".resources.languages.";
    public static final String WHITESPACE = "\\s+";

    // "types" and the regular expression patterns that recognize those types
    // note, it is a list because order matters (some patterns may be more generic)
    private Map<String, String> parameters;
    private TreeNode commandTree;
    private List<String> cleanCommands;
    private ModelController modelController;
    private InputCleaner inputCleaner;


    public CommandParser(String rawInput, ModelController modelController){
        this.modelController = modelController;
        parameters = new HashMap<>();
        inputCleaner = new InputCleaner(rawInput, modelController, this);
        cleanCommands = inputCleaner.cleanString();
        addParamCounts("Commands");
        commandTree = new TreeNode(null);
        System.out.println("Command Taken in by the parser: " + rawInput);
        System.out.println("Clean command: "+cleanCommands);
        TreeNode root = makeTree();
        printPreOrder(root);
    }

    /**
     * Adds the given resource file to this language's recognized types
     */
    public void addParamCounts(String syntax) {
        ResourceBundle resources = ResourceBundle.getBundle(RESOURCES_PACKAGE + syntax);
        for (String key : Collections.list(resources.getKeys())) {
            addSingleParamCount(key, resources.getString(key));
            parameters.put(key, resources.getString(key));
            System.out.println("Key: " + key);
            System.out.println("Number: " + resources.getString(key));
            System.out.println();
        }
    }

    public void addSingleParamCount(String command, String paramCount){
        parameters.put(command, paramCount);
    }
    /**
     * makes the tree at the tree root node commandTree
     * @return
     */
    public TreeNode makeTree() {
        Deque<String> commandQueue = new LinkedList<>(cleanCommands);
        System.out.println("QUEUE: " + commandQueue);

        while (!commandQueue.isEmpty()) {
            String command = commandQueue.removeFirst();
            TreeNode child = new TreeNode(command);
            child = checkCommandBlock(child);
            commandTree.addChild(child);
            insertNodeRecursive(commandQueue, child);
        }
        return commandTree;
    }
    private void printPreOrder(TreeNode root) {
        if (root == null) {
            return;
        }

        System.out.println("Value: " + root.getCommand());
        for(TreeNode child : root.getChildren()){
            printPreOrder(child);
        }
    }

    private TreeNode insertNodeRecursive(Deque<String> splitCommands, TreeNode root) {
        if (getParamCount(root.getValue()) == 0) {
            System.out.println(root.getValue() + " is a leaf");
        }


        System.out.println();
        int paramCount = getParamCount(root.getValue());
        System.out.println(paramCount);
        for(int i = 0; i < getParamCount(root.getValue()); i ++){
            String command = splitCommands.removeFirst();
            TreeNode dummy = new TreeNode(command);
            dummy = checkCommandBlock(dummy);
            root.addChild(dummy);
            System.out.println("Parent: " + root.getCommand());
            System.out.println("Child: " + dummy.getCommand());
            insertNodeRecursive(splitCommands, dummy);
        }

        System.out.println();
        return root;
    }

    /**
     * Returns respective parameter counts for the command specified
     * @param text String representation of the command
     * @return String rep of the number of params needed for command
     */
    public Integer getParamCount(String text) {
        final String ERROR = "NO MATCH";
        try{
            return Integer.parseInt(parameters.get(text));
        }catch (Exception e){
            return 0;
        }
    }

    private TreeNode checkCommandBlock(TreeNode node){
        if(node.getCommand().contains("CommandBlock")) {
            node = new TreeNode(node.getCommand(), "CommandBlock");
        }
        return node;
    }

    @Override
    public List<String> translateCommand(List<String> commandsBeforeTranslation) {
        return null;
    }
}