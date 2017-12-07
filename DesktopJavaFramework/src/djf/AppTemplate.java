package djf;

import djf.ui.*;
import djf.components.*;
import properties_manager.PropertiesManager;
import properties_manager.InvalidXMLFileFormatException;
import static djf.settings.AppPropertyType.*;
import static djf.settings.AppStartupConstants.*;
import java.util.Optional;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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
    public abstract void buildAppComponentsHook2();
    
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
        WelcomeDialogSingleton welcomeDialog = WelcomeDialogSingleton.getSingleton();
        welcomeDialog.init(primaryStage);
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        try{
            boolean success = false;
            success = loadProperties(APP_PROPERTIES_FILE_NAME);
            if(success){
//                WelcomeDialogSingleton dialog = WelcomeDialogSingleton.getSingleton();
                
                String appTitle = props.getProperty(APP_TITLE);
                
                gui = new AppGUI(primaryStage, appTitle, this);
                
                welcomeDialog.show("Welcome to the Metro Map Maker", "");
                if(welcomeDialog.getTruth()){
                    buildAppComponentsHook();
                }
                else{
                    buildAppComponentsHook2();
                }
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
