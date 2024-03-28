package com.example.sugarsense.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.sugarsense.Assistance_Page;
import com.example.sugarsense.Diabetes_Risk_Analysis_Page;
import com.example.sugarsense.Diet_Plans_Page;
import com.example.sugarsense.Health_Risk_Page;
import com.example.sugarsense.Precautions_Page;
import com.example.sugarsense.R;
import com.example.sugarsense.Splash_Screen_Diabetes_Risk_Analysis;
import com.example.sugarsense.Your_Analysis_Report_Page;
import com.example.sugarsense.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Home_Fragment extends Fragment {

    Activity content;
    String user_id;
    String username;
    DatabaseReference db;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        content = getActivity();

       return root;
    }

    public void onStart()
    {
        super.onStart();
        TextView Profile_Name = content.findViewById(R.id.Profile_Name);
        MaterialCardView Health_Risk_cardview = content.findViewById(R.id.Health_Risk_cardview);
        MaterialCardView Precautions_cardview = content.findViewById(R.id.Precautions_cardview);
        MaterialCardView Diet_Plans_cardview = content.findViewById(R.id.Diet_Plans_cardview);
        MaterialCardView Medical_Assistance_Cardview = content.findViewById(R.id.Medical_Assistance_Cardview);
        MaterialCardView Diabetes_Risk_Analysis_cardview = content.findViewById(R.id.Diabetes_Risk_Analysis_cardview);
        MaterialCardView Report_Analysis_cardview = content.findViewById(R.id.Report_Analysis_cardview);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
        {
            user_id = user.getEmail();
        }

        db = FirebaseDatabase.getInstance().getReference("User Profile Name");
        db.child(user_id.replace(".","")).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task)
            {
                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {
                        DataSnapshot dataSnapshot = task.getResult();
                        Profile_Name.setText(String.valueOf(dataSnapshot.getValue()));
                    }
                }
            }
        });

        Health_Risk_cardview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(content, Health_Risk_Page.class);
                startActivity(intent);
                content.finish();
            }
        });

        Precautions_cardview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(content, Precautions_Page.class);
                startActivity(intent);
                content.finish();
            }
        });

        Diet_Plans_cardview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(content, Diet_Plans_Page.class);
                startActivity(intent);
                content.finish();
            }
        });

        Medical_Assistance_Cardview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(content, Assistance_Page.class);
                startActivity(intent);
                content.finish();
            }
        });

        Diabetes_Risk_Analysis_cardview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(content, Splash_Screen_Diabetes_Risk_Analysis.class));
            }
        });

        Report_Analysis_cardview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(content, Your_Analysis_Report_Page.class));
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}