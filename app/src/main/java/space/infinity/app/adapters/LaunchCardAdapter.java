package space.infinity.app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    @Override
    public LaunchCardAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rocket_launch_card, parent, false);
        return new LaunchCardAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LaunchCardAdapterViewHolder holder, int position) {
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

    public class LaunchCardAdapterViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback{

        private TextView rocket_name;
        private TextView rocket_type;
        private TextView payload_id;
        private TextView payload_type;
        private TextView payload_customer;
        private TextView launch_date;
        private TextView launch_site;
        private MapView map;

        LaunchCardAdapterViewHolder(View itemView) {
            super(itemView);
            rocket_name = itemView.findViewById(R.id.rocket_name);
            rocket_type = itemView.findViewById(R.id.rocket_type);
            payload_id = itemView.findViewById(R.id.payload_id);
            payload_customer = itemView.findViewById(R.id.payload_customer);
            payload_type = itemView.findViewById(R.id.payload_type);
            launch_date = itemView.findViewById(R.id.launch_date);
            launch_site = itemView.findViewById(R.id.launch_site);
            map = itemView.findViewById(R.id.map_view_launch);

            if (map != null) {
                map.onCreate(null);
                map.onResume();
                map.getMapAsync(this);
            }
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsInitializer.initialize(context.getApplicationContext());
            googleMap.getUiSettings().setAllGesturesEnabled(false);
            googleMap.getUiSettings().setZoomGesturesEnabled(false);
            try {
                JSONObject jsonObject = new GetLatLngFromLocation().execute(launchList
                        .get(getAdapterPosition()).getLaunch_site().getSite_name_long()).get();
                JSONObject result = (JSONObject) jsonObject.getJSONArray("results").get(0);
                JSONObject loc = result.getJSONObject("geometry").getJSONObject("location");
                LatLng location = new LatLng(loc.getDouble("lat"), loc.getDouble("lng"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 5));
                googleMap.addMarker(new MarkerOptions().position(location));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetLatLngFromLocation extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {
            String urlString = "http://maps.googleapis.com/maps/api/geocode/json?address="
                .concat(strings[0]).concat("&sensor=false");
            JSONObject jsonObject = null;
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
                urlConnection.disconnect();
                jsonObject = new JSONObject(stringBuilder.toString());
            } catch (java.io.IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonObject;
        }
    }
}
