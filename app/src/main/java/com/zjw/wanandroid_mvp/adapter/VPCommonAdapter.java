package com.zjw.wanandroid_mvp.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

public class VPCommonAdapter extends FragmentStatePagerAdapter {

    private List<SupportFragment> fragments;
    private List<String> tabNames;

    public VPCommonAdapter(@NonNull @NotNull FragmentManager fm, List<SupportFragment> fragments, List<String> tabNames) {
        super(fm);
        this.fragments = fragments;
        this.tabNames = tabNames;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames.get(position);
    }
}
