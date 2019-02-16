package space.infinity.app.viewmodel.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import space.infinity.app.R;
import space.infinity.app.model.entity.Astronaut;
import space.infinity.app.util.OnItemClickListener;

public class AstronautAdapter extends ListAdapter<Astronaut, AstronautAdapter.AstronautsViewHolder> {

    private static final DiffUtil.ItemCallback<Astronaut> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Astronaut>() {
        @Override
        public boolean areItemsTheSame(Astronaut oldItem, Astronaut newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Astronaut oldItem, Astronaut newItem) {
            return oldItem.toString().equals(newItem.toString());
        }
    };

    private OnItemClickListener onItemClickListener;

    public AstronautAdapter(OnItemClickListener onItemClickListener) {
        super(DIFF_CALLBACK);
        this.onItemClickListener = onItemClickListener;
    }

    public Astronaut getAstronautAt(int position) {
        return getItem(position);
    }

    @NonNull
    @Override
    public AstronautsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.astronaut_item, viewGroup, false);
        return new AstronautsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AstronautsViewHolder astronautsViewHolder, int i) {
        Astronaut astronaut = getAstronautAt(i);
        Glide.with(astronautsViewHolder.itemView.getContext())
            .load(astronaut.getImage())
            .apply(RequestOptions.circleCropTransform())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(astronautsViewHolder.image);
    }

    public class AstronautsViewHolder extends RecyclerView.ViewHolder {

        private CardView item;
        private ImageView image;

        AstronautsViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            image = itemView.findViewById(R.id.image);
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
