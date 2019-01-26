package space.infinity.app.adapters;

import android.content.Context;
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
import space.infinity.app.models.CosmicItem;
import space.infinity.app.models.Galaxy;
import space.infinity.app.models.Moon;
import space.infinity.app.models.Planet;
import space.infinity.app.models.Star;

public class EncyclopediaAdapter
        extends RecyclerView.Adapter<EncyclopediaAdapter.EncyclopediaViewHolder> {

    private Context context;
    private List<CosmicItem> cosmicItemList;

    public EncyclopediaAdapter(Context context, List<CosmicItem> cosmicItemList) {
        this.context = context;
        this.cosmicItemList = cosmicItemList;
    }

    public void add(List<? extends  CosmicItem> cosmicItemList) {
        this.cosmicItemList.addAll(cosmicItemList);
        notifyDataSetChanged();
    }

    public void remove() {
        this.cosmicItemList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EncyclopediaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cosmic_item, viewGroup,
                false);
        return new EncyclopediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EncyclopediaViewHolder encyclopediaViewHolder, int i) {
        CosmicItem cosmicItem = cosmicItemList.get(i);
        String type = cosmicItem.getType();
        String name = "";
        String image = "";
        switch (type) {
            case CosmicItem.CosmicType.PLANET:
                name = ((Planet) cosmicItem).getName();
                image = ((Planet) cosmicItem).getImage();
                break;
            case CosmicItem.CosmicType.MOON:
                name = ((Moon) cosmicItem).getName();
                image = ((Moon) cosmicItem).getImage();
                break;
            case CosmicItem.CosmicType.STAR:
                name = ((Star) cosmicItem).getName();
                image = ((Star) cosmicItem).getImage();
                break;
            case CosmicItem.CosmicType.GALAXY:
                name = ((Galaxy) cosmicItem).getName();
                image = ((Galaxy) cosmicItem).getImage();
                break;
        }
        encyclopediaViewHolder.name.setText(name);
        Glide.with(context)
                .load(image)
                .apply(RequestOptions.centerCropTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(encyclopediaViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return this.cosmicItemList.size();
    }

    class EncyclopediaViewHolder extends RecyclerView.ViewHolder {

        private CardView item;
        private ImageView image;
        private TextView name;

        EncyclopediaViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
