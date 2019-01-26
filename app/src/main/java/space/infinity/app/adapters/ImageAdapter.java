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
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import space.infinity.app.R;
import space.infinity.app.activities.FullscreenActivity;
import space.infinity.app.models.ImageItem;
import space.infinity.app.utils.Constants;

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
                    Intent intent = new Intent(context, FullscreenActivity.class);
                    intent.putExtra(Constants.IMAGE, imageItemList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
