package com.s22010334.finalproject.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.s22010334.finalproject.fragments.CareFragment;
import com.s22010334.finalproject.fragments.Homefragment;
import com.s22010334.finalproject.fragments.CartFragment;
import com.s22010334.finalproject.fragments.ProfileFragment;

public class ViewPageAdapter extends FragmentStateAdapter {
    public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new Homefragment();
            case 1: return new CareFragment();
            case 2: return new CartFragment();
            case 3: return new ProfileFragment();
            default: return new Homefragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
