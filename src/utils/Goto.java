package utils;

import gui.LoginPane;

public class Goto {
     private static LoginPane loginPane;

    public static void setLoginPane(LoginPane loginPane) {
        Goto.loginPane = loginPane;
    }
}
