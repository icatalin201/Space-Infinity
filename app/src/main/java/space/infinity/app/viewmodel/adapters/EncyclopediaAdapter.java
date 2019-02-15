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
import space.infinity.app.model.entity.CosmicItem;
import space.infinity.app.util.OnItemClickListener;

public class EncyclopediaAdapter
        extends ListAdapter<CosmicItem, EncyclopediaAdapter.EncyclopediaViewHolder> {

    private static final DiffUtil.ItemCallback<CosmicItem> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CosmicItem>() {
                @Override
                public boolean areItemsTheSame(CosmicItem oldItem, CosmicItem newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(CosmicItem oldItem, CosmicItem newItem) {
                    return oldItem.toString().equals(newItem.toString());
                }
            };

    private OnItemClickListener onItemClickListener;

    public EncyclopediaAdapter(OnItemClickListener onItemClickListener) {
        super(DIFF_CALLBACK);
        this.onItemClickListener = onItemClickListener;
    }

    public CosmicItem getItemAt(int position) {
        return getItem(position);
    }

    @NonNull
    @Override
    public EncyclopediaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cosmic_item, viewGroup, false);
        return new EncyclopediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EncyclopediaViewHolder encyclopediaViewHolder, int i) {
        CosmicItem cosmicItem = getItemAt(i);
        String name = cosmicItem.getName();
        String image = cosmicItem.getImage();
        encyclopediaViewHolder.name.setText(name);
        Glide.with(encyclopediaViewHolder.itemView.getContext())
                .load(image)
                .apply(RequestOptions.centerCropTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(encyclopediaViewHolder.image);
    }

    public class EncyclopediaViewHolder extends RecyclerView.ViewHolder {

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
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }

        public ImageView getImage() {
            return image;
        }
    }
}
