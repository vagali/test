package businessLogic;

/**
 *
 * @author Luis
 */
public class MateriaManagerFactory {
    public static final String TEST="TEST";
    
    public static MateriaManager createMateriaManager(String type) {
        MateriaManager materiaManager = null;
        switch(type){
            case TEST:
                break;
            default:
                materiaManager = new MateriaManagerImplementation();
        }
        return materiaManager;
    }
}