package space.infinity.app.viewmodel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import space.infinity.app.R;

public class ISSCrewAdapter extends RecyclerView.Adapter<ISSCrewAdapter.ISSCrewViewHolder> {

    private Context context;
    private List<String> crewList;

    public ISSCrewAdapter(Context context, List<String> crewList) {
        this.context = context;
        this.crewList = crewList;
    }

    public void add(List<String> crewList) {
        this.crewList.addAll(crewList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ISSCrewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.crew_item, viewGroup, false);
        return new ISSCrewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ISSCrewViewHolder issCrewViewHolder, int i) {
        String crew = crewList.get(i);
        issCrewViewHolder.textView.setText(crew);
    }

    @Override
    public int getItemCount() {
        return crewList.size();
    }

    class ISSCrewViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        ISSCrewViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.crew);
        }
    }
}
