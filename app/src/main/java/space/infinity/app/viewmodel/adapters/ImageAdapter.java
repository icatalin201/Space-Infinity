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
import space.infinity.app.model.entity.ImageItem;
import space.infinity.app.view.activity.FullscreenActivity;

/**
 * Created by icatalin on 11.02.2018.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ApodViewHolder> {

    private Context context;
    private List<ImageItem> imageItemList;

    public ImageAdapter(Context context, List<ImageItem> imageItemList) {
        this.context = context;
        this.imageItemList = imageItemList;
    }

    public void add(List<ImageItem> imageItemList) {
        this.imageItemList.addAll(imageItemList);
        notifyDataSetChanged();
    }

    public void remove() {
        this.imageItemList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ApodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_item_card,
                parent, false);
        return new ApodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApodViewHolder holder, int position) {
        ImageItem imageItem = imageItemList.get(position);
        Glide.with(context)
                .load(imageItem.getImage())
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.galleryImage);
        holder.galleryImageTitle.setText(imageItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return imageItemList.size();
    }

    class ApodViewHolder extends RecyclerView.ViewHolder {

        private CardView galleryCard;
        private ImageView galleryImage;
        private TextView galleryImageTitle;

        ApodViewHolder(View itemView) {
            super(itemView);
            galleryCard = itemView.findViewById(R.id.gallery_card);
            galleryImage = itemView.findViewById(R.id.gallery_image);
            galleryImageTitle = itemView.findViewById(R.id.gallery_image_title);
            galleryCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageItem imageItem = imageItemList.get(getAdapterPosition());
                    Intent intent = new Intent(context, FullscreenActivity.class);
                    intent.putExtra("path", imageItem.getImage());
                    intent.putExtra("hdpath", imageItem.getHdImage());
                    intent.putExtra("desc", imageItem.getDescription());
                    intent.putExtra("name", imageItem.getTitle());
                    context.startActivity(intent);
                }
            });
        }
    }
}
