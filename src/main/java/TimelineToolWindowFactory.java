import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.ThrowableComputable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.psi.*;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class TimelineToolWindowFactory implements ToolWindowFactory {

    private static class NoMethodToVisualizeException extends Exception {
        NoMethodToVisualizeException(String message) {
            super(message);
        }
    }

    private static final String VISUALIZATION_DISPLAY_NAME = "Visualization";
    private JBPanel visualizationRootView;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

    }

    private void visualizeMostImportantMethod(Project project, ToolWindow toolWindow) {
        try {
            visualizeCallHierarchy(toolWindow, getMethodToVisualize(project));
        } catch (NoMethodToVisualizeException e) {
            e.printStackTrace();
            // ToDo: Inform user why there is nothing shown in the visualization
        }
    }

    private PsiMethod getMethodToVisualize(Project project) throws NoMethodToVisualizeException {
        PsiClass selectedClass = getSelectedClass(project);
        return selectMethodToDisplay(selectedClass.getMethods());
    }

    private PsiClass getSelectedClass(Project project) throws NoMethodToVisualizeException {
        VirtualFile selectedVirtualFile = getSelectedVirtualFile(project);
        return getClassOfVirtualFile(selectedVirtualFile, project);
    }

    private VirtualFile getSelectedVirtualFile(Project project) throws NoMethodToVisualizeException {
        FileEditor selectedEditor = FileEditorManager.getInstance(project).getSelectedEditor();
        if (selectedEditor != null && selectedEditor.getFile() != null) {
            return selectedEditor.getFile();
        } else {
            throw new NoMethodToVisualizeException("There is no selected editor tab or it has no content");
        }
    }

    private PsiClass getClassOfVirtualFile(@NotNull VirtualFile virtualFile, Project project) throws NoMethodToVisualizeException {
        PsiFile selectedPsiFile = PsiManager.getInstance(project).findFile(virtualFile);
        if (selectedPsiFile instanceof PsiJavaFile) {
            PsiClass[] classes = ((PsiJavaFile) selectedPsiFile).getClasses();
            return selectClassToStartVisualization(classes);
        } else {
            throw new NoMethodToVisualizeException("This plugin only supports visualization for Java Classes");
        }
    }

    private PsiClass selectClassToStartVisualization(PsiClass[] possibleClasses) throws NoMethodToVisualizeException {
        if (possibleClasses.length > 0) {
            return possibleClasses[0];
        } else {
            throw new NoMethodToVisualizeException("There are no classes in the selected Editor");
        }
    }

    private PsiMethod selectMethodToDisplay(PsiMethod[] classMethods) {
        // ToDo: Select suiting method
        return classMethods[0];
    }

    private void initContent(ToolWindow toolWindow) {
        visualizationRootView = new JBPanel(new GridBagLayout());
        JBScrollPane scrollPane = new JBScrollPane(visualizationRootView);
        replaceVisualizationContent(scrollPane, toolWindow.getContentManager());
        toolWindow.show(null);
    }

    private void replaceVisualizationContent(JComponent componentToAdd, ContentManager contentManager) {
        Content visualizationContent = contentManager.findContent(VISUALIZATION_DISPLAY_NAME);
        if (visualizationContent != null) {
            contentManager.removeContent(visualizationContent, true);
        }
        visualizationContent = contentManager.getFactory().createContent(componentToAdd, VISUALIZATION_DISPLAY_NAME, false);
        contentManager.addContent(visualizationContent);
    }

    void visualizeCallHierarchy(ToolWindow toolWindow, PsiMethod rootMethod) {
        initContent(toolWindow);
        ColorService.initColors(getAllMethodNamesOfClass(Objects.requireNonNull(rootMethod.getContainingClass())));
        CallGraph callGraph = ProgressManager.getInstance().computePrioritized(
                (ThrowableComputable<CallGraph, ProcessCanceledException>) () -> CallGraphGenerator.generateCallGraph((rootMethod))
        );
        callGraph.initVisualization(visualizationRootView);
    }

    private String[] getAllMethodNamesOfClass(PsiClass containingClass) {
        ArrayList<String> methodNames = new ArrayList<>();
        for (PsiMethod method : containingClass.getMethods()) {
            methodNames.add(method.getName());
        }
        return methodNames.toArray(new String[0]);
    }

}