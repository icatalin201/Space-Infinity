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
import space.infinity.app.model.entity.LaunchRocket;
import space.infinity.app.util.Constants;
import space.infinity.app.view.activity.RocketActivity;

public class RocketAdapter extends RecyclerView.Adapter<RocketAdapter.RocketViewHolder> {

    private Context context;
    private List<LaunchRocket> rocketList;

    public RocketAdapter(Context context, List<LaunchRocket> rocketList) {
        this.context = context;
        this.rocketList = rocketList;
    }

    public void add(List<LaunchRocket> rocketList) {
        this.rocketList.addAll(rocketList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RocketViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rocket_item, viewGroup,
                false);
        return new RocketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RocketViewHolder rocketViewHolder, int i) {
        LaunchRocket rocket = rocketList.get(i);
        Glide.with(context)
                .load(rocket.getImageURL())
                .apply(RequestOptions.centerCropTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(rocketViewHolder.image);
        rocketViewHolder.name.setText(rocket.getName());
    }

    @Override
    public int getItemCount() {
        return rocketList.size();
    }

    class RocketViewHolder extends RecyclerView.ViewHolder {

        private CardView item;
        private ImageView image;
        private TextView name;

        RocketViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, RocketActivity.class);
                    intent.putExtra(Constants.ROCKET, rocketList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
