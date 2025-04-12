package managers;
import FileBackedTaskManager.FileBackedTaskManager;
import model.*;

import java.io.File;

public class Managers {

    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }

    public TaskManager getDefault(){
        return new InMemoryTaskManager();
    }

}
