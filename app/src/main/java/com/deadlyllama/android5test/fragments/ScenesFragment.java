package com.deadlyllama.android5test.fragments;

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

import com.deadlyllama.android5test.R;
import com.deadlyllama.android5test.models.ObsScene;
import com.deadlyllama.android5test.models.ObsSceneList;
import com.deadlyllama.android5test.service.WebsocketService;
import com.deadlyllama.android5test.websocket.requests.GetSceneList;
import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.WebSocket;

public class ScenesFragment extends Fragment {

    private static final String TAG = "ScenesFragment";
    private static WebSocket Websocket;
    private OnFragmentInteractionListener mListener;
    private ObsSceneList sceneList;

    public ScenesFragment() {}

    public static ScenesFragment newInstance() {
        return new ScenesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            Handler handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message inputMessage) {
                    Log.d(TAG, "handleMessage: handle message fired - scenes fragment" + inputMessage.toString());

                    if (inputMessage.what == 0) {
                        String response = (String) inputMessage.obj;
                        Gson gson = new Gson();

                        sceneList = gson.fromJson(response, ObsSceneList.class);

                        Log.d(TAG, "handleMessage: Done serialising!");
                    }
                }
            };

            WebsocketService websocketService = WebsocketService.getInstance();
            websocketService.getListener().setHandler(handler);
            Websocket = websocketService.getWebsocket();

            GetSceneList request = new GetSceneList();
            Websocket.send(request.serialise());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scenes, container, false);
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
