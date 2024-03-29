package djf.settings;

/**
 *
 * @author Dan Kim
 */
public enum AppPropertyType {
    // LOADED FROM app_properties.xml

    // INITIAL WINDOW TITLE AND LOGO
    APP_TITLE,
    APP_LOGO,

    // INITIAL WINDOW GEOMETRY
    PREF_WIDTH,
    PREF_HEIGHT,
    START_MAXIMIZED,
    
    // FOR FINDING THE CSS FILE
    APP_CSS,
    APP_PATH_CSS,

    // FILE TOOLBAR ICONS
    NEW_ICON,
    LOAD_ICON,
    SAVE_ICON,
    SAVE_AS_ICON,
    EXPORT_ICON,
    EXIT_ICON,
    ABOUT_ICON,
    UNDO_ICON,
    REDO_ICON,
        
    // FILE TOOLBAR TOOLTIPS FOR BUTTONS
    NEW_TOOLTIP,
    LOAD_TOOLTIP,
    SAVE_TOOLTIP,
    SAVE_AS_TOOLTIP,
    EXPORT_TOOLTIP,
    EXIT_TOOLTIP,
    ABOUT_TOOLTIP,
    UNDO_TOOLTIP,
    REDO_TOOLTIP,
	
    // ERROR MESSAGES AND TITLES FOR DIALOG BOXES
    NEW_ERROR_MESSAGE,
    NEW_ERROR_TITLE,
    LOAD_ERROR_MESSAGE,
    LOAD_ERROR_TITLE,
    SAVE_ERROR_MESSAGE,
    SAVE_ERROR_TITLE,
    PROPERTIES_LOAD_ERROR_MESSAGE,
    PROPERTIES_LOAD_ERROR_TITLE,
	
    // AND VERIFICATION MESSAGES AND TITLES
    NEW_COMPLETED_MESSAGE,
    NEW_COMPLETED_TITLE,
    LOAD_COMPLETED_MESSAGE,
    LOAD_COMPLETED_TITLE,
    SAVE_COMPLETED_MESSAGE,
    SAVE_COMPLETED_TITLE,	
    SAVE_UNSAVED_WORK_TITLE,
    SAVE_UNSAVED_WORK_MESSAGE,
    SAVE_WORK_TITLE,
    LOAD_WORK_TITLE,

    // DATA FILE EXTENSIONS AND DESSCRIPTIONS
    WORK_FILE_EXT,
    WORK_FILE_EXT_DESC
}
