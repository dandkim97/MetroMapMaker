package djf;

import djf.ui.*;
import djf.components.*;
import properties_manager.PropertiesManager;
import properties_manager.InvalidXMLFileFormatException;
import static djf.settings.AppPropertyType.*;
import static djf.settings.AppStartupConstants.*;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Dan Kim
 */
public abstract class AppTemplate extends Application {
    
    protected AppGUI gui;
    protected AppDataComponent dataComponent;
    protected AppFileComponent fileComponent;
    protected AppWorkspaceComponent workspaceComponent;
    
    public abstract void buildAppComponentsHook();
    
    public AppDataComponent getDataComponent() { return dataComponent; }
    public AppFileComponent getFileComponent() { return fileComponent; }
    public AppWorkspaceComponent getWorkspaceComponent() { return workspaceComponent; }
    public AppGUI getGUI() { return gui; }
    
    @Override
    public void start(Stage primaryStage) {
        
        AppMessageDialogSingleton messageDialog = AppMessageDialogSingleton.getSingleton();
	messageDialog.init(primaryStage);
	AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
	yesNoDialog.init(primaryStage);
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        try{
            boolean success = false;
            success = loadProperties(APP_PROPERTIES_FILE_NAME);
            if(success){
                String appTitle = props.getProperty(APP_TITLE);
                
                gui = new AppGUI(primaryStage, appTitle, this);
                
                buildAppComponentsHook();
                
                primaryStage.show();
            }
            
        }catch(Exception e){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(PROPERTIES_LOAD_ERROR_TITLE), props.getProperty(PROPERTIES_LOAD_ERROR_MESSAGE));
        }
        
        
    }

    public boolean loadProperties(String propertiesFileName) {
	    PropertiesManager props = PropertiesManager.getPropertiesManager();
	try {
	    // LOAD THE SETTINGS FOR STARTING THE APP
	    props.addProperty(PropertiesManager.DATA_PATH_PROPERTY, PATH_DATA);
	    props.loadProperties(propertiesFileName, PROPERTIES_SCHEMA_FILE_NAME);
	    return true;
	} catch (InvalidXMLFileFormatException ixmlffe) {
	    // SOMETHING WENT WRONG INITIALIZING THE XML FILE
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(PROPERTIES_LOAD_ERROR_TITLE), props.getProperty(PROPERTIES_LOAD_ERROR_MESSAGE));
	    return false;
	}
    }
    
}
