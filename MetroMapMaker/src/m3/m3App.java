package m3;

import djf.AppTemplate;
import java.util.Locale;
import static javafx.application.Application.launch;
import m3.gui.m3Workspace;

/**
 *
 * @author Dan Kim
 */
public class m3App extends AppTemplate {
    
    @Override
    public void buildAppComponentsHook() {
        // CONSTRUCT ALL THREE COMPONENTS. NOTE THAT FOR THIS APP
        // THE WORKSPACE NEEDS THE DATA COMPONENT TO EXIST ALREADY
        // WHEN IT IS CONSTRUCTED, AND THE DATA COMPONENT NEEDS THE
        // FILE COMPONENT SO WE MUST BE CAREFUL OF THE ORDER
//        fileComponent = new m3Files();
//        dataComponent = new m3Data(this);
        workspaceComponent = new m3Workspace(this);
        workspaceComponent.reloadWorkspace(dataComponent);
    }
    
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }
    
}
