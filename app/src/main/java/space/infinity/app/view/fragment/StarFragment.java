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
import space.infinity.app.model.entity.Star;
import space.infinity.app.util.Constants;
import space.infinity.app.util.OnItemClickListener;
import space.infinity.app.view.activity.EncyclopediaActivity;
import space.infinity.app.viewmodel.StarViewModel;
import space.infinity.app.viewmodel.adapters.StarAdapter;

public class StarFragment extends Fragment implements OnItemClickListener {

    private LoadingDots progressBar;
    private RecyclerView recyclerView;
    private StarAdapter starAdapter;
    private FragmentActivity fragmentActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_star, container, false);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.recycler);
        starAdapter = new StarAdapter(this);
        recyclerView.setAdapter(starAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        StarViewModel starViewModel = ViewModelProviders.of(this).get(StarViewModel.class);
        starViewModel.getStars().observe(this, new Observer<List<Star>>() {
            @Override
            public void onChanged(List<Star> stars) {
                starAdapter.submitList(stars);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
        fragmentActivity = getActivity();
        return view;
    }

    @Override
    public void onItemClick(int position) {
        Star star = starAdapter.getStarAt(position);
        Intent intent = new Intent(fragmentActivity, EncyclopediaActivity.class);
        intent.putExtra(Constants.ENCYCLOPEDIA, star);
        StarAdapter.StarViewHolder starViewHolder = (StarAdapter.StarViewHolder)
                recyclerView.findViewHolderForAdapterPosition(position);
        if (starViewHolder != null) {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(fragmentActivity,
                            starViewHolder.getImage(), "image");
            startActivity(intent, activityOptionsCompat.toBundle());
        }
    }
}
