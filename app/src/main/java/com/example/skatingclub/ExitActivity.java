package com.example.skatingclub;

import android.os.Bundle;
import android.widget.RelativeLayout;
import com.example.skatingclub.databinding.ActivityExitBinding;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class ExitActivity extends DrawerBaseActivity {

    private static final int SIGN_IN_CODE = 1;

    private RelativeLayout activity_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.skatingclub.databinding.ActivityExitBinding binding = ActivityExitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        allocateActivityTitle("Авторизация");

        activity_exit=findViewById(R.id.exit);

        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_CODE);
        else {
            Snackbar.make(activity_exit, "Вы авторизованы", Snackbar.LENGTH_LONG).show();
        }
    }


    }