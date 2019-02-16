package space.infinity.app.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eyalbira.loadingdots.LoadingDots;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.infinity.app.R;
import space.infinity.app.model.entity.Moon;
import space.infinity.app.util.Constants;
import space.infinity.app.util.OnItemClickListener;
import space.infinity.app.view.activity.EncyclopediaActivity;
import space.infinity.app.viewmodel.MoonViewModel;
import space.infinity.app.viewmodel.adapters.MoonAdapter;

public class MoonFragment extends Fragment implements OnItemClickListener {

    private LoadingDots progressBar;
    private RecyclerView recyclerView;
    private MoonAdapter moonAdapter;
    private FragmentActivity fragmentActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moon, container, false);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.recycler);
        moonAdapter = new MoonAdapter(this);
        recyclerView.setAdapter(moonAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        MoonViewModel moonViewModel = ViewModelProviders.of(this).get(MoonViewModel.class);
        moonViewModel.getMoons().observe(this, new Observer<List<Moon>>() {
            @Override
            public void onChanged(List<Moon> moons) {
                moonAdapter.submitList(moons);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
        fragmentActivity = getActivity();
        return view;
    }

    @Override
    public void onItemClick(int position) {
        Moon moon = moonAdapter.getMoonAt(position);
        Intent intent = new Intent(fragmentActivity, EncyclopediaActivity.class);
        intent.putExtra(Constants.ENCYCLOPEDIA, moon);
        MoonAdapter.MoonViewHolder moonViewHolder = (MoonAdapter.MoonViewHolder)
                recyclerView.findViewHolderForAdapterPosition(position);
        if (moonViewHolder != null) {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(fragmentActivity,
                            moonViewHolder.getImage(), "image");
            startActivity(intent, activityOptionsCompat.toBundle());
        }
    }
}
