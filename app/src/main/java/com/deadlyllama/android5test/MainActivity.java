package com.deadlyllama.android5test;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.deadlyllama.android5test.fragments.DashboardFragment;
import com.deadlyllama.android5test.fragments.ScenesFragment;
import com.deadlyllama.android5test.service.WebsocketService;
import com.deadlyllama.android5test.websocket.ObsEndpoint;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.deadlyllama.android5test.ui.main.SectionsPagerAdapter;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MainActivity extends AppCompatActivity implements
        ObsControlFragment.OnFragmentInteractionListener,
        DashboardFragment.OnFragmentInteractionListener,
        ScenesFragment.OnFragmentInteractionListener
{

    private static final String TAG = "MainActivity";
    private static final WebsocketService websocketService = WebsocketService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Starting websocket!");
                start();
            }
        });
        
        start();
    }

    private void start() {
        Log.d(TAG, "start: Starting");
        Request request = new Request.Builder().url("ws://192.168.1.151:4444").build();

        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                Log.d(TAG, "handleMessage: handle message fired! " + inputMessage.toString());
            }
        };

        ObsEndpoint listener = new ObsEndpoint(handler);
        OkHttpClient client = new OkHttpClient();

        WebSocket websocket = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();

        websocketService.setListener(listener);
        websocketService.setWebsocket(websocket);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}
}