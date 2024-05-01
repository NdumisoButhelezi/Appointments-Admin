package com.example.appointments_admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class GoogleSignInActivity extends AppCompatActivity {

    private static final String TAG = "GoogleSignInActivity";
    private static final int REQ_ONE_TAP_SIGN_IN = 100;
    private FirebaseAuth mAuth;
    private SignInClient signInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_in);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Google One Tap sign-in
        signInClient = Identity.getSignInClient(this);

        // Start One Tap sign-in flow
        BeginSignInRequest signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                        GoogleIdTokenRequestOptions.builder()
                                .setSupported(true)
                                // Update with your new API key
                                .setServerClientId("119418552495-ktmcfktf8vsvn39h8g6qhvmg16ar37e0.apps.googleusercontent.com")
                                .setFilterByAuthorizedAccounts(true)
                                .build())
                .build();

        signInClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this::handleSignInResult)
                .addOnFailureListener(e -> Log.e(TAG, "Error signing in with Google One Tap", e));
    }

    private void handleSignInResult(BeginSignInResult result) {
        Intent intent;
        try {
            intent = result.getPendingIntent().getIntentSender().sendIntent(this, REQ_ONE_TAP_SIGN_IN, null, null, 0, null, null);
            startActivityForResult(intent, REQ_ONE_TAP_SIGN_IN);
        } catch (Exception e) {
            Log.e(TAG, "Error handling sign-in result", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_ONE_TAP_SIGN_IN) {
            // Handle the result of the One Tap sign-in flow
            BeginSignInResult result = BeginSignInResult.fromIntent(data);
            if (result != null && resultCode == RESULT_OK) {
                String idToken;
                idToken = result.getIdToken();
                firebaseAuthWithGoogle(idToken);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Navigate to the next activity or update the UI as needed
                    } else {
                        // If sign in fails, display a message to the user.
                        // Update the UI accordingly
                    }
                });
    }
}
