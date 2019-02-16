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
import space.infinity.app.model.entity.Moon;
import space.infinity.app.util.OnItemClickListener;

public class MoonAdapter extends ListAdapter<Moon, MoonAdapter.MoonViewHolder> {

    private static final DiffUtil.ItemCallback<Moon> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Moon>() {
                @Override
                public boolean areItemsTheSame(Moon oldItem, Moon newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(Moon oldItem, Moon newItem) {
                    return oldItem.toString().equals(newItem.toString());
                }
            };

    private OnItemClickListener onItemClickListener;

    public MoonAdapter(OnItemClickListener onItemClickListener) {
        super(DIFF_CALLBACK);
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MoonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cosmic_item, parent, false);
        return new MoonViewHolder(view);
    }

    public Moon getMoonAt(int position) {
        return getItem(position);
    }

    @Override
    public void onBindViewHolder(@NonNull MoonViewHolder holder, int position) {
        Moon cosmicItem = getMoonAt(position);
        String name = cosmicItem.getName();
        String image = cosmicItem.getImage();
        holder.name.setText(name);
        Glide.with(holder.itemView.getContext())
                .load(image)
                .apply(RequestOptions.centerCropTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.image);
    }

    public class MoonViewHolder extends RecyclerView.ViewHolder {

        private CardView item;
        private ImageView image;
        private TextView name;

        public MoonViewHolder(@NonNull View itemView) {
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
