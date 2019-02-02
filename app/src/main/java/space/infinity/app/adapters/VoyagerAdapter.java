package space.infinity.app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import space.infinity.app.R;
import space.infinity.app.models.Voyager;

public class VoyagerAdapter extends RecyclerView.Adapter<VoyagerAdapter.VoyagerViewHolder> {

    private Context context;
    private List<Voyager> voyagerList;

    public VoyagerAdapter(Context context, List<Voyager> voyagerList) {
        this.context = context;
        this.voyagerList = voyagerList;
    }

    public void add(List<Voyager> voyagers) {
        this.voyagerList.addAll(voyagers);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VoyagerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.voyager_item, viewGroup, false);
        return new VoyagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoyagerViewHolder voyagerViewHolder, int i) {
        Voyager voyager = voyagerList.get(i);
        Glide.with(context)
                .load(voyager.getImage())
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.centerCropTransform())
                .into(voyagerViewHolder.image);
        voyagerViewHolder.name.setText(voyager.getName());
        voyagerViewHolder.distanceFromSun.setText("Distance from Sun\n"
                .concat(voyager.getDistanceFromSunKM()));
        voyagerViewHolder.distanceFromEarth.setText("Distance from Earth\n"
                .concat(voyager.getDistanceFromEarthKM()));
        voyagerViewHolder.velocity.setText("Velocity\n".concat(voyager.getVelocity()));
        voyagerViewHolder.oneWayLightTime.setText("One Way Light Time\n"
                .concat(voyager.getOneWayLightTime()));
        voyagerViewHolder.date.setText(String.format("Launched on %s", voyager.getLaunchDate()));
    }

    @Override
    public int getItemCount() {
        return voyagerList.size();
    }

    class VoyagerViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView distanceFromSun;
        private TextView distanceFromEarth;
        private TextView velocity;
        private TextView oneWayLightTime;
        private TextView date;
        private ImageView image;

        VoyagerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            distanceFromEarth = itemView.findViewById(R.id.distance_from_earth);
            distanceFromSun = itemView.findViewById(R.id.distance_from_sun);
            velocity = itemView.findViewById(R.id.velocity);
            oneWayLightTime = itemView.findViewById(R.id.one_way_light_time);
            date = itemView.findViewById(R.id.date);
            image = itemView.findViewById(R.id.image);
        }
    }
}
