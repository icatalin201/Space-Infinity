package space.infinity.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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
import space.infinity.app.activities.LaunchSiteActivity;
import space.infinity.app.models.LaunchSite;
import space.infinity.app.utils.Constants;

public class LaunchSitesAdapter extends RecyclerView.Adapter<LaunchSitesAdapter.LaunchSiteViewHolder> {

    private Context context;
    private List<LaunchSite> launchSiteList;

    public LaunchSitesAdapter(Context context, List<LaunchSite> launchSiteList) {
        this.context = context;
        this.launchSiteList = launchSiteList;
    }

    public void add(List<LaunchSite> launchSites) {
        this.launchSiteList.addAll(launchSites);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LaunchSiteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.launch_site_item,
                viewGroup, false);
        return new LaunchSiteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaunchSiteViewHolder launchSiteViewHolder, int i) {
        LaunchSite launchSite = launchSiteList.get(i);
        launchSiteViewHolder.name.setText(launchSite.getName());
        Glide.with(context)
                .load(launchSite.getImage())
                .apply(RequestOptions.centerCropTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(launchSiteViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return launchSiteList.size();
    }

    class LaunchSiteViewHolder extends RecyclerView.ViewHolder {

        private CardView item;
        private ImageView image;
        private TextView name;

        LaunchSiteViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, LaunchSiteActivity.class);
                    intent.putExtra(Constants.LAUNCH_SITE, launchSiteList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
