// Copyright (c) 2015 Yandex LLC. All rights reserved.
// Author: Oleg Isupov <cypt4@yandex-team.ru>

package org.telegram.yi;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class CardPagerAdapter extends PagerAdapter {

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final FrameLayout frameLayout = new FrameLayout(container.getContext());
        frameLayout.setBackgroundColor(position == 0 ? Color.YELLOW : Color.RED);
        container.addView(frameLayout, position);
        return frameLayout;
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        super.destroyItem(container, position, object);
        container.removeView((View)object);
    }
}
