package com.example.bruno.ajedrezporcorrespondencia;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragmentForMainActivity.listenerMainFragment} interface
 * to handle interaction events.
 * Use the {@link MainFragmentForMainActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragmentForMainActivity extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Listener listenerMainFragment;
    View challegereButton;
    View galeriaButton;

    public interface Listener {

        void navigateToGaleria();
        void navigateToChalleger();
    }

    // TODO: Rename and change types of parameters

    public MainFragmentForMainActivity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragmentForMainActivity.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragmentForMainActivity newInstance() {
        MainFragmentForMainActivity fragment = new MainFragmentForMainActivity();
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
        return inflater.inflate(R.layout.main_fragment_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listenerMainFragment = (Listener) getActivity();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        challegereButton = view.findViewById(R.id.buttonChalleger);
        challegereButton.findViewById(R.id.buttonChalleger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerMainFragment.navigateToChalleger();
            }
        });
        galeriaButton = view.findViewById(R.id.buttonGaleria);
        view.findViewById(R.id.buttonGaleria).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerMainFragment.navigateToGaleria();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listenerMainFragment = null;
    }


}
