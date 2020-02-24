package com.deadlyllama.android5test.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.deadlyllama.android5test.R;
import com.deadlyllama.android5test.models.ObsScene;
import com.deadlyllama.android5test.models.ObsSceneList;
import com.deadlyllama.android5test.service.WebsocketService;
import com.deadlyllama.android5test.websocket.requests.GetSceneList;
import com.deadlyllama.android5test.websocket.requests.SetCurrentScene;
import com.google.gson.JsonObject;

import okhttp3.WebSocket;

public class ScenesFragment extends Fragment {

    private static final String TAG = "ScenesFragment";
    private WebSocket websocket;
    private ObsScene currentScene;
    public ObsSceneList sceneList;
    public LinearLayout linearLayout;
    public ProgressBar progressBar;

    private OnFragmentInteractionListener mListener;

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
                    if (inputMessage.what == 0) {
                        JsonObject data = (JsonObject) inputMessage.obj;

                        if (data.has("message-id")) {
                            if (data.get("message-id").getAsString().equals("GetSceneList")) {
                                sceneList = ObsSceneList.fromJsonObject((JsonObject) (inputMessage.obj));

                                progressBar.setVisibility(View.INVISIBLE);

                                for (ObsScene scene : sceneList.scenes) {
                                    Button button = new Button(getContext());
                                    button.setText(scene.name);
                                    button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                    button.setPadding(50, 35, 50, 35);
                                    linearLayout.addView(button);

                                    attachClickListener(button, scene);
                                }
                            }
                        }
                    }
                }
            };

            if (sceneList == null) {
                WebsocketService websocketService = WebsocketService.getInstance();
                websocketService.getListener().setHandler(handler);
                websocket = websocketService.getWebsocket();

                GetSceneList request = new GetSceneList();
                websocket.send(request.serialise());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scenes, container, false);
        linearLayout = view.findViewById(R.id.sceneFragmentLinearLayout);
        progressBar = view.findViewById(R.id.progress_sceneList);
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

    public void attachClickListener(final Button button, final ObsScene scene) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 1; i <= linearLayout.getChildCount(); i++) {
                    if (linearLayout.getChildAt(i) instanceof Button) {
                        Button button = (Button) linearLayout.getChildAt(i);
                        button.setTextColor(Color.DKGRAY);
                    }
                }

                currentScene = scene;
                button.setTextColor(Color.BLUE);
                SetCurrentScene setCommand = new SetCurrentScene(scene.name);
                websocket.send(setCommand.serialise());
            }
        });
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
