package space.infinity.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import space.infinity.app.R;
import space.infinity.app.activities.FullscreenActivity;
import space.infinity.app.models.gallery.ImageInfo;
import space.infinity.app.sql.SqlService;
import space.infinity.app.utils.Helper;

/**
 * Created by icatalin on 24.02.2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private Context context;
    private List<ImageInfo> imageInfoList;

    public SearchAdapter(Context context, List<ImageInfo> imageInfoList) {
        this.context = context;
        this.imageInfoList = imageInfoList;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_item_card, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        Glide.with(context).load(imageInfoList.get(position).getImage())
                .asBitmap().centerCrop().into(holder.galleryImage);
        holder.galleryImageTitle.setText(imageInfoList.get(position).getTitle());
        Helper.customAnimation(context, holder.galleryCard, 700, android.R.anim.fade_in);
        /*if (SqlService.isImageFav(context, imageInfoList.get(position).getTitle())) {
            holder.galleryImageFav.setImageResource(R.drawable.fav_big);
            holder.galleryImageFav.setTag("yes");
        }
        else {
            holder.galleryImageFav.setImageResource(R.drawable.notfav_big);
            holder.galleryImageFav.setTag("no");
        }*/
    }

    @Override
    public int getItemCount() {
        return imageInfoList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        private CardView galleryCard;
        private ImageView galleryImage;
        private TextView galleryImageTitle;
        //private ImageButton galleryImageFav;

        public SearchViewHolder(View itemView) {
            super(itemView);
            galleryCard = itemView.findViewById(R.id.gallery_card);
            galleryImage = itemView.findViewById(R.id.gallery_image);
            galleryImageTitle = itemView.findViewById(R.id.gallery_image_title);
            //galleryImageFav = itemView.findViewById(R.id.image_fav);
            galleryCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, FullscreenActivity.class);
                    intent.putExtra("imageObject", imageInfoList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
            /*galleryImageFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (galleryImageFav.getTag().equals("yes")) {
                        SqlService.handleImageFavs(context, "remove",
                                imageInfoList.get(getAdapterPosition()).getTitle(),
                                imageInfoList.get(getAdapterPosition()).getImage(),
                                imageInfoList.get(getAdapterPosition()).getImage(),
                                imageInfoList.get(getAdapterPosition()).getDescription());
                        Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show();
                        galleryImageFav.setTag("no");
                        galleryImageFav.setImageResource(R.drawable.notfav_big);
                    }
                    else if (galleryImageFav.getTag().equals("no")) {
                        SqlService.handleImageFavs(context,"add",
                                imageInfoList.get(getAdapterPosition()).getTitle(),
                                imageInfoList.get(getAdapterPosition()).getImage(),
                                imageInfoList.get(getAdapterPosition()).getImage(),
                                imageInfoList.get(getAdapterPosition()).getDescription());
                        Toast.makeText(context, "Marked as favorite", Toast.LENGTH_SHORT).show();
                        galleryImageFav.setTag("yes");
                        galleryImageFav.setImageResource(R.drawable.fav_big);
                    }
                }
            });*/
        }
    }
}
