package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    ArrayList<Message> messageList;
    Button sendButton;
    Button receiveButton;
    EditText inputMessage;
    ListView listView;

    Cursor cursor;
    SQLiteDatabase database;
    Database databaseOpener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        listView = findViewById(R.id.listView);
        sendButton = findViewById(R.id.sendButton);
        receiveButton = findViewById(R.id.receiveButton);
        inputMessage = findViewById(R.id.textBox);
        messageList = new ArrayList<>();
        databaseOpener = new Database(this);
        database = databaseOpener.getWritableDatabase();
        cursor = database.query(false, Database.TABLE_NAME, new String[] {Database.COLUMN_ID,
                Database.COLUMN_USER, Database.COLUMN_MESSAGE},
                null, null, null, null,null, null);

        cursor.moveToNext();
        while(cursor.moveToNext()){
            String user = cursor.getString(cursor.getColumnIndex(Database.COLUMN_USER));
            String message = cursor.getString(cursor.getColumnIndex(Database.COLUMN_MESSAGE));
            long id = cursor.getLong(cursor.getColumnIndex(Database.COLUMN_ID));

            if(user.equals("MessageSend")) {
                messageList.add(new MessageSend(user, message, id));
            } else {
                messageList.add(new MessageReceive(user, message, id));
            }
        }

        final BaseAdapter listAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return messageList.size();
            }

            @Override
            public Message getItem(int position) {
                return messageList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Message message = getItem(position);

                if(message.isSend()) {
                   convertView = getLayoutInflater().inflate(R.layout.sendlayout, parent, false);
                   TextView textView = convertView.findViewById(R.id.messageSent);
                   textView.setText(message.getMessage());
                } else if (message.isReceived()) {
                    convertView = getLayoutInflater().inflate(R.layout.receivelayout, parent, false);
                    TextView textView = convertView.findViewById(R.id.messageReceived);
                    textView.setText(message.getMessage());
                }

                return convertView;
            }
        };

        listView.setAdapter(listAdapter);

        sendButton.setOnClickListener(view -> {
            String input = inputMessage.getText().toString();
            Message message = new MessageSend("MessageSend", input, databaseOpener.insertData("MessageSend", input));
            messageList.add(message);
            listAdapter.notifyDataSetChanged();
            inputMessage.setText("");
            dragToBottom();
        });

        receiveButton.setOnClickListener(view -> {
            String input = inputMessage.getText().toString();
            Message message = new MessageSend("MessageReceive", input, databaseOpener.insertData("MessageReceive", input));
            messageList.add(message);
            listAdapter.notifyDataSetChanged();
            inputMessage.setText("");
            dragToBottom();
        });

        inputMessage.setOnClickListener(view -> inputMessage.setText(""));

        printCursor(cursor);
    }
    private void dragToBottom() {
        this.listView.post(() -> listView.setSelection(messageList.size() - 1));
    }

    private String printCursor(Cursor cursor){
        Log.d("VERSION_NUMBER","Version: " + database.getVersion());
        Log.d("NUM_OF_COLUMN", "Number of columns: " + cursor.getColumnCount());
        Log.d("NAME_OF_COLUMN","Name of columns: " + cursor.getColumnName(cursor.getColumnIndex(Database.COLUMN_MESSAGE)));
        Log.d("NUM_OF_RESULT","Number of results: " + cursor.getCount());
        String databaseString ="";
        cursor.moveToFirst();
        for(Message i : messageList){
            Log.d("ROW_OF_RESULT","ID: " + i.getId() +
                    " User: " + i.getUser() + " Message: " + i.getMessage());
        }
        database.close();
        return databaseString;
    }
}