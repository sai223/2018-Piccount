package com.example.intae.client;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the reference of FrameLayout and TabLayout
        frameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);
        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);

        // 내역보기 탭 생성
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("내역보기");
        firstTab.setIcon(R.drawable.tab1_see_records);

        // 내역추가 탭 생성
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("내역추가");
        secondTab.setIcon(R.drawable.tab2_add_records);

        // 리포트 탭 생성
        TabLayout.Tab thirdTab = tabLayout.newTab();
        thirdTab.setText("리포트");
        thirdTab.setIcon(R.drawable.tab3_report);

        // 환경설정 탭 생성
        TabLayout.Tab fourthTab = tabLayout.newTab();
        fourthTab.setText("설정");
        fourthTab.setIcon(R.drawable.tab4_setting);

        // 생성한 각 탭을 레이아웃에 추가
        tabLayout.addTab(firstTab);
        tabLayout.addTab(secondTab);
        tabLayout.addTab(thirdTab);
        tabLayout.addTab(fourthTab);

        // TabLayout의 각 탭을 누를 때 발생하는 Event 설정
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 선택된 탭으로 화면을 바꾼다.
                Fragment fragment = null;
                switch(tab.getPosition()){
                    case 0:
                        fragment = new FirstFragment();
                        break;
                    case 1:
                        fragment = new SecondFragment();
                        break;
                    case 2:
                        fragment = new ThirdFragment();
                        break;
                    case 3:
                        fragment = new FourthFragment();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // 딱히 기능 없음
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // 딱히 기능 없음
            }
        });
    }
}
