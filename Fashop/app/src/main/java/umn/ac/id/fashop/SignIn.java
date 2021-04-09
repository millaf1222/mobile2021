package umn.ac.id.fashop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnSignIn;
    private TextView btnCreate,forgotTextLink;
    private FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //menyembunyikan bar activity
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        fAuth   = FirebaseAuth.getInstance();
        btnSignIn = findViewById(R.id.btnSignIn);
        btnCreate = findViewById(R.id.btnCreate);
        forgotTextLink = findViewById(R.id.forgotPassword);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    etUsername.setError("username is Required.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                //authentication user
                fAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignIn.this, "Sign In is Successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else {
                            Toast.makeText(SignIn.this, "Error! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                btnCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(),SignUp.class));
                    }
                });

                forgotTextLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText resetEmail = new EditText(v.getContext());
                        AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                        passwordResetDialog.setTitle("Reset Password ?");
                        passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
                        passwordResetDialog.setView(resetEmail);

                        passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //extract the email and send reset link
                                String mail = resetEmail.getText().toString();
                                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(SignIn.this, "Reset Link Sent To your Email.", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SignIn.this, "Error! Reset Link  is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                        passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //close the dialog
                            }
                        });

                        passwordResetDialog.create().show();
                    }
                });

            }
        });
    }
}