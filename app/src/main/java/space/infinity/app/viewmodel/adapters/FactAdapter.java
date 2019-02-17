package space.infinity.app.viewmodel.adapters;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import space.infinity.app.R;
import space.infinity.app.model.entity.SpaceFact;
import space.infinity.app.util.OnItemClickListener;

public class FactAdapter extends ListAdapter<SpaceFact, FactAdapter.FactViewHolder> {

    private static final DiffUtil.ItemCallback<SpaceFact> DIFF_CALLBACK =
        new DiffUtil.ItemCallback<SpaceFact>() {
            @Override
            public boolean areItemsTheSame(SpaceFact oldItem, SpaceFact newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(SpaceFact oldItem, SpaceFact newItem) {
                return oldItem.getName().equals(newItem.getName());
            }
        };

    private OnItemClickListener onItemClickListener;

    public FactAdapter(OnItemClickListener onItemClickListener) {
        super(DIFF_CALLBACK);
        this.onItemClickListener = onItemClickListener;
    }

    public SpaceFact getSpaceFactAt(int position) {
        return getItem(position);
    }

    @NonNull
    @Override
    public FactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fact_item, parent, false);
        return new FactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FactViewHolder holder, int position) {
//        DisplayMetrics metrics = holder.itemView.getContext().getResources().getDisplayMetrics();
//        int width = metrics.widthPixels;
//
//        holder.cardView.getLayoutParams().width = width * 2 / 3;
        SpaceFact spaceFact = getSpaceFactAt(position);
        holder.number.setText(String.format("Space fact #%s", spaceFact.getId()));
        holder.text.setText(spaceFact.getName());
        if (spaceFact.isFavorite()) {
            holder.favorite.setImageResource(R.drawable.ic_baseline_favorite_24px);
        } else {
            holder.favorite.setImageResource(R.drawable.ic_baseline_favorite_border_24px);
        }
    }

    class FactViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView number;
        private TextView text;
        private ImageButton favorite;
        private ImageButton share;

        FactViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.number);
            cardView = itemView.findViewById(R.id.card);
            text = itemView.findViewById(R.id.text);
            favorite = itemView.findViewById(R.id.favorite);
            share = itemView.findViewById(R.id.share);
            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SpaceFact spaceFact = getSpaceFactAt(getAdapterPosition());
                    if (spaceFact.isFavorite()) {
                        favorite.setImageResource(R.drawable.ic_baseline_favorite_border_24px);
                    } else {
                        favorite.setImageResource(R.drawable.ic_baseline_favorite_24px);
                    }
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String packageName = view.getContext().getPackageName();
                    Intent appShareIntent = new Intent(Intent.ACTION_SEND);
                    appShareIntent.setType("text/plain");
                    SpaceFact spaceFact = getSpaceFactAt(getAdapterPosition());
                    String extraText = spaceFact.getName().concat("\n");
                    extraText += "See more. Download the app!\n";
                    extraText += "https://play.google.com/store/apps/details?id=" + packageName;
                    appShareIntent.putExtra(Intent.EXTRA_TEXT, extraText);
                    view.getContext().startActivity(appShareIntent);
                }
            });
        }
    }
}
