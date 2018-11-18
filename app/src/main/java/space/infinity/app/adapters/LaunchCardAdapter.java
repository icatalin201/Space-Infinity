package space.infinity.app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import space.infinity.app.R;
import space.infinity.app.models.launch.Launch;
import space.infinity.app.utils.Helper;

/**
 * Created by icatalin on 24.01.2018.
 */

public class LaunchCardAdapter extends RecyclerView.Adapter<LaunchCardAdapter.LaunchCardAdapterViewHolder> {

    private Context context;
    private List<Launch> launchList;

    public LaunchCardAdapter(Context context, List<Launch> launches){
        this.context = context;
        this.launchList = launches;
    }

    @NonNull
    @Override
    public LaunchCardAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rocket_launch_card, parent, false);
        return new LaunchCardAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaunchCardAdapterViewHolder holder, int position) {
        holder.rocket_name.setText(context.getResources().getString(R.string.rocket_name)
                .concat(" ").concat(launchList.get(position).getRocket().getRocket_name()));
        holder.rocket_type.setText(context.getResources().getString(R.string.rocket_type)
                .concat(" ").concat(launchList.get(position).getRocket().getRocket_type()));
        holder.payload_id.setText(context.getResources().getString(R.string.payload_id)
                .concat(" ").concat(launchList.get(position).getRocket().getSecond_stage()
                .getPayloads().get(0).getPayload_id()));
        holder.payload_type.setText(context.getResources().getString(R.string.payload_type)
                .concat(" ").concat(launchList.get(position).getRocket().getSecond_stage()
                .getPayloads().get(0).getPayload_type()));
        holder.payload_customer.setText(context.getResources().getString(R.string.payload_customer)
                .concat(" ").concat(launchList.get(position).getRocket().getSecond_stage()
                .getPayloads().get(0).getCustomers()));
        holder.launch_site.setText(context.getResources().getString(R.string.launch_site)
                .concat(" ").concat(launchList.get(position).getLaunch_site().getSite_name_long()));
        Long unix_date = launchList.get(position).getLaunch_date_unix();
        holder.launch_date.setText(context.getResources().getString(R.string.launch_date)
                .concat(" ").concat(Helper.unixToDate(unix_date)));
    }

    @Override
    public int getItemCount() {
        return launchList.size();
    }

    class LaunchCardAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView rocket_name;
        private TextView rocket_type;
        private TextView payload_id;
        private TextView payload_type;
        private TextView payload_customer;
        private TextView launch_date;
        private TextView launch_site;

        LaunchCardAdapterViewHolder(View itemView) {
            super(itemView);
            rocket_name = itemView.findViewById(R.id.rocket_name);
            rocket_type = itemView.findViewById(R.id.rocket_type);
            payload_id = itemView.findViewById(R.id.payload_id);
            payload_customer = itemView.findViewById(R.id.payload_customer);
            payload_type = itemView.findViewById(R.id.payload_type);
            launch_date = itemView.findViewById(R.id.launch_date);
            launch_site = itemView.findViewById(R.id.launch_site);
        }
    }
}
