package com.example.twapp.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.twapp.R;
import com.example.twapp.utils.PlaySound;
import com.example.twapp.utils.SharedPreferencesUitl;
import com.example.twapp.utils.Vibrator;


public class TwHightFragment extends Fragment implements View.OnClickListener {
    private LinearLayout hightSound;
    private LinearLayout hightshake;
    private ImageView imageVibrator;
    private TextView tvVibrator, tvSound;
    private ImageView imageSound;
    private SharedPreferencesUitl preferencesUitl;
    private Vibrator vibrator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferencesUitl = SharedPreferencesUitl.getInstance(getActivity(), "tw");
        PlaySound.initSoundPool(getActivity());
        vibrator = new Vibrator(getActivity());
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myview = inflater.inflate(R.layout.fragment_hight, container, false);
        hightshake = myview.findViewById(R.id.thight_shake);
        hightSound = myview.findViewById(R.id.thight_sound);
        imageVibrator = myview.findViewById(R.id.image_vibrator);
        imageSound = myview.findViewById(R.id.image_sound);
        tvSound = myview.findViewById(R.id.tv_sound);
        tvVibrator = myview.findViewById(R.id.tv_vibrator);

        hightSound.setOnClickListener(this);
        hightshake.setOnClickListener(this);
        if (preferencesUitl.read("thightShake", false)) {
            tvVibrator.setTextColor(Color.parseColor("#1497db"));
            imageVibrator.setBackground(getActivity().getDrawable(R.drawable.vibrator_true));
        } else {
            tvVibrator.setTextColor(Color.parseColor("#000000"));
            imageVibrator.setBackground(getActivity().getDrawable(R.drawable.vibrator_false));
        }
        if (preferencesUitl.read("thightSound", false)) {
            tvSound.setTextColor(Color.parseColor("#1497db"));
            imageSound.setBackground(getActivity().getDrawable(R.drawable.sound_true));
        } else {
            tvSound.setTextColor(Color.parseColor("#000000"));
            imageSound.setBackground(getActivity().getDrawable(R.drawable.sound_false));
        }
        return myview;
    }

    @SuppressLint("ResourceAsColor")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.thight_shake:
                if (!preferencesUitl.read("thightShake", false)) {
                    imageVibrator.setBackground(getActivity().getDrawable(R.drawable.vibrator_true));
                    preferencesUitl.write("thightShake", true);
                    tvVibrator.setTextColor(Color.parseColor("#1497db"));
                    vibrator.setVibrator();
                } else {
                    imageVibrator.setBackground(getActivity().getDrawable(R.drawable.vibrator_false));
                    preferencesUitl.write("thightShake", false);
                    tvVibrator.setTextColor(Color.parseColor("#000000"));
                    vibrator.cancel();
                }
//                senBroadcast("com.thight.shake", true);
                break;
            case R.id.thight_sound:
                if (!preferencesUitl.read("thightSound", false)) {
                    imageSound.setBackground(getActivity().getDrawable(R.drawable.sound_true));
                    preferencesUitl.write("thightSound", true);
                    PlaySound.play(PlaySound.HIGHT_SOUND, PlaySound.NO_CYCLE);
                    tvSound.setTextColor(Color.parseColor("#1497db"));
                } else {
                    imageSound.setBackground(getActivity().getDrawable(R.drawable.sound_false));
                    preferencesUitl.write("thightSound", false);
                    tvSound.setTextColor(Color.parseColor("#000000"));
                    PlaySound.stop(PlaySound.HIGHT_SOUND);
                }
//                senBroadcast("com.thight.sound", true);
                break;
        }
    }

    private void senBroadcast(String s, boolean b) {
        Intent intent = new Intent();
        intent.putExtra("twapp", b);
        intent.setAction(s);
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        vibrator.cancel();
    }
}
