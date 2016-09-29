package com.juziwl.activityfragmentinteractive;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2016/9/27
 * @description 应用程序Activity的模板类
 */
public class FragmentViewPagerActivity extends AppCompatActivity implements OnActivityUseFragmentMethodListener {
    private TabLayout tablayout;
    private ViewPager viewpager;
    private OnActivityUseFragmentMethodListener onActivityUseFragmentMethodListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmentviewpager);
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);

        final ArrayList<LazyLoadBaseFragment> fragments = new ArrayList<>();
        String[] strings = new String[8];
        for (int i = 0; i < 8; i++) {
            LazyLoadBaseFragment fragment = new ViewPagerFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", i);
            fragment.setArguments(bundle);
            fragments.add(fragment);
            strings[i] = "position = " + i;
        }

        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments, strings);
        viewpager.setOffscreenPageLimit(1);
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                LazyLoadBaseFragment fragment = fragments.get(position);
                if (fragment != null && fragment instanceof OnActivityUseFragmentMethodListener) {
                    ((OnActivityUseFragmentMethodListener) fragment).onActivityUseFragmentMethod("position == " + position + "\ttext == " + (Math.random() * 1000));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tablayout.setupWithViewPager(viewpager);
    }

    @Override
    public void onActivityUseFragmentMethod(String text) {
        System.out.println("FragmentViewPagerActivity  text = " + text);
    }

    class FragmentViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<LazyLoadBaseFragment> fragments;
        private String[] strings;

        public FragmentViewPagerAdapter(FragmentManager fm, ArrayList<LazyLoadBaseFragment> fragments, String[] strings) {
            super(fm);
            this.fragments = fragments;
            this.strings = strings;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return strings[position];
        }
    }
}
