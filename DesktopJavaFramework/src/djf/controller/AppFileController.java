package djf.controller;

import djf.AppTemplate;
import djf.components.AppDataComponent;
import java.io.File;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import static djf.settings.AppPropertyType.*;
import static djf.settings.AppStartupConstants.*;
import djf.ui.AppGUI;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import javafx.scene.control.TextInputDialog;
import jtps.JTPS;
import properties_manager.PropertiesManager;

/**
 *
 * @author Dan Kim
 */
public class AppFileController {
    
    AppTemplate app;
    
    boolean saved;
    
    File currentWorkFile;
    
    public AppFileController(AppTemplate initApp) {
        // NOTHING YET
        saved = true;
        app = initApp;
    }
    
    public void markAsEdited(AppGUI gui) {
        // THE WORK IS NOW DIRTY
        saved = false;
        
        // LET THE UI KNOW
        gui.updateToolbarControls(saved);
    }
    
    public void handleNewRequest() {
	AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToMakeNew = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE WITH A CANCEL
                continueToMakeNew = promptToSave();
            }

            // IF THE USER REALLY WANTS TO MAKE A NEW COURSE
            if (continueToMakeNew) {
                // RESET THE WORKSPACE
		app.getWorkspaceComponent().resetWorkspace();

                // RESET THE DATA
                app.getDataComponent().resetData();
                
                // NOW RELOAD THE WORKSPACE WITH THE RESET DATA
                app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());

		// MAKE SURE THE WORKSPACE IS ACTIVATED
		app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());
		
		// WORK IS NOT SAVED
                saved = false;
		currentWorkFile = null;

                // REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
                // THE APPROPRIATE CONTROLS
                app.getGUI().updateToolbarControls(saved);

                // TELL THE USER NEW WORK IS UNDERWAY
		dialog.show(props.getProperty(NEW_COMPLETED_TITLE), props.getProperty(NEW_COMPLETED_MESSAGE));
            }
        } catch (IOException ioe) {
            // SOMETHING WENT WRONG, PROVIDE FEEDBACK
	    dialog.show(props.getProperty(NEW_ERROR_TITLE), props.getProperty(NEW_ERROR_MESSAGE));
        }
    }
    
    public void handleSaveRequest() {
	// WE'LL NEED THIS TO GET CUSTOM STUFF
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        try {
	    // MAYBE WE ALREADY KNOW THE FILE
	    if (currentWorkFile != null) {
		saveWork(currentWorkFile);
	    }
	    // OTHERWISE WE NEED TO PROMPT THE USER
	    else {
		// PROMPT THE USER FOR A FILE NAME
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(PATH_WORK));
		fc.setTitle(props.getProperty(SAVE_WORK_TITLE));
		fc.getExtensionFilters().addAll(
		new FileChooser.ExtensionFilter(props.getProperty(WORK_FILE_EXT_DESC), props.getProperty(WORK_FILE_EXT)));

		File selectedFile = fc.showSaveDialog(app.getGUI().getWindow());
		if (selectedFile != null) {
		    saveWork(selectedFile);
		}
	    }
        } catch (IOException ioe) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
        }
    }
    
    // HELPER METHOD FOR SAVING WORK
    private void saveWork(File selectedFile) throws IOException {
	// SAVE IT TO A FILE
	app.getFileComponent().saveData(app.getDataComponent(), selectedFile.getPath());
	
	// MARK IT AS SAVED
	currentWorkFile = selectedFile;
	saved = true;
	
	// TELL THE USER THE FILE HAS BEEN SAVED
	AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        dialog.show(props.getProperty(SAVE_COMPLETED_TITLE),props.getProperty(SAVE_COMPLETED_MESSAGE));
		    
	// AND REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
	// THE APPROPRIATE CONTROLS
	app.getGUI().updateToolbarControls(saved);	
    }
    
    public void handleLoadRequest() {
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToOpen = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE WITH A CANCEL
                continueToOpen = promptToSave();
            }

            // IF THE USER REALLY WANTS TO OPEN A Course
            if (continueToOpen) {
                // GO AHEAD AND PROCEED LOADING A Course
                promptToOpen();
            }
        } catch (IOException ioe) {
            // SOMETHING WENT WRONG
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    PropertiesManager props = PropertiesManager.getPropertiesManager();
	    dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
        }
    }
    
    public void handleExitRequest() {
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToExit = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE
                continueToExit = promptToSave();
            }

            // IF THE USER REALLY WANTS TO EXIT THE APP
            if (continueToExit) {
                // EXIT THE APPLICATION
                System.exit(0);
            }
        } catch (IOException ioe) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
		PropertiesManager props = PropertiesManager.getPropertiesManager();
                dialog.show(props.getProperty(SAVE_ERROR_TITLE), props.getProperty(SAVE_ERROR_MESSAGE));
        }
    }
    
    private boolean promptToSave() throws IOException {
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	
	// CHECK TO SEE IF THE CURRENT WORK HAS
	// BEEN SAVED AT LEAST ONCE
	
        // PROMPT THE USER TO SAVE UNSAVED WORK
	AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
        yesNoDialog.show(props.getProperty(SAVE_UNSAVED_WORK_TITLE), props.getProperty(SAVE_UNSAVED_WORK_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(AppYesNoCancelDialogSingleton.YES)) {
            // SAVE THE DATA FILE
            AppDataComponent dataManager = app.getDataComponent();
	    
	    if (currentWorkFile == null) {
		// PROMPT THE USER FOR A FILE NAME
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(PATH_WORK));
		fc.setTitle(props.getProperty(SAVE_WORK_TITLE));
		fc.getExtensionFilters().addAll(
		new FileChooser.ExtensionFilter(props.getProperty(WORK_FILE_EXT_DESC), props.getProperty(WORK_FILE_EXT)));

		File selectedFile = fc.showSaveDialog(app.getGUI().getWindow());
		if (selectedFile != null) {
		    saveWork(selectedFile);
		    saved = true;
		}
	    }
	    else {
		saveWork(currentWorkFile);
		saved = true;
	    }
        } // IF THE USER SAID CANCEL, THEN WE'LL TELL WHOEVER
        // CALLED THIS THAT THE USER IS NOT INTERESTED ANYMORE
        else if (selection.equals(AppYesNoCancelDialogSingleton.CANCEL)) {
            return false;
        }

        // IF THE USER SAID NO, WE JUST GO ON WITHOUT SAVING
        // BUT FOR BOTH YES AND NO WE DO WHATEVER THE USER
        // HAD IN MIND IN THE FIRST PLACE
        return true;
    }
    
    private void promptToOpen() {
	// WE'LL NEED TO GET CUSTOMIZED STUFF WITH THIS
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	
        // AND NOW ASK THE USER FOR THE FILE TO OPEN
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(PATH_WORK));
	fc.setTitle(props.getProperty(LOAD_WORK_TITLE));
        File selectedFile = fc.showOpenDialog(app.getGUI().getWindow());

        // ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (selectedFile != null) {
            try {
                // RESET THE WORKSPACE
		app.getWorkspaceComponent().resetWorkspace();

                // RESET THE DATA
                app.getDataComponent().resetData();
                
                // LOAD THE FILE INTO THE DATA
                app.getFileComponent().loadData(app.getDataComponent(), selectedFile.getAbsolutePath());
                
		// MAKE SURE THE WORKSPACE IS ACTIVATED
		app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());
                
                // AND MAKE SURE THE FILE BUTTONS ARE PROPERLY ENABLED
                saved = true;
                app.getGUI().updateToolbarControls(saved);
            } catch (Exception e) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
            }
        }
    }
    
    public String promptToOpenPic() {
        // WE'LL NEED TO GET CUSTOMIZED STUFF WITH THIS
        PropertiesManager props = PropertiesManager.getPropertiesManager();
	
        // AND NOW ASK THE USER FOR THE FILE TO OPEN
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(PATH_WORK));
	fc.setTitle(props.getProperty(LOAD_WORK_TITLE));
        File selectedFile = fc.showOpenDialog(app.getGUI().getWindow());

        // ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (selectedFile != null) {
            try {
		app.getWorkspaceComponent().resetWorkspace();
                app.getDataComponent().resetData();
                app.getFileComponent().loadData(app.getDataComponent(), selectedFile.getAbsolutePath());              
		app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());               
                
            } catch (Exception e) {}
            try{
                String str = selectedFile.getAbsoluteFile().toURI().toURL().toString();
                return str;
            } catch (Exception e) {}
            
        }
        return null;
    }
    
    public void markFileAsNotSaved() {
        saved = false;
    }
        
    public boolean isSaved() {
        return saved;
    }
    
    public void handleAboutRequest(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About Dialog");
            alert.setHeaderText("About this Application");
            alert.setContentText("Name of App: MetroMapMaker\n"
                               + "Developer: Dan Kim\n" 
                               + "Contributor(s): Richard McKenna, Ritwik Banerjee");
            alert.showAndWait();
    }

    public void handleExportRequest() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Export Dialog");
                alert.setHeaderText("Exported Map");
                alert.setContentText("Map has been exported.");
                alert.showAndWait();
    }
    
    public void handleWelcomeRequest(){
        TextInputDialog dialog = new TextInputDialog("");
                dialog.setTitle("Text Input Dialog");
                dialog.setHeaderText("New Map");
                dialog.setContentText("Please enter the name:");
                dialog.showAndWait();
        
    }
    
    public void handleUndoRequest(){
        JTPS jtps = app.getJTPS();
        jtps.undoTransaction();
        app.getGUI().updateToolbarControls(false);
    }
    
    public void handleRedoRequest(){
        JTPS jtps = app.getJTPS();
        jtps.doTransaction();
        app.getGUI().updateToolbarControls(false);
    }
}
