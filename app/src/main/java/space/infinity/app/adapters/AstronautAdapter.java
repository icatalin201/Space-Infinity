package space.infinity.app.adapters;

import android.content.Context;
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
import space.infinity.app.models.Astronaut;

public class AstronautAdapter extends RecyclerView.Adapter<AstronautAdapter.AstronautsViewHolder> {

    private Context context;
    private List<Astronaut> astronautList;

    public AstronautAdapter(Context context, List<Astronaut> astronautList) {
        this.context = context;
        this.astronautList = astronautList;
    }

    public void add(List<Astronaut> astronautList) {
        this.astronautList.addAll(astronautList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AstronautsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.astronaut_item, viewGroup,
                false);
        return new AstronautsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AstronautsViewHolder astronautsViewHolder, int i) {
        Astronaut astronaut = astronautList.get(i);
        Glide.with(context)
                .load(astronaut.getImage())
                .apply(RequestOptions.circleCropTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(astronautsViewHolder.image);
        astronautsViewHolder.name.setText(astronaut.getName());
    }

    @Override
    public int getItemCount() {
        return this.astronautList.size();
    }

    class AstronautsViewHolder extends RecyclerView.ViewHolder {

        private CardView item;
        private ImageView image;
        private TextView name;

        AstronautsViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
