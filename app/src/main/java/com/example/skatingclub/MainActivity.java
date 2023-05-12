package com.example.skatingclub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import android.text.format.DateFormat;


public class MainActivity extends AppCompatActivity {

    private static final int SIGN_IN_CODE = 1;
    private RelativeLayout activity_main;
    private FloatingActionButton sendBtn;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        activity_main = findViewById(R.id.activity_main);

        // успешность авторизации
        if(requestCode == SIGN_IN_CODE) {
            if (resultCode== RESULT_OK){
                Snackbar.make(activity_main, "Вы успешно авторизованы", Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(activity_main, "Неуспешная авторизация", Snackbar.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity_main = findViewById(R.id.activity_main);


        // пользователь еще не зарегистрирован
        if (FirebaseAuth.getInstance().getCurrentUser()==null)
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_CODE);
        else {
            Snackbar.make(activity_main, "Вы авторизованы", Snackbar.LENGTH_LONG).show();
        displayAllMessenger();
        }

        sendBtn =  (FloatingActionButton) findViewById(R.id.btnSend);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textField = findViewById(R.id.messageField);
                if (textField.getText().toString() == "") { // если поле пустое, то выход из функции
                    return;
                }

                FirebaseDatabase.getInstance().getReference().push().setValue(
                        new Message(FirebaseAuth.getInstance().getCurrentUser().getEmail(), textField.getText().toString()
                        )
                );
                textField.setText("");
            }
        });
    }

    private void displayAllMessenger() {
        Query query = FirebaseDatabase.getInstance().getReference().child("Message");
        ListView listOfMessages = (ListView) findViewById(R.id.list_of_messages);

        FirebaseListOptions<Message> options=  new FirebaseListOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .setLayout(android.R.layout.activity_list_item) // хз то ли активити указано
                .build();
        FirebaseListAdapter<Message> adapter = new FirebaseListAdapter<Message>(options){
            @Override
            protected void populateView(@NonNull View v, @NonNull Message model, int position) {
                TextView mess_user, mess_time, mess_text;
                mess_user = v.findViewById(R.id.message_user);
                mess_time = v.findViewById(R.id.message_time);
                mess_text = v.findViewById(R.id.message_text);

                mess_user.setText(model.getUserName());
                mess_text.setText(model.getTextMessage());
                mess_time.setText(DateFormat.format("dd-mm-yyyy HH:mm:ss", model.getMessageTime()));
            }
        };
        listOfMessages.setAdapter(adapter);
    }
}