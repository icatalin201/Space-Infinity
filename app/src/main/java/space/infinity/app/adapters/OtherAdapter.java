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
import space.infinity.app.activities.Encyclopedia;
import space.infinity.app.models.encyclopedia.Other;
import space.infinity.app.utils.Helper;

/**
 * Created by icatalin on 24.02.2018.
 */

public class OtherAdapter extends RecyclerView.Adapter<OtherAdapter.OtherViewHolder> {

    private Context context;
    private List<Other> otherList;

    public OtherAdapter(Context context, List<Other> others) {
        this.context = context;
        this.otherList = others;
    }

    @Override
    public OtherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.choose_enc_item, parent, false);
        return new OtherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OtherViewHolder holder, int position) {
        holder.name.setText(otherList.get(position).getName());
        String imageName = otherList.get(position).getImage().split("\\.")[0];
        int resID = context.getResources().getIdentifier(imageName , "drawable", context.getPackageName());
        Glide.with(context).load(resID).asBitmap().centerCrop().into(holder.image);
        Helper.customAnimation(context, holder.card, 700, android.R.anim.fade_in);
    }

    @Override
    public int getItemCount() {
        return otherList.size();
    }

    class OtherViewHolder extends RecyclerView.ViewHolder {

        private CardView card;
        private ImageView image;
        private TextView name;

        OtherViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Encyclopedia.class);
                    intent.putExtra("object", otherList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
