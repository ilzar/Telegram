// Copyright (c) 2015 Yandex LLC. All rights reserved.
// Author: Oleg Isupov <cypt4@yandex-team.ru>

package org.telegram.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.BaseFragment;

public class FenegramWallpaperActivity extends BaseFragment {

    public static int[] resources = new int[]{R.drawable.bag0, R.drawable.bag1,R.drawable.bag2, R.drawable.bag3,
                                              R.drawable.bag4, R.drawable.bag5,R.drawable.bag6, R.drawable.bag7,
                                              R.drawable.bag8};
    private int selectedResource;

    @Override
    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        swipeBackEnabled = false;
        return true;
    }

    @Override
    public View createView(final Context context) {
        actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        actionBar.setAllowOverlayTitle(true);
        actionBar.setTitle(LocaleController.getString("ChatBackground", R.string.ChatBackground));

        final FrameLayout rootLayout = new FrameLayout(context);
        fragmentView = rootLayout;
        final ViewPager viewPager = new ViewPager(context);
        final TextView textView = new TextView(context);
        final FrameLayout.LayoutParams params =
            new FrameLayout.LayoutParams(AndroidUtilities.dp(100), AndroidUtilities.dp(30));
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        params.bottomMargin = AndroidUtilities.dp(20);
        textView.setAlpha(0.5f);
        textView.setGravity(Gravity.CENTER);
        textView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        textView.setBackgroundColor(Color.GRAY);
        textView.setTextColor(Color.BLACK);

        rootLayout.addView(viewPager,
                            new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                         ViewGroup.LayoutParams.MATCH_PARENT));
        rootLayout.addView(textView, params);

        selectedResource = context.getSharedPreferences("YandexPreferences", Context.MODE_PRIVATE)
                                  .getInt("wallpaper", 0);

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return resources.length;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                final ImageView imageView = new ImageView(context);
                imageView.setBackgroundResource(resources[position]);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(final ViewGroup container,
                                    final int position,
                                    final Object object) {
                container.removeView((View) object);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position,
                                       final float positionOffset,
                                       final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                selectedResource = position;
                updateText(textView);
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        viewPager.setCurrentItem(selectedResource);

        ActionBarMenu menu = actionBar.createMenu();
        menu.addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56));
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(final int id) {
                if (id == -1) {
                    finishFragment();
                } else {
                    context.getSharedPreferences("YandexPreferences", Context.MODE_PRIVATE).edit()
                           .putInt("wallpaper", viewPager.getCurrentItem()).apply();
                    ApplicationLoader.reloadWallpaper();
                    FenegramWallpaperActivity.this.finishFragment();
                }
            }
        });

        updateText(textView);

        return rootLayout;
    }

    @Override
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
    }

    private void updateText(final TextView textView) {
        textView.setText((selectedResource + 1) + "/" + resources.length);
    }
}
