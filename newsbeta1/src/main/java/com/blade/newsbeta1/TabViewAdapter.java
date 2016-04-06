package com.blade.newsbeta1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/** FragmentStatePagerAdapter 销毁滚出屏幕的fragment 节省内存
 * Created by blade on 2016/4/3.
 */
public class TabViewAdapter extends FragmentStatePagerAdapter {
    private List<String> tabTitleList;
    private List<Fragment> fragmentList;

    public TabViewAdapter(FragmentManager fm, List<String> tabTitleList, List<Fragment> fragmentList) {
        super(fm);
        this.tabTitleList = tabTitleList;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitleList.get(position);
    }
}
