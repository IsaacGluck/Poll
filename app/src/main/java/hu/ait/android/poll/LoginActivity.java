package hu.ait.android.poll;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView((R.id.etPassword))
    EditText etPassword;
    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        displayUsername(currentUser);
//    }

    @OnClick(R.id.btnRegister)
    void registerClick() {
        if (!isFormValid()) {
            return;
        }

        showProgressDialog();

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();

                            displayUsername(user);

                            Toast.makeText(LoginActivity.this,
                                    "Registration ok", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this,
                                    "Authentication failed. Error: "
                                            + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
//                            displayUsername(null);
                        }
                    }
                });
    }

    @OnClick(R.id.btnLogin)
    void loginClick() {
        if (!isFormValid()) {
            return;
        }

        showProgressDialog();

        firebaseAuth.signInWithEmailAndPassword(
                etEmail.getText().toString(),
                etPassword.getText().toString()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if (task.isSuccessful()) {
                    // open messages Activity
                    startActivity(new Intent(LoginActivity.this, PollActivity.class));

                } else {
                    Toast.makeText(LoginActivity.this,
                            "Error: "+task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }





    private void displayUsername(FirebaseUser user) {
        user.updateProfile(new UserProfileChangeRequest.Builder().
                setDisplayName(usernameFromEmail(user.getEmail())).build());
    }

    private boolean isFormValid() {
        if (TextUtils.isEmpty(etEmail.getText())) {
            etEmail.setError("The email can not be empty");
            return false;
        }

        if (TextUtils.isEmpty(etPassword.getText())) {
            etPassword.setError("The password can not be empty");
            return false;
        }

        return true;
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Hold On...");
        }

        progressDialog.show();
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }
}
