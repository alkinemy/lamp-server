package lamp.admin.domain.app.base;

public interface AppManagementListener {

    void afterAppStart();

    void beforeAppStop();

//    void appDidFinishLaunching();
//
//    void appWillTerminate();
}
