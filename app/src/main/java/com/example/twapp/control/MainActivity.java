package com.example.twapp.control;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twapp.R;
import com.example.twapp.base.BaseActivity;
import com.example.twapp.fragment.AboutUsFragment;
import com.example.twapp.fragment.ActivateFragment;
import com.example.twapp.fragment.HistoFragment;
import com.example.twapp.fragment.HomeFragment;
import com.example.twapp.fragment.LoginFragment;
import com.example.twapp.fragment.TwHightFragment;
import com.example.twapp.lib3.slidingmenu.SlidingMenu;
import com.example.twapp.updateversion.UpdateVersion;
import com.example.twapp.utils.SharedPreferencesUitl;

public class MainActivity extends BaseActivity implements OnClickListener {
    public LoginFragment logoFragment;
    public TwHightFragment thightFragment;
    public AboutUsFragment aboutUsFragment;
    public HomeFragment homeFragment;
    public HistoFragment histoFragment;
    public ActivateFragment activateFragment;
    // 左侧5布局
    private RelativeLayout Alar, updata, aboutus, rllogin, history, activate;
    // 标题
    private ImageView leftTitle;
    private TextView mainTitle;
    private SlidingMenu slidingMenu;
    // 登录 的图片和文本
    private ImageView logImagview;
    private TextView logMsage;
    private SharedPreferencesUitl sharedPreferencesUitl;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSlidng();
        initview();
        initFragment();
        changeFragment(homeFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        changeFragment(homeFragment);
    }

    /**
     * 初始化侧拉
     */
    private void initSlidng() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        // 全屏都可以滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.LEFT);
        // 可以滑出100单位
        slidingMenu.setBehindOffsetRes(R.dimen.sl);
        // 在当前的activity中滑动
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        // 左侧的布局
        slidingMenu.setMenu(R.layout.activity_main_left);

    }

    /**
     * 打开或关闭 sliding
     */
    private void isCloseslidingMenu() {
        if (slidingMenu != null && slidingMenu.isMenuShowing()) {
            slidingMenu.showContent();
        } else if (slidingMenu != null) {
            // 否则打开 左侧的布局
            slidingMenu.showMenu();
        }
    }

    private void initFragment() {
        logoFragment = new LoginFragment();
        thightFragment = new TwHightFragment();
        aboutUsFragment = new AboutUsFragment();
        homeFragment = new HomeFragment();
        histoFragment = new HistoFragment();
        activateFragment = new ActivateFragment();
    }

    /**
     * 切换的Fragment
     *
     * @param f
     */
    public void changeFragment(Fragment f) {
        isLogoOk();
        //关闭
        if (slidingMenu != null && slidingMenu.isMenuShowing()) {
            slidingMenu.showContent();
        }

        getSupportFragmentManager().beginTransaction().
                replace(R.id.main_layout_viewgroup, f).commit();

    }


    private void initview() {
        // 左标题
        leftTitle = findViewById(R.id.main_title_left);
        mainTitle = findViewById(R.id.main_title_text);
        // 左
        Alar = slidingMenu.findViewById(R.id.rl_alarm);
        updata = slidingMenu.findViewById(R.id.rl_updata);
        aboutus = slidingMenu.findViewById(R.id.rl_aboutus);
        logImagview = slidingMenu.findViewById(R.id.frg_right_icon);
        logMsage = slidingMenu.findViewById(R.id.frg_right_logo);
        history = slidingMenu.findViewById(R.id.rl_history);
        rllogin = slidingMenu.findViewById(R.id.relativelayout_unlogin);
        activate = slidingMenu.findViewById(R.id.rl_actvite);
        slidingMenu.findViewById(R.id.test_act).setOnClickListener(this);//测试按钮
        TextView tvVersion = findViewById(R.id.tv_Version);
        tvVersion.setText("版本号：" + getVersion());
        activate.setOnClickListener(this);
        rllogin.setOnClickListener(this);
        Alar.setOnClickListener(this);
        updata.setOnClickListener(this);
        aboutus.setOnClickListener(this);
        leftTitle.setOnClickListener(this);
        logImagview.setOnClickListener(this);
        logMsage.setOnClickListener(this);
        history.setOnClickListener(this);
        sharedPreferencesUitl = SharedPreferencesUitl.getInstance(this, "tw");
        isLogoOk();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isLogoOk();
    }

    @Override

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }

    /**
     * 是否登录成功
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void isLogoOk() {
        //取出所有的用户信息
        String uname = sharedPreferencesUitl.read("name", "");
        String upwd = sharedPreferencesUitl.read("upwd", "");
        if (!"".equals(uname) && !"".equals(upwd)) {
            //更改 UI   textview --用户名
            logMsage.setText(uname);
            logImagview.setImageDrawable(getDrawable(R.drawable.fun_share_weixin));
        }

    }

    /**
     * 注销用户
     */
    public void clearUser() {
        sharedPreferencesUitl.read("name", "");
        sharedPreferencesUitl.read("upwd", "");
        logMsage.setText("立刻登陆");
        logImagview.setImageResource(R.drawable.login);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        Alar.setBackgroundColor(0);
        updata.setBackgroundColor(0);
        aboutus.setBackgroundColor(0);
        history.setBackgroundColor(0);
        activate.setBackgroundColor(0);
        switch (v.getId()) {
            case R.id.rl_actvite:
                activate.setBackgroundColor(R.color.huise);
                mainTitle.setText("绑定标签");
                changeFragment(activateFragment);
                break;
            case R.id.rl_alarm://高温报警
                Alar.setBackgroundColor(R.color.huise);
                mainTitle.setText("高温报警");
                changeFragment(thightFragment);
                break;
            case R.id.rl_history://历史记录
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                history.setBackgroundColor(R.color.huise);
                mainTitle.setText("历史记录");
                changeFragment(histoFragment);
//                Intent intents = new Intent(MainActivity.this, HistoryActivity.class);
//                startActivity(intents);
                break;
            case R.id.rl_updata://软件更新
                updata.setBackgroundColor(R.color.huise);
//                mainTitle.setText("软件更新");
                UpdateVersion updateVersion = new UpdateVersion(this);
                updateVersion.startUpdate();
                isCloseslidingMenu();
                break;
            case R.id.rl_aboutus://关于我们
                aboutus.setBackgroundColor(R.color.huise);
                mainTitle.setText("关于我们");
                changeFragment(aboutUsFragment);
                break;
            case R.id.main_title_left://左按钮
                isCloseslidingMenu();
                break;
            case R.id.relativelayout_unlogin://登录
                if (!"立刻登陆".equals(logMsage.getText().toString())) {
                    Intent intent = new Intent(MainActivity.this, UserActivity.class);
                    startActivityForResult(intent, 101);
                } else {
                    mainTitle.setText("用户登录");
                    changeFragment(logoFragment);
                }
                break;
            case R.id.test_act:
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
        super.onActivityResult(arg0, arg1, arg2);
        if (arg0 == 101 && arg1 == 250) {
            clearUser();
            //isLogoOk();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //左侧拉 是否打开
            if (slidingMenu != null && slidingMenu.isMenuShowing()) {
                slidingMenu.showContent();//关闭
                return true;
            }
            //当前Activity  fra 是哪个
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.main_layout_viewgroup);
            if (f instanceof HomeFragment) {
                exit();
                return true;
            } else {
                changeFragment(homeFragment);
                mainTitle.setText("体温管家");
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 双击back退出
     */
    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出体温管家", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }


}
