package space.infinity.app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import space.infinity.app.R;
import space.infinity.app.models.Launch;

public class RocketLaunchesAdapter extends RecyclerView.Adapter<RocketLaunchesAdapter.RocketLaunchesViewHolder> {

    private Context context;
    private List<Launch> launchList;

    public RocketLaunchesAdapter(Context context, List<Launch> launches) {
        this.context = context;
        this.launchList = launches;
    }

    public void add(List<Launch> launches) {
        this.launchList.addAll(launches);
    }

    @NonNull
    @Override
    public RocketLaunchesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.launch_item, viewGroup,
                false);
        return new RocketLaunchesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RocketLaunchesViewHolder rocketLaunchesViewHolder, int i) {
        Launch launch = launchList.get(i);
    }

    @Override
    public int getItemCount() {
        return launchList.size();
    }

    class RocketLaunchesViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        RocketLaunchesViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
