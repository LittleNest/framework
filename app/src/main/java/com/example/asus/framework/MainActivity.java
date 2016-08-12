package com.example.asus.framework;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


     /*

    默认显示第一个fragment
    点击不同的按钮，主界面显示不同的fragment
     */


    int oldOperFlag;//前一次操作的是哪个按钮
    int currentOperFlag;//现在操作的是哪个按钮

    TextView titleview;//标题




    List<Fragment> fragmentList=new ArrayList<Fragment>();
    List<Button> buttonList=new ArrayList<Button>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 隐藏actionbar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        findViewById(R.id.show_top).setVisibility(View.GONE);
        findViewById(R.id.show_topmain).setVisibility(View.VISIBLE);

        //标题
        titleview= (TextView) findViewById(R.id.toptitle);


        //图片按钮
        ImageButton btn_position;
        ImageButton btn_fenlei;
        ImageButton btn_discover_top;


        btn_fenlei= (ImageButton) findViewById(R.id.btn_fenlei);
        btn_position= (ImageButton) findViewById(R.id.btn_position);
        btn_discover_top= (ImageButton) findViewById(R.id.btn_discover_top);

        //图片按钮点击事件
        btn_fenlei.setOnClickListener(this);
        btn_position.setOnClickListener(this);
        btn_discover_top.setOnClickListener(this);

        //集合初始化
        fragmentList.add(new MainPageFragment());
        fragmentList.add(new DiscoverFragment());
        fragmentList.add(new MessageFragment());
        fragmentList.add(new PersonCenterFragment());

        //按钮集合
        buttonList.add((Button)findViewById(R.id.btn_main_page));
        buttonList.add((Button)findViewById(R.id.btn_discover));
        buttonList.add((Button) findViewById(R.id.btn_message_cart));
        buttonList.add((Button)findViewById(R.id.btn_person_center));



        //默认显示第一个fragment
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,fragmentList.get(0))
                .commit();

        Button btnPage= (Button) findViewById(R.id.btn_main_page);
        //默认第一个按钮被选中
        btnPage.setSelected(true);

    }

    //按钮点击事件：点击按钮，对应的fragmetn显示，其余的fragmetn隐藏
    public void onTabClicked(View v){
        switch (v.getId()){
            case R.id.btn_main_page:
                currentOperFlag=0;//第一个按钮
                findViewById(R.id.show_top).setVisibility(View.GONE);
                findViewById(R.id.show_topmain).setVisibility(View.VISIBLE);
                break;
            case R.id.btn_discover:
                currentOperFlag=1;
                findViewById(R.id.show_top).setVisibility(View.VISIBLE);
                findViewById(R.id.show_topmain).setVisibility(View.GONE);
                findViewById(R.id.btn_discover_top).setVisibility(View.VISIBLE);
                titleview.setText("发现");
                break;
            case R.id.btn_message_cart:
                currentOperFlag=2;
                titleview.setText("消息");
                findViewById(R.id.show_top).setVisibility(View.VISIBLE);
                findViewById(R.id.show_topmain).setVisibility(View.GONE);
                findViewById(R.id.btn_discover_top).setVisibility(View.GONE);
                break;
            case R.id.btn_person_center:
                currentOperFlag=3;
                titleview.setText("我的");
                findViewById(R.id.show_top).setVisibility(View.VISIBLE);
                findViewById(R.id.show_topmain).setVisibility(View.GONE);
                findViewById(R.id.btn_discover_top).setVisibility(View.GONE);
                break;
        }

        //实现旧的隐藏，新的显示
        switchFragment(fragmentList.get(oldOperFlag),fragmentList.get(currentOperFlag));

        buttonList.get(oldOperFlag).setSelected(false);//上一次点击的按钮，取消select

        buttonList.get(currentOperFlag).setSelected(true);//当前点击的按钮，设置select
        Log.i("MainActivity","oldOperFlag:"+oldOperFlag+",currentOperFlag:"+currentOperFlag);

        //当前的操作，变成上一次的操作
        oldOperFlag=currentOperFlag;

}
    //隐藏当前显示的fragment,新的fragment:1)没有添加过，添加；2）添加过，显示
    public void switchFragment(Fragment oldFragment, Fragment newFragment){

        //判断是否是同一个fragment
        if(oldFragment!=newFragment) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            //隐藏oldfragment
            fragmentTransaction.hide(oldFragment);

            if (!newFragment.isAdded()) {
                //没有添加过，添加
                fragmentTransaction.add(R.id.fragment_container, newFragment).commit();

            } else {
                fragmentTransaction.show(newFragment).commit();
            }
        }

    }

    @Override
    public void onClick(View v) {

        //图片按钮
        switch (v.getId()) {
            case R.id.btn_fenlei:
                Toast.makeText(MainActivity.this, "分类", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_position:
                Toast.makeText(MainActivity.this, "定位", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_discover_top:
                Toast.makeText(MainActivity.this, "发布", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}