package com.example.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.chat.databinding.ActivityMainBinding;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.provider.FirebaseInitProvider;

public class MainActivity extends AppCompatActivity {

    private boolean isSigningup=true;
    private ActivityMainBinding binding;
    private EditText edtUsername,edtPassword,edtEmail;
    private Button btnSubmit;
    private TextView txtLogininfo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        edtEmail=findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtUsername = findViewById(R.id.edtUsername);
        btnSubmit = findViewById(R.id.btnSubmit);
        txtLogininfo = findViewById (R.id.txtLogininfo);


       if( FirebaseAuth.getInstance().getCurrentUser()!=null){
           startActivity(new Intent(MainActivity.this, FriendsActivity.class));
           finish();
       }
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtEmail.getText().toString().isEmpty()|| edtPassword.getText().toString().isEmpty()){

                    if(isSigningup&& edtUsername.toString().isEmpty()){
                        Toast.makeText(MainActivity.this, "invalid input", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (isSigningup){
                    handleSignUp();
                }else {
                    handleLogin();
                }
            }
        });

        txtLogininfo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                if (isSigningup) {
                    isSigningup = false;
                    btnSubmit.setText("Log in");
                    edtUsername.setVisibility(View.GONE);
                    txtLogininfo.setText("Don't have an account? Sign up");
                } else {
                    isSigningup = true;
                    edtUsername.setVisibility(View.VISIBLE);
                    btnSubmit.setText("sign up");
                    txtLogininfo.setText("already have an account? login");
                }
            }
        });

    }
    private void handleSignUp()
    {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference("user/"+FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new User(edtUsername.getText().toString(),edtEmail.getText().toString(),""));
                    startActivity(new Intent(MainActivity.this, FriendsActivity.class));

                    Toast.makeText(MainActivity.this,"signed up ya basha",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void handleLogin()
    {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, FriendsActivity.class));

                    Toast.makeText(MainActivity.this,"logged in ya basha",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}