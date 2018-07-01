package com.salomon.tehilah.journalapp.login.ui;

/**
 * Created by Salomon KABONGO on 27-Jun-18.
 */
public interface LoginView {

    void enableInputs();
    void disableInputs();
    void showProgress();
    void hideProgress();


    void handleSignUp();
    void handleSignIn();
    void handleSignInWithGoogle();

    void navigateToMainScreen();
    void loginError(String error);

    void newUserSuccess();
    void newUserError(String error);

}
