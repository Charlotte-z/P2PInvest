package com.example.administrator.p2pinvest.activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.p2pinvest.R;
import com.example.administrator.p2pinvest.fragment.HomeFragment;
import com.example.administrator.p2pinvest.fragment.InvestFragment;
import com.example.administrator.p2pinvest.fragment.MineFragment;
import com.example.administrator.p2pinvest.fragment.MoreFragment;

public class MainActivity extends FragmentActivity {

    private RadioGroup rg;
    private FragmentTransaction ft;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String s = null;
//        if(s.equals("qs")){
//
//        }

        rg = findViewById(R.id.rg);
        rg.check(R.id.home_rb);
        setSelect(0);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.home_rb:
                        setSelect(0);
                        break;
                    case R.id.invest_rb:
                        setSelect(1);
                        break;
                    case R.id.mine_rb:
                        setSelect(2);
                        break;
                    case R.id.more_rb:
                        setSelect(3);
                        break;

                }
            }
        });
    }

    private HomeFragment homeFragment;
    private InvestFragment investFragment;
    private MineFragment mineFragment;
    private MoreFragment moreFragment;
    public void setSelect(int select) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        hideFragments();
        switch (select){
            case 0:
                if(homeFragment == null){
                    homeFragment = new HomeFragment();//创建对象以后，并不会马上调用生命周期方法，而是在commit之后才调用
                    ft.add(R.id.fl_main, homeFragment);
                }
                ft.show(homeFragment);

                break;
            case 1:
                if(investFragment == null){
                    investFragment = new InvestFragment();//创建对象以后，并不会马上调用生命周期方法，而是在commit之后才调用
                    ft.add(R.id.fl_main, investFragment);
                }
                ft.show(investFragment);
                break;
            case 2:
                if(mineFragment == null){
                    mineFragment = new MineFragment();//创建对象以后，并不会马上调用生命周期方法，而是在commit之后才调用
                    ft.add(R.id.fl_main, mineFragment);
                }
                ft.show(mineFragment);
                break;
            case 3:
                if(moreFragment == null){
                    moreFragment = new MoreFragment();//创建对象以后，并不会马上调用生命周期方法，而是在commit之后才调用
                    ft.add(R.id.fl_main, moreFragment);
                }
                ft.show(moreFragment);
                break;
        }
        ft.commit();
    }

    private void hideFragments() {
        if(homeFragment != null){
            ft.hide(homeFragment);
        }
        if(investFragment != null){
            ft.hide(investFragment);
        }
        if(mineFragment != null){
            ft.hide(mineFragment);
        }
        if(moreFragment != null){
            ft.hide(moreFragment);
        }
    }
}
