package com.example.skatingclub;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends DrawerBaseActivity {

    private static final String EXTRA_CURRENT_USER_ID = "currentUser";
    private static final String EXTRA_OTHER_USER_ID = "otherUser";

    private TextView textViewName;
    private View isOnlineChat;
    private RecyclerView recyclerViewMessages;
    private EditText editTextMessage;

    private FloatingActionButton buttonSend;
    private MessageAdapter messageAdapter;
    public static String currentUserId;
    private static String otherUserId;

    private ChatViewModel viewModel;
    private ChatViewModelFactory viewModelFactory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initViews();
        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        otherUserId = getIntent().getStringExtra(EXTRA_OTHER_USER_ID);
        viewModelFactory = new ChatViewModelFactory(currentUserId, otherUserId);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(ChatViewModel.class);
        messageAdapter = new MessageAdapter(currentUserId);
        recyclerViewMessages.setAdapter(messageAdapter);
        observeViewModel();
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message(
                        editTextMessage.getText().toString(),
                        currentUserId,
                        otherUserId
                );
                if (!message.getText().equals("")) {
                    viewModel.sendMessage(message);
                }
            }
        });
    }



    private void initViews() {
        textViewName = findViewById(R.id.textViewName);
        isOnlineChat = findViewById(R.id.isOnlineChat);
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.btnSend);
    }

    private void observeViewModel() {
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null) {
                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                messageAdapter.setMessages(messages);
            }
        });
        viewModel.getIsSent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSent) {
                if (isSent) {
                    editTextMessage.setText("");
                }
            }
        });
        viewModel.getOtherUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                textViewName.setText(user.getName());
                int resId;
                if (user.isOnline()) {
                    resId = R.drawable.ic_online_user;
                } else {
                    resId = R.drawable.ic_offline_user;
                }
                Drawable drawable = ContextCompat.getDrawable(MainActivity.this, resId);
                isOnlineChat.setBackground(drawable);
            }
        });
    }

    static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId);
        intent.putExtra(EXTRA_OTHER_USER_ID, otherUserId);
        return intent;
    }
    @Override
    protected void onResume() {
        super.onResume();
        viewModel.setOnlineStatus(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.setOnlineStatus(false);
    }
}