package com.hugoguillin.installertoolbox;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int pestanas;

    public PagerAdapter(FragmentManager fm, int pestanas) {
        super(fm);
        this.pestanas = pestanas;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0: return new Acristalamiento();
            case 1: return new Carp_metalica();
            case 2: return new Carp_pvc();
            case 3: return new Persianas();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return pestanas;
    }
}
