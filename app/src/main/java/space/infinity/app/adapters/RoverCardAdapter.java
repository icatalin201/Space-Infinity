package space.infinity.app.adapters;

import android.content.Context;
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
import space.infinity.app.models.RoverImages;
import space.infinity.app.utils.Helper;

/**
 * Created by Catalin on 1/13/2018.
 */

public class RoverCardAdapter extends RecyclerView.Adapter<RoverCardAdapter.RoverCardViewHolder> {

    private Context mContext;
    private List<RoverImages> roverImages;
    private int lastPosition = -1;

    public RoverCardAdapter(Context mContext, List<RoverImages> roverImages){
        this.mContext = mContext;
        this.roverImages = roverImages;
    }

    @Override
    public RoverCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_item_card, parent, false);
        return new RoverCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoverCardViewHolder holder, int position) {
        Glide.with(mContext).load(roverImages.get(position).getImg_src())
                .asBitmap().centerCrop().into(holder.roverImage);
        holder.roverImageDate.setText(mContext.getResources().getString(R.string.date).concat(" ")
                .concat(roverImages.get(position).getEarth_date()));
        Helper.setAnimationForAdapter(mContext, holder.itemView, position, lastPosition);
    }

    @Override
    public int getItemCount() {
        return roverImages.size();
    }

    public class RoverCardViewHolder extends RecyclerView.ViewHolder{

        public CardView roverCard;
        public ImageView roverImage;
        public TextView roverImageDate;

        public RoverCardViewHolder(View itemView) {
            super(itemView);
            roverCard = itemView.findViewById(R.id.rover_card);
            roverImage = itemView.findViewById(R.id.rover_image);
            roverImageDate = itemView.findViewById(R.id.rover_image_date);
        }
    }

}
