package com.example.sugarsense.ui.graph_alalysis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.sugarsense.Graph_Data_Input_Page;
import com.example.sugarsense.MainActivity;
import com.example.sugarsense.R;
import com.example.sugarsense.databinding.FragmentGraphAnalysisBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Graph_Analysis_Fragment extends Fragment {

    Activity content;

    private FragmentGraphAnalysisBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGraphAnalysisBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        content = getActivity();

        return root;
    }

    public void onStart() {
        super.onStart();
        ImageView Gif_View = content.findViewById(R.id.Gif_View);
        BottomNavigationView BottomView = content.findViewById(R.id.nav_view);

        BottomView.setVisibility(View.INVISIBLE);
        Glide.with(this).asGif().load(R.raw.line_graph).into(Gif_View);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(content, Graph_Data_Input_Page.class);
                startActivity(intent);
                Animatoo.INSTANCE.animateInAndOut(content);
            }
        }, 9000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}