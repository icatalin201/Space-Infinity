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
import space.infinity.app.model.entity.Galaxy;
import space.infinity.app.util.Constants;
import space.infinity.app.util.OnItemClickListener;
import space.infinity.app.view.activity.EncyclopediaActivity;
import space.infinity.app.viewmodel.GalaxyViewModel;
import space.infinity.app.viewmodel.adapters.GalaxyAdapter;

public class GalaxyFragment extends Fragment implements OnItemClickListener {

    private LoadingDots progressBar;
    private RecyclerView recyclerView;
    private GalaxyAdapter galaxyAdapter;
    private FragmentActivity fragmentActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_galaxy, container, false);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.recycler);
        galaxyAdapter = new GalaxyAdapter(this);
        recyclerView.setAdapter(galaxyAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        GalaxyViewModel galaxyViewModel = ViewModelProviders.of(this).get(GalaxyViewModel.class);
        galaxyViewModel.getGalaxies().observe(this, new Observer<List<Galaxy>>() {
            @Override
            public void onChanged(List<Galaxy> galaxies) {
                galaxyAdapter.submitList(galaxies);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
        fragmentActivity = getActivity();
        return view;
    }

    @Override
    public void onItemClick(int position) {
        Galaxy galaxy = galaxyAdapter.getGalaxyAt(position);
        Intent intent = new Intent(fragmentActivity, EncyclopediaActivity.class);
        intent.putExtra(Constants.ENCYCLOPEDIA, galaxy);
        GalaxyAdapter.GalaxyViewHolder galaxyViewHolder = (GalaxyAdapter.GalaxyViewHolder)
                recyclerView.findViewHolderForAdapterPosition(position);
        if (galaxyViewHolder != null) {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(fragmentActivity,
                            galaxyViewHolder.getImage(), "image");
            startActivity(intent, activityOptionsCompat.toBundle());
        }
    }
}
