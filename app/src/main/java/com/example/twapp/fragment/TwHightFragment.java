package com.example.twapp.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.twapp.R;
import com.example.twapp.base.BaseFragment;
import com.example.twapp.utils.PlaySound;
import com.example.twapp.utils.SharedPreferencesUitl;
import com.example.twapp.utils.Vibrator;


public class TwHightFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout hightSound;
    private LinearLayout hightshake;
    private ImageView imageVibrator;
    private TextView tvVibrator, tvSound;
    private ImageView imageSound;
    private SharedPreferencesUitl preferencesUitl;
    private Vibrator vibrator;
    private EditText etxHight, etxLow;
    private Button btn_save_set;
    private boolean isVibrator = true;
    private boolean isSound = true;


    @Override
    protected void create() {
        preferencesUitl = SharedPreferencesUitl.getInstance(getActivity(), "tw");
        PlaySound.initSoundPool(getActivity());
        vibrator = new Vibrator(getActivity());
        float hight = preferencesUitl.read("hight", 37.5f);
        float low = preferencesUitl.read("hight", 36.5f);
    }

    @Override
    protected void resuem() {

    }

    @Override
    protected void psuse() {

    }


    @Override
    protected int getViewID() {
        return R.layout.fragment_hight;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView(View conteView) {
        hightshake = conteView.findViewById(R.id.thight_shake);
        hightSound = conteView.findViewById(R.id.thight_sound);
        imageVibrator = conteView.findViewById(R.id.image_vibrator);
        imageSound = conteView.findViewById(R.id.image_sound);
        tvSound = conteView.findViewById(R.id.tv_sound);
        tvVibrator = conteView.findViewById(R.id.tv_vibrator);
        etxHight = conteView.findViewById(R.id.etx_hight);
        etxLow = conteView.findViewById(R.id.etx_low);
        btn_save_set = conteView.findViewById(R.id.btn_save_set);
        btn_save_set.setOnClickListener(this);
        hightSound.setOnClickListener(this);
        hightshake.setOnClickListener(this);

        if (preferencesUitl.read("thightShake", false)) {
            tvVibrator.setTextColor(Color.parseColor("#1497db"));
            isVibrator = false;
            imageVibrator.setBackground(getActivity().getDrawable(R.drawable.vibrator_true));
        } else {
            isVibrator = true;
            tvVibrator.setTextColor(Color.parseColor("#000000"));
            imageVibrator.setBackground(getActivity().getDrawable(R.drawable.vibrator_false));
        }
        if (preferencesUitl.read("thightSound", false)) {
            isSound = false;
            tvSound.setTextColor(Color.parseColor("#1497db"));
            imageSound.setBackground(getActivity().getDrawable(R.drawable.sound_true));
        } else {
            isVibrator = true;
            tvSound.setTextColor(Color.parseColor("#000000"));
            imageSound.setBackground(getActivity().getDrawable(R.drawable.sound_false));
        }
    }

    @Override
    protected void setListener() {

    }

    @SuppressLint("ResourceAsColor")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.thight_shake:
                if (isVibrator) {
                    isVibrator = false;
                    imageVibrator.setBackground(getActivity().getDrawable(R.drawable.vibrator_true));
                    tvVibrator.setTextColor(Color.parseColor("#1497db"));
                    vibrator.setVibrator();
                } else {
                    isVibrator = true;
                    imageVibrator.setBackground(getActivity().getDrawable(R.drawable.vibrator_false));
                    tvVibrator.setTextColor(Color.parseColor("#000000"));
                    vibrator.cancel();
                }
                break;
            case R.id.thight_sound:
                if (isSound) {
                    isSound = false;
                    imageSound.setBackground(getActivity().getDrawable(R.drawable.sound_true));
                    PlaySound.play(PlaySound.HIGHT_SOUND, PlaySound.NO_CYCLE);
                    tvSound.setTextColor(Color.parseColor("#1497db"));
                } else {
                    isSound = true;
                    imageSound.setBackground(getActivity().getDrawable(R.drawable.sound_false));
                    tvSound.setTextColor(Color.parseColor("#000000"));
                    PlaySound.stop(PlaySound.HIGHT_SOUND);
                }

                break;
            case R.id.btn_save_set:
                float hight = Float.valueOf(etxHight.getText().toString());
                float low = Float.valueOf(etxLow.getText().toString());
                preferencesUitl.write("hight", hight);
                preferencesUitl.write("hight", low);
                if (isVibrator) {
                    preferencesUitl.write("thightShake", false);
                } else {
                    preferencesUitl.write("thightShake", true);
                }
                if (isSound) {
                    preferencesUitl.write("thightSound", false);
                } else {
                    preferencesUitl.write("thightSound", true);
                }
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.main_layout_viewgroup, new HomeFragment()).commit();

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
