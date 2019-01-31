package space.infinity.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
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
import space.infinity.app.activities.RocketActivity;
import space.infinity.app.models.Rocket;
import space.infinity.app.utils.Constants;

public class RocketAdapter extends RecyclerView.Adapter<RocketAdapter.RocketViewHolder> {

    private Context context;
    private List<Rocket> rocketList;

    public RocketAdapter(Context context, List<Rocket> rocketList) {
        this.context = context;
        this.rocketList = rocketList;
    }

    public void add(List<Rocket> rocketList) {
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
        Rocket rocket = rocketList.get(i);
        Glide.with(context)
                .load(rocket.getImage())
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
