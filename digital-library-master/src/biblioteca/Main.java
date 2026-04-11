package biblioteca;

import helpers.InitializerHelper;
import views.Login;

public class Main {

    public static void main(String[] args) {
        InitializerHelper.ensureDataFiles();
        Login.show();
    }
}