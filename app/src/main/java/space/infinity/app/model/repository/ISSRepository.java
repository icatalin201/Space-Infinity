package space.infinity.app.model.repository;

import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import space.infinity.app.R;
import space.infinity.app.util.Constants;
import space.infinity.app.util.Helper;
import space.infinity.app.util.JSONHandler;

public class ISSRepository {

    public interface ISSCallback {
        void onIssPass(String date);
        void onIssPassFailure();
        void onGetAstronauts(List<String> crewList);
        void onFailure();
        void onUpdatePosition(LatLng latLng, String velocity, String altitude);
    }

    private ISSCallback issCallback;
    private Handler handler;
    private Marker marker;

    public ISSRepository(ISSCallback issCallback) {
        this.issCallback = issCallback;
        this.handler = new Handler(Looper.getMainLooper());
    }

    public void getAstronautsInSpace() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONHandler().makeHttpRequest(Constants.ISS_CREW);
                    JSONArray jsonArray = jsonObject.getJSONArray("people");
                    final List<String> crewList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        crewList.add(jsonArray.getJSONObject(i).getString("name"));
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            issCallback.onGetAstronauts(crewList);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            issCallback.onFailure();
                        }
                    });
                }
            }
        }).start();
    }

    private void updateLocation(LatLng location, GoogleMap googleMap,
                                String velocity, String altitude) {
        if (googleMap == null) return;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(location, 3.0f);
        googleMap.animateCamera(cameraUpdate);
        if(marker == null) {
            marker = googleMap.addMarker(new MarkerOptions().position(location)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.iss))
                    .zIndex(3.0f).title("International Space Station"));
        }
        else {
            marker.setPosition(location);
        }
        marker.setVisible(true);
        issCallback.onUpdatePosition(location, velocity, altitude);
    }

    public void updatePosition(final GoogleMap googleMap) {
        Log.i("ISS", "update position");
        JSONObject jsonObject = new JSONHandler().makeHttpRequest(Constants.ISS_NOW);
        System.out.println(jsonObject.toString());
        try {
            DecimalFormat numberFormat = new DecimalFormat("#.00");
            final String velocity = numberFormat.format(jsonObject.getDouble("velocity"));
            final String altitude = numberFormat.format(jsonObject.getDouble("altitude"));
            final LatLng location = new LatLng(jsonObject.getDouble("latitude"),
                    jsonObject.getDouble("longitude"));
            handler.post(new Runnable() {
                @Override
                public void run() {
                    updateLocation(location, googleMap, velocity, altitude);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    issCallback.onFailure();
                }
            });
        }
    }

    public void issPass(Location location) {
        if (location == null) {
            issCallback.onIssPassFailure();
            return;
        }
        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();
        final String params = "lat=".concat(latitude.toString())
                .concat("&lon=").concat(longitude.toString())
                .concat("&n=3");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONHandler()
                            .makeHttpRequest(Constants.ISS_PASS.concat(params));
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    JSONObject object = (JSONObject) jsonArray.get(0);
                    final String date = Helper.unixToDate(object.getLong("risetime"));
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            issCallback.onIssPass(date);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            issCallback.onIssPassFailure();
                        }
                    });
                }
            }
        }).start();
    }

}
