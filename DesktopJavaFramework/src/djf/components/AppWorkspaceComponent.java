package djf.components;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author Dan Kim
 */
public abstract class AppWorkspaceComponent {
    protected Pane workspace;
    protected boolean workspaceActivated;

    public void activateWorkspace(BorderPane appPane) {
        if (!workspaceActivated) {
            // PUT THE WORKSPACE IN THE GUI
            appPane.setCenter(workspace);
            workspaceActivated = true;
        }
    }
    
    public void setWorkspace(Pane initWorkspace) { 
	workspace = initWorkspace; 
    }
    
    public Pane getWorkspace() { return workspace; }
    
    public abstract void resetWorkspace();
    
    public abstract void reloadWorkspace(AppDataComponent dataComponent);
}
