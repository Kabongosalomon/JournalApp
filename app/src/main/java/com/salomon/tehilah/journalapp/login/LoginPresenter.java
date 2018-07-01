package com.salomon.tehilah.journalapp.login;

import com.salomon.tehilah.journalapp.login.events.LoginEvent;

/**
 * Created by Salomon KABONGO on 27-Jun-18.
 */
public interface LoginPresenter {
    void onCreate();
    void onDestroy();
    void checkForAuthenticatedUser();
    void onEventMainThread(LoginEvent event);
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);
}
