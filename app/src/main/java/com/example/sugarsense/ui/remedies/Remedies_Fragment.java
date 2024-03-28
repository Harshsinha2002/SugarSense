package com.example.sugarsense.ui.remedies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.sugarsense.R;
import com.example.sugarsense.Remedies_page;
import com.example.sugarsense.databinding.FragmentRemediesBinding;

public class Remedies_Fragment extends Fragment {

    Activity content;
    ImageView Homeopathic_img, Ayurvedic_img, Allopathic_img, Life_style_changes_img;
    TextView Homeopathic_text, Ayurvedic_text, Allopathic_text, Life_style_changes_text;
    int char_count;
    boolean animation_done_once = false;
    private FragmentRemediesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRemediesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        content = getActivity();

       return root;
    }

    public void onStart()
    {
        super.onStart();
        Homeopathic_img = content.findViewById(R.id.Homeopathic_img);
        Ayurvedic_img = content.findViewById(R.id.Ayurvedic_img);
        Allopathic_img = content.findViewById(R.id.Allopathic_img);
        Life_style_changes_img = content.findViewById(R.id.Life_style_changes_img);

        Homeopathic_text = content.findViewById(R.id.Homeopathic_text);
        Ayurvedic_text = content.findViewById(R.id.Ayurvedic_text);
        Allopathic_text = content.findViewById(R.id.Allopathic_text);
        Life_style_changes_text = content.findViewById(R.id.Life_style_changes_text);

        Homeopathic_img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(content, Remedies_page.class);
                intent.putExtra("Remedy", "Homeopathic");
                startActivity(intent);
                Animatoo.INSTANCE.animateShrink(content);
            }
        });

        Ayurvedic_img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(content, Remedies_page.class);
                intent.putExtra("Remedy", "Ayurvedic");
                startActivity(intent);
                Animatoo.INSTANCE.animateShrink(content);
            }
        });

        Allopathic_img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(content, Remedies_page.class);
                intent.putExtra("Remedy", "Allopathic");
                startActivity(intent);
                Animatoo.INSTANCE.animateShrink(content);
            }
        });

        Life_style_changes_img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(content, Remedies_page.class);
                intent.putExtra("Remedy", "Life style changes");
                startActivity(intent);
                Animatoo.INSTANCE.animateShrink(content);
            }
        });


        if(animation_done_once == false)
        {
            animation_done_once = true;
            setHomeopathicRemediesImage();
        }
    }

    private void setHomeopathicRemediesImage()
    {
        Homeopathic_img.setImageDrawable(content.getDrawable(R.drawable.homeopathic_remedies_img));
        Animation animation = AnimationUtils.loadAnimation(content.getApplicationContext(), R.anim.move_right_to_left_animation);
        Homeopathic_img.startAnimation(animation);
        char_count = 0;
        printTextHomeopathic("HOMEOPATHIC");
    }

    private void setAyurvedicRemediesImage()
    {
        Ayurvedic_img.setImageDrawable(content.getDrawable(R.drawable.ayurvedic_remedies_img));
        Animation animation = AnimationUtils.loadAnimation(content.getApplicationContext(), R.anim.move_right_to_left_animation);
        Ayurvedic_img.startAnimation(animation);
        char_count = 0;
        printTextAyurvedic("AYURVEDIC");
    }

    private void setAllopathicRemediesImage()
    {
        Allopathic_img.setImageDrawable(content.getDrawable(R.drawable.allopathic_remedies_img));
        Animation animation = AnimationUtils.loadAnimation(content.getApplicationContext(), R.anim.move_right_to_left_animation);
        Allopathic_img.startAnimation(animation);
        char_count = 0;
        printTextAllopathic("ALLOPATHIC");
    }

    private void setLife_style_changesRemediesImage()
    {
        Life_style_changes_img.setImageDrawable(content.getDrawable(R.drawable.life_style_changes_img));
        Animation animation = AnimationUtils.loadAnimation(content.getApplicationContext(), R.anim.move_right_to_left_animation);
        Life_style_changes_img.startAnimation(animation);
        char_count = 0;
        printTextLife_style_changes("LIFESTYLE CHANGES");
    }

    private void printTextHomeopathic(String text)
    {
        if(char_count <= text.length())
        {
            String fetchedText = text.substring(0, char_count);
            Homeopathic_text.setText(fetchedText);
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    char_count++;
                    printTextHomeopathic(text);
                }
            }, 200);
        }
        else
        {
            setAyurvedicRemediesImage();
        }
    }

    private void printTextAyurvedic(String text)
    {
        if(char_count <= text.length())
        {
            String fetchedText = text.substring(0, char_count);
            Ayurvedic_text.setText(fetchedText);
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    char_count++;
                    printTextAyurvedic(text);
                }
            }, 200);
        }
        else
        {
            setAllopathicRemediesImage();
        }
    }

    private void printTextAllopathic(String text)
    {
        if(char_count <= text.length())
        {
            String fetchedText = text.substring(0, char_count);
            Allopathic_text.setText(fetchedText);
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    char_count++;
                    printTextAllopathic(text);
                }
            }, 200);
        }
        else
        {
            setLife_style_changesRemediesImage();
        }
    }

    private void printTextLife_style_changes(String text)
    {
        if(char_count <= text.length())
        {
            String fetchedText = text.substring(0, char_count);
            Life_style_changes_text.setText(fetchedText);
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    char_count++;
                    printTextLife_style_changes(text);
                }
            }, 200);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}