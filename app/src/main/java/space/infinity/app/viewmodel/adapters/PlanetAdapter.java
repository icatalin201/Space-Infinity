package space.infinity.app.viewmodel.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import space.infinity.app.R;
import space.infinity.app.model.entity.Planet;
import space.infinity.app.util.OnItemClickListener;

public class PlanetAdapter extends ListAdapter<Planet, PlanetAdapter.PlanetViewHolder> {

    private static final DiffUtil.ItemCallback<Planet> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Planet>() {
                @Override
                public boolean areItemsTheSame(Planet oldItem, Planet newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(Planet oldItem, Planet newItem) {
                    return oldItem.toString().equals(newItem.toString());
                }
            };

    private OnItemClickListener onItemClickListener;

    public PlanetAdapter(OnItemClickListener onItemClickListener) {
        super(DIFF_CALLBACK);
        this.onItemClickListener = onItemClickListener;
    }

    public Planet getPlanetAt(int position) {
        return getItem(position);
    }

    @NonNull
    @Override
    public PlanetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cosmic_item, parent, false);
        return new PlanetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanetViewHolder holder, int position) {
        Planet cosmicItem = getPlanetAt(position);
        String name = cosmicItem.getName();
        String image = cosmicItem.getImage();
        holder.name.setText(name);
        Glide.with(holder.itemView.getContext())
                .load(image)
                .apply(RequestOptions.centerCropTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.image);
    }

    public class PlanetViewHolder extends RecyclerView.ViewHolder {

        private CardView item;
        private ImageView image;
        private TextView name;

        public PlanetViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }

        public ImageView getImage() {
            return image;
        }
    }
}
