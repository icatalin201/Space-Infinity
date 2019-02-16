package space.infinity.app.viewmodel.adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import space.infinity.app.R;
import space.infinity.app.view.fragment.GalaxyFragment;
import space.infinity.app.view.fragment.MoonFragment;
import space.infinity.app.view.fragment.PlanetFragment;
import space.infinity.app.view.fragment.StarFragment;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PlanetFragment();
        } else if (position == 1){
            return new MoonFragment();
        } else if (position == 2){
            return new StarFragment();
        } else {
            return new GalaxyFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.planets);
            case 1:
                return mContext.getString(R.string.moons);
            case 2:
                return mContext.getString(R.string.stars);
            case 3:
                return mContext.getString(R.string.galaxies);
            default:
                return null;
        }
    }

}
