package com.salomon.tehilah.journalapp.login;

/**
 * Created by Salomon KABONGO on 27-Jun-18.
 */
public interface LoginInteractor {

    void checkAlreadyAuthenticated();
    void doSignUp(String email, String password);
    void doSignIn(String email, String password);

}
