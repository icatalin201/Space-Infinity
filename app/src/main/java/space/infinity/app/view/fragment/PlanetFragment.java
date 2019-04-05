package space.infinity.app.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

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
import space.infinity.app.model.entity.Planet;
import space.infinity.app.util.Constants;
import space.infinity.app.util.OnItemClickListener;
import space.infinity.app.view.activity.EncyclopediaActivity;
import space.infinity.app.viewmodel.PlanetViewModel;
import space.infinity.app.viewmodel.adapters.PlanetAdapter;

public class PlanetFragment extends Fragment implements OnItemClickListener {

    private LoadingDots progressBar;
    private RecyclerView recyclerView;
    private PlanetAdapter planetAdapter;
    private FragmentActivity fragmentActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planet, container, false);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.recycler);
        planetAdapter = new PlanetAdapter(this);
        recyclerView.setAdapter(planetAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutAnimation(AnimationUtils
                .loadLayoutAnimation(getActivity(), R.anim.layout_animation_down));
        PlanetViewModel planetViewModel = ViewModelProviders.of(this).get(PlanetViewModel.class);
        planetViewModel.getPlanets().observe(this, new Observer<List<Planet>>() {
            @Override
            public void onChanged(List<Planet> planets) {
                planetAdapter.submitList(planets);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
        fragmentActivity = getActivity();
        return view;
    }

    @Override
    public void onItemClick(int position) {
        Planet planet = planetAdapter.getPlanetAt(position);
        Intent intent = new Intent(fragmentActivity, EncyclopediaActivity.class);
        intent.putExtra(Constants.ENCYCLOPEDIA, planet);
        PlanetAdapter.PlanetViewHolder planetViewHolder = (PlanetAdapter.PlanetViewHolder)
                recyclerView.findViewHolderForAdapterPosition(position);
        if (planetViewHolder != null) {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(fragmentActivity,
                            planetViewHolder.getImage(), "image");
            startActivity(intent, activityOptionsCompat.toBundle());
        }
    }
}
