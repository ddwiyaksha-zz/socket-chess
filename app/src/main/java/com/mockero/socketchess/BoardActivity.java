package com.mockero.socketchess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class BoardActivity extends ActionBarActivity {

    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private BoardAdapter adapter;
    private CommunicationThread communication;
    private Receiver receiver;
    private TextView inputView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        initializeComponent();
    }

    private void initializeComponent() {
        inputView = (TextView) findViewById(R.id.input);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new GridLayoutManager(this, 8);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new BoardAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new Receiver(adapter, inputView);
        registerReceiver(receiver, new IntentFilter("com.mockero.socketchess.piece"));
        communication = new CommunicationThread(this);
        communication.restart();
        communication.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        communication.cancel();
        unregisterReceiver(receiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class Receiver extends BroadcastReceiver {

        private BoardAdapter adapter;
        private TextView inputView;

        public Receiver(BoardAdapter adapter, TextView inputView) {
            this.adapter = adapter;
            this.inputView = inputView;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("piece");
            Log.e("Receiver", "message : " + message);
            if(!TextUtils.isEmpty(message)) {
                if(!message.equals("Reconnecting..") && !message.equals("Starting..")) {
                    Log.e("Receiver", "processing : " + message);
                    adapter.setPiece(message);
                }
                inputView.setText(message);
            }
        }
    }

}
