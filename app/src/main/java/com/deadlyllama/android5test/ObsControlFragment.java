package com.deadlyllama.android5test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.deadlyllama.android5test.websocket.ObsEndpoint;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ObsControlFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ObsControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObsControlFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String TAG = "ObsControlFragment";

    private OnFragmentInteractionListener mListener;

    private Button button;
    private WebSocket ws;
    private TextView responseView;
    private Handler handler;

    public ObsControlFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ObsControlFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ObsControlFragment newInstance() {
        ObsControlFragment fragment = new ObsControlFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_obs_control, container, false);

        // Inflate the layout for this fragment
        button = view.findViewById(R.id.initConnectionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialiseConnection();
            }
        });

        Button getVersionButton = view.findViewById(R.id.setCodeScene);
        getVersionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "{ \"request-type\": \"SetCurrentScene\", \"scene-name\": \"Coding\", \"message-id\": \"qweqwe\" }";
                Log.d(TAG, "onClick: sending: " + message);
                ws.send(message);
            }
        });

        responseView = view.findViewById(R.id.lastResponseTextView);

        Button getSceneButton = view.findViewById(R.id.getCurrentScene);
        getSceneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "{ \"request-type\": \"GetCurrentScene\", \"message-id\": \"qweqwe\" }";
                Log.d(TAG, "onClick: sending: " + message);
                ws.send(message);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void initialiseConnection() {
        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Log.d(TAG, "handleMessage: fired outside background thread!!");

                if (msg.what == -1) {
                    Log.d(TAG, "handleMessage: RE-ENABLING BUTTON");
                    button.setEnabled(true);
                }

                if (msg.what == 0) {
                    responseView.setText(msg.obj.toString());
                }

                super.handleMessage(msg);
            }
        };

        Log.d(TAG, "start: Starting");
        Request request = new Request.Builder().url("ws://192.168.1.151:4444").build();
        ObsEndpoint listener = new ObsEndpoint(handler);
        OkHttpClient client = new OkHttpClient();

        Log.d(TAG, "start: Initialising websocket");
        ws = client.newWebSocket(request, listener);

        Log.d(TAG, "start: Dispatching?");
        button.setEnabled(false);
        client.dispatcher().executorService().shutdown();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
