package com.example.FunkyWords;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Copyright (c) 2013 MadeByMany. All rights reserved.
 * Project: FunkyWords
 * Package: com.example.FunkyWords
 * User: Nelson Sachse
 */
public class BaseActivity extends FragmentActivity {
    private static float MIN_SCALE = 0.85f;
    private static float MIN_ALPHA = 0.5f;

    public static final String[] mSentences = {
            "Open the champagne, while I get undressed",
            "Your elbows are the best ones I’ve seen today",
            "I like your dress. May I have it?",
            "Get on my back and we’ll go for a run"
    };

    public static final String[] mCountries = {
            "Portuguese",
            "Dutch",
            "German",
            "Norwegian"
    };

    public static final int[] mBackgroundSentences = {
            R.drawable.portugal_flag,
            R.drawable.portugal_flag,
            R.drawable.portugal_flag,
            R.drawable.portugal_flag
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        setViewsAndListeners();
    }

    /**
     * get views
     */
    protected void setViewsAndListeners() {
        ViewPager viewPagerTop = (ViewPager)findViewById(R.id.pager_top);
        viewPagerTop.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager(),mSentences, mBackgroundSentences));
//        viewPagerTop.setPageTransformer(true, new ZoomOutPageTransformer());
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        String[] sentences;
        int[] background;

        public ScreenSlidePagerAdapter(FragmentManager fm, String[] sentences, int[] background) {
            super(fm);
            this.sentences = sentences;
            this.background = background;
        }

        @Override
        public Fragment getItem(int position) {
            return new ScreenSlidePageFragment(getSentence(position), getBackground(position));
        }

        @Override
        public int getCount() {
            return 4;
        }

        public String getSentence(int position){
            return sentences[position];
        }

        public int getBackground(int position){
            return background[position];
        }
    }

    public class ScreenSlidePageFragment extends Fragment {
        String sentence;
        int background;


        public ScreenSlidePageFragment(String sentence, int background) {
            this.sentence = sentence;
            this.background = background;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);

            ((TextView)rootView.findViewById(R.id.fragment_sentence)).setText(sentence);
            ((TextView)rootView.findViewById(R.id.fragment_translation)).setText(sentence);
            ((ImageView)rootView.findViewById(R.id.flag)).setImageDrawable(getResources().getDrawable(background));

            return rootView;
        }
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
