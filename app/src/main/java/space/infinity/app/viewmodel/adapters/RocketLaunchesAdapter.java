package space.infinity.app.viewmodel.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import space.infinity.app.R;
import space.infinity.app.model.entity.Launch;
import space.infinity.app.model.entity.LaunchAgency;
import space.infinity.app.model.entity.LaunchLocation;
import space.infinity.app.model.entity.LaunchPad;
import space.infinity.app.model.entity.LaunchRocket;
import space.infinity.app.util.Constants;
import space.infinity.app.view.activity.LaunchActivity;

public class RocketLaunchesAdapter
        extends RecyclerView.Adapter<RocketLaunchesAdapter.RocketLaunchesViewHolder> {

    private Context context;
    private List<Launch> launchList;

    public RocketLaunchesAdapter(Context context, List<Launch> launches) {
        this.context = context;
        this.launchList = launches;
    }

    public void add(List<Launch> launches) {
        this.launchList.addAll(launches);
        notifyDataSetChanged();

    }

    public void remove() {
        this.launchList.clear();
        notifyDataSetChanged();
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
        LaunchRocket launchRocket = launch.getRocket();
        LaunchLocation launchLocation = launch.getLocation();
        String image = launchRocket.getImageURL();
        String agency = "";
        String location = "";
        if (launchLocation.getPads() != null && launchLocation.getPads().size() > 0) {
            LaunchPad launchPad = launchLocation.getPads().get(0);
            if (launchPad.getAgencies() != null && launchPad.getAgencies().size() > 0) {
                LaunchAgency launchAgency = launchLocation.getPads().get(0).getAgencies().get(0);
                agency = launchAgency.getName();
            }
            location = launchLocation.getPads().get(0).getName();
        }
        Glide.with(context)
                .load(image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.centerCropTransform())
                .into(rocketLaunchesViewHolder.imageView);
        rocketLaunchesViewHolder.name.setText(launch.getName());
        rocketLaunchesViewHolder.agency.setText(agency);
        rocketLaunchesViewHolder.location.setText(location);
        rocketLaunchesViewHolder.windowStart.setText(launch.getWindowstart());
    }

    @Override
    public int getItemCount() {
        return launchList.size();
    }

    class RocketLaunchesViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView imageView;
        private TextView name;
        private TextView agency;
        private TextView location;
        private TextView windowStart;

        RocketLaunchesViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.item);
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            agency = itemView.findViewById(R.id.agency);
            location = itemView.findViewById(R.id.location);
            windowStart = itemView.findViewById(R.id.windowstart);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, LaunchActivity.class);
                    intent.putExtra(Constants.LAUNCH, launchList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
