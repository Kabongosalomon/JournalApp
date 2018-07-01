package com.salomon.tehilah.journalapp.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.salomon.tehilah.journalapp.R;
import com.salomon.tehilah.journalapp.login.LoginPresenter;
import com.salomon.tehilah.journalapp.login.LoginPresenterImpl;
import com.salomon.tehilah.journalapp.noteslist.ui.Activity_noteList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Salomon KABONGO on 29-Jun-18.
 */
public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, LoginView {
    // Constant for logging
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;

    @BindView(R.id.bSignInGoogle)
    SignInButton bSignInGoogle;
    @BindView(R.id.bSignIn)
    Button bSignIn;
    @BindView(R.id.bSignUp)
    Button bSignUp;
    @BindView(R.id.rootLayout)
    ConstraintLayout rootLayout;
    @BindView(R.id.tEtEmail)
    TextInputEditText tEtEmail;
    @BindView(R.id.tEtPassword)
    TextInputEditText tEtPassword;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private LoginPresenter loginPresenter;

    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginPresenter = new LoginPresenterImpl(this);
        loginPresenter.onCreate();
        loginPresenter.checkForAuthenticatedUser();


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //init FireBase
//        mAuth = FirebaseAuth.getInstance();
//        db= FirebaseDatabase.getInstance();
//        users = db.getReference("Users");

    }

    @Override
    protected void onDestroy() {
        loginPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
        /*updateUI(currentUser);*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            hideProgress();
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Snackbar.make(rootLayout, "Welcome ", Snackbar.LENGTH_SHORT)
                    .show();
            startActivity(new Intent(this, Activity_noteList.class));

        }
    }


    @Override
    @OnClick(R.id.bSignUp)
    public void handleSignUp() {
        loginPresenter.registerNewUser(tEtEmail.getText().toString(),
                tEtPassword.getText().toString());
    }

    @Override
    @OnClick(R.id.bSignIn)
    public void handleSignIn() {
        loginPresenter.validateLogin(tEtEmail.getText().toString(),
                tEtPassword.getText().toString());
    }

    @Override
    @OnClick(R.id.bSignInGoogle)
    public void handleSignInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        showProgress();
    }


    @Override
    public void showProgress() {
//        final AlertDialog waitingDialog = new SpotsDialog(MainActivity.this);
//        waitingDialog.show();
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void hideProgress() {
//        final AlertDialog waitingDialog = new SpotsDialog(MainActivity.this);
//        waitingDialog.dismis();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }


    @Override
    public void loginError(String error) {
        tEtPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signin), error);
        tEtPassword.setError(msgError);
    }

    @Override
    public void navigateToMainScreen() {
        startActivity(new Intent(this, Activity_noteList.class));
    }


    @Override
    public void newUserSuccess() {
        Snackbar.make(rootLayout, R.string.login_notice_message_useradded, Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void newUserError(String error) {
        tEtPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signup), error);
        tEtPassword.setError(msgError);
    }


    private void setInputs(boolean enabled) {
        bSignIn.setEnabled(enabled);
        bSignUp.setEnabled(enabled);
        tEtEmail.setEnabled(enabled);
        tEtPassword.setEnabled(enabled);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

