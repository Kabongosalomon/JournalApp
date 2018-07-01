package com.salomon.tehilah.journalapp.login;

/**
 * Created by Salomon KABONGO on 27-Jun-18.
 */
public interface LoginRepository {
    void signUp(final String email, final String password);
    void signIn(String email, String password);
    void checkAlreadyAuthenticated();
}
