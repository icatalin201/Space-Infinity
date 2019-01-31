package space.infinity.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import space.infinity.app.R;
import space.infinity.app.activities.InternalWebActivity;
import space.infinity.app.models.LaunchAgency;
import space.infinity.app.models.LaunchMission;

public class MissionsAdapter extends RecyclerView.Adapter<MissionsAdapter.MissionsViewHolder> {

    private Context context;
    private List<LaunchMission> launchMissionList;

    public MissionsAdapter(Context context, List<LaunchMission> launchMissionList) {
        this.context = context;
        this.launchMissionList = launchMissionList;
    }

    public void add(List<LaunchMission> launchMissions) {
        this.launchMissionList.addAll(launchMissions);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MissionsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.mission_item, viewGroup, false);
        return new MissionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MissionsViewHolder missionsViewHolder, int i) {
        LaunchMission launchMission = launchMissionList.get(i);
        List<LaunchAgency> launchAgencies = launchMission.getAgencies();
        if (launchAgencies != null && launchAgencies.size() > 0) {
            missionsViewHolder.agencies.setText(String.format("#%s",
                    launchAgencies.get(0).getName().replace(" ", "")));
            missionsViewHolder.agencies.setVisibility(View.VISIBLE);
        } else {
            missionsViewHolder.agencies.setVisibility(View.GONE);
        }
        missionsViewHolder.cardView.setTag(launchMission.getWikiURL());
        missionsViewHolder.name.setText(launchMission.getName());
        missionsViewHolder.description.setText(launchMission.getDescription());
        missionsViewHolder.payloads.setText(String.format("Payloads: %s", launchMission.getPayloads().size()));
    }

    @Override
    public int getItemCount() {
        return launchMissionList.size();
    }

    class MissionsViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView name;
        private TextView description;
        private TextView payloads;
        private TextView agencies;

        MissionsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            payloads = itemView.findViewById(R.id.payloads);
            cardView = itemView.findViewById(R.id.item);
            agencies = itemView.findViewById(R.id.agencies);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getTag().toString().contains("http")) {
                        Intent intent = new Intent(context, InternalWebActivity.class);
                        intent.putExtra("url", view.getTag().toString());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
