package lamp.admin.domain.app;

public interface AppManagementListener {

    void afterAppStart();

    void beforeAppStop();

//    void appDidFinishLaunching();
//
//    void appWillTerminate();
}
