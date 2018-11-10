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

import java.util.List;

import space.infinity.app.R;
import space.infinity.app.activities.Encyclopedia;
import space.infinity.app.models.encyclopedia.Planet;
import space.infinity.app.utils.Helper;

/**
 * Created by icatalin on 18.02.2018.
 */

public class PlanetAdapter extends RecyclerView.Adapter<PlanetAdapter.PlanetViewHolder> {

    private Context context;
    private List<Planet> planetList;

    public PlanetAdapter(Context context, List<Planet> planetList) {
        this.context = context;
        this.planetList = planetList;
    }

    @NonNull
    @Override
    public PlanetAdapter.PlanetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.choose_enc_item, parent, false);
        return new PlanetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanetAdapter.PlanetViewHolder holder, int position) {
        holder.name.setText(planetList.get(position).getName());
        String imageName = planetList.get(position).getImage().split("\\.")[0];
        int resID = context.getResources().getIdentifier(imageName , "drawable", context.getPackageName());
        Glide.with(context).load(resID).transition(DrawableTransitionOptions.withCrossFade()).into(holder.image);
        Helper.customAnimation(context, holder.card, 700, android.R.anim.fade_in);
    }

    @Override
    public int getItemCount() {
        return planetList.size();
    }

    class PlanetViewHolder extends RecyclerView.ViewHolder {

        private CardView card;
        private ImageView image;
        private TextView name;

        PlanetViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Encyclopedia.class);
                    intent.putExtra("object", planetList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
