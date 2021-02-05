package com.xujian.joke.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by:      xujian
 * Version          ${version}
 * Date:            16/4/17
 * Description(描述):
 * Modification  History(历史修改):
 * Date              Author          Version
 * ---------------------------------------------------------
 * 16/4/17          xujian         ${version}
 * Why & What is modified(修改原因):
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitle;
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    public SectionsPagerAdapter(FragmentManager fm, String[] title, ArrayList<Fragment> list) {
        super(fm);
        this.mTitle = title;
        this.fragmentArrayList = list;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}
