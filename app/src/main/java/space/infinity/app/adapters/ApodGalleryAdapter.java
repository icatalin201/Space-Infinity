package space.infinity.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import space.infinity.app.R;
import space.infinity.app.activities.FullscreenActivity;
import space.infinity.app.models.apod.APOD;
import space.infinity.app.utils.Helper;

/**
 * Created by icatalin on 11.02.2018.
 */

public class ApodGalleryAdapter extends RecyclerView.Adapter<ApodGalleryAdapter.ApodViewHolder> {

    private Context context;
    private List<APOD> imageDataList;
    private int lastPosition = -1;

    public ApodGalleryAdapter(Context context, List<APOD> imageDataList) {
        this.context = context;
        this.imageDataList = imageDataList;
    }

    @Override
    public ApodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_item_card, parent, false);
        return new ApodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ApodViewHolder holder, int position) {
        Glide.with(context).load(imageDataList.get(position).getUrl())
                .asBitmap().centerCrop().into(holder.galleryImage);
        holder.galleryImageTitle.setText(imageDataList.get(position).getTitle());
        Helper.setAnimationForAdapter(context, holder.itemView, position, lastPosition);
    }

    @Override
    public int getItemCount() {
        return imageDataList.size();
    }

    protected class ApodViewHolder extends RecyclerView.ViewHolder {

        private CardView galleryCard;
        private ImageView galleryImage;
        private TextView galleryImageTitle;

        protected ApodViewHolder(View itemView) {
            super(itemView);
            galleryCard = itemView.findViewById(R.id.gallery_card);
            galleryImage = itemView.findViewById(R.id.gallery_image);
            galleryImageTitle = itemView.findViewById(R.id.gallery_image_title);
            galleryCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, FullscreenActivity.class);
                    intent.putExtra("imagePath", imageDataList.get(getAdapterPosition()).getUrl());
                    context.startActivity(intent);
                }
            });
        }
    }
}
