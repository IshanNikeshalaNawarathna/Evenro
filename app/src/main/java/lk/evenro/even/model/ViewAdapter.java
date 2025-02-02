package lk.evenro.even.model;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

import lk.evenro.even.AboutFragment;
import lk.evenro.even.EventFragment;
import lk.evenro.even.ReviewFragment;

public class ViewAdapter extends FragmentStateAdapter {

    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    public ViewAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AboutFragment();
            case 1:
                return new EventFragment();
            case 2:
                return new ReviewFragment();
            default:
                return new AboutFragment();
        }
    }
    @Override
    public int getItemCount() {
        return 3;
    }
}
