package com.nutonomy.apps.inouto.introsliders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    public static final String key = "data";
    private static final int NUM_PAGES = 6;

    private SimpleGestureFilter detector;
    private int countScreens;
    private int currentScreen;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnNext;
    private WelcomeFragment welcomeFragment;
    private String[] screenValues;

    /*
    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countScreens = 6;
        currentScreen = 0;

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_welcome);

        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);

        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4,
                R.layout.welcome_slide5,
                R.layout.welcome_slide6};

        screenValues = new String[]{
                new String("{\"screen\":[{\"message\": \"\",\"resource\": \"nutonomycar\"}]}"),
                new String("{\"screen\":[{\"message\": \"Please wear your seatbelt\",\"resource\": \"seatbell\"}]}"),
                new String("{\"screen\":[{\"message\": \"Please do not take photos or record video while in the vehicle\",\"resource\": \"norecording\"}]}"),
                new String("{\"screen\":[{\"message\": \"There will be some communication between the safety driver and safety engineer. This is normal.\",\"resource\": \"communication\"}]}"),
                new String("{\"screen\":[{\"message\": \"Please do not interact with in-car personnel during the ride, they have to be 100% focused on the task of monitoring the car. The only exception to this is: If you feel uncomfortable or unsafe or want to stop the ride at any point for any reason, immediately let the in-car personnel know\",\"resource\": \"nointeraction\"}]}"),
                new String("{\"screen\":[{\"message\": \"We are about to leave do you have any questions or concerns?\",\"resource\": \"nutonomycar2\"}]}")
        };

        // adding bottom dots
    //    addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

    }
    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private LayoutInflater layoutInflater;
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
            addBottomDots(0);
        }

        @Override
        public Fragment getItem(int position) {
         //   Log.d("getItem position",position+"");
            addBottomDots(position);
            return WelcomeFragment.create(screenValues[position]);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

    }
    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }


    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];
        Log.d("addBottomDots currentPage",currentPage+"");

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(45);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length >= 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }


    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

}
