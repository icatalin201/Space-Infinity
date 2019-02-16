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
import space.infinity.app.model.entity.Star;
import space.infinity.app.util.OnItemClickListener;

public class StarAdapter extends ListAdapter<Star, StarAdapter.StarViewHolder> {

    private static final DiffUtil.ItemCallback<Star> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Star>() {
                @Override
                public boolean areItemsTheSame(Star oldItem, Star newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(Star oldItem, Star newItem) {
                    return oldItem.toString().equals(newItem.toString());
                }
            };

    private OnItemClickListener onItemClickListener;

    public StarAdapter(OnItemClickListener onItemClickListener) {
        super(DIFF_CALLBACK);
        this.onItemClickListener = onItemClickListener;
    }

    public Star getStarAt(int position) {
        return getItem(position);
    }

    @NonNull
    @Override
    public StarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cosmic_item, parent, false);
        return new StarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StarViewHolder holder, int position) {
        Star cosmicItem = getStarAt(position);
        String name = cosmicItem.getName();
        String image = cosmicItem.getImage();
        holder.name.setText(name);
        Glide.with(holder.itemView.getContext())
                .load(image)
                .apply(RequestOptions.centerCropTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.image);
    }

    public class StarViewHolder extends RecyclerView.ViewHolder {

        private CardView item;
        private ImageView image;
        private TextView name;

        public StarViewHolder(@NonNull View itemView) {
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
