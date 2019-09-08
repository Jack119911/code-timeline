package resources;

public class TutorialText {

    private TutorialText() {}

    public static final String INTRO = "This windows shows the visualization of the Call-Hierachy. You switch between this tutorial and the actual visualization.";
    public static final String BLOCKS = "Each colored block you can see represents a call of the named method. Blocks which refer to the same method share the same color. The positioning of the blocks indicates their relation.";
    public static final String CONTROL_FLOW = "There is one method call (generateBody) at the top which is the one we are analyzing right now. The control flow starts in the top left corner and ends in the top right corner. All methods called by generateBody are listed beneath it. Their order from left to right corresponds to their execution. And their method calls are listed below them, and so on. ";
    public static final String DEPENDENCY = "To be clear: This only shows the dependencies between methods and in what order they are generally executed. The specific execution of the code when running the program differs from this visualization. It is changed by your variables, user input and other external factors. But the visualization gives you some hints.";
    public static final String CONTROL_STRUCTURES = "There are mainly two possibilities to change the flow of your program. First, a block of code can be repeated and second, a block of code can only be executed if a certain condition is true. So of course this changes whether methods are called or not and how often they are called. And is therefore important to show in the visualization. The question mark and the repeat symbol are marking method calls which are either executed multiple times or it is unsure if they are executed at all. For further information when and why they are executed, you have to look at their parents body.";
    public static final String NAVIGATION = "For a quick look at the code of a method, you can hover over its name in the visualization. Clicking anywhere on a method block lets your editor jump to the corresponding location in the code.";
    public static final String ROOT_METHOD = "You can change the root method of the visualization. Just right click on a method name in your editor to open the context menu  and select the topmost entry.";

}
