package djf.components;

import java.io.IOException;

/**
 *
 * @author Dan Kim
 */
public interface AppFileComponent {
    public void saveData(AppDataComponent data, String filePath) throws IOException;
    public void loadData(AppDataComponent data, String filePath) throws IOException;
    public void exportData(AppDataComponent data, String filePath) throws IOException;
    public void importData(AppDataComponent data, String filePath) throws IOException;
    
}
