package demo.firebaseauth.com.firebaseauthphone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class Login extends AppCompatActivity {
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private Button buttonLogin;
    EditText editTextPhone;
    private static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeCallback();
        initializeWedges();


    }

    private void initializeWedges() {
        buttonLogin=(Button)findViewById(R.id.btn_login);
        editTextPhone=(EditText)findViewById(R.id.edit_text_phone);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        editTextPhone.getText().toString(),        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        Login.this,               // Activity (for callback binding)
                        mCallbacks);
            }
        });

    }

    private void initializeCallback() {


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential.getSmsCode());


            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);
                Intent intent=new Intent(Login.this,LoginVerification.class);
                intent.putExtra("strVerificationID",verificationId);
                startActivity(intent);


                // ...
            }


        };

    }


}
