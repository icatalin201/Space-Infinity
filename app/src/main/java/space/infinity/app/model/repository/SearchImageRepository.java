package space.infinity.app.model.repository;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import space.infinity.app.model.entity.ImageItem;
import space.infinity.app.util.Constants;
import space.infinity.app.util.JSONHandler;

public class SearchImageRepository {

    public interface SearchImageCallback {
        void onSuccess(List<ImageItem> imageItems);
        void onLoading();
    }

    private SearchImageCallback searchImageCallback;
    private Handler handler;

    public SearchImageRepository(SearchImageCallback searchImageCallback) {
        this.searchImageCallback = searchImageCallback;
        this.handler = new Handler(Looper.getMainLooper());
    }

    public void search(String query) {
        searchImageCallback.onLoading();
        String url = Constants.NASA_IMAGE_URL.concat("search?q=").concat(query);
        searchImages(url);
    }

    private void searchImages(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = new JSONHandler().makeHttpRequest(url);
                final List<ImageItem> imageItemList = new ArrayList<>();
                try {
                    JSONArray array = jsonObject.getJSONObject("collection").getJSONArray("items");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = (JSONObject) array.get(i);
                        JSONObject o = (JSONObject) object.getJSONArray("data").get(0);
                        if (o.getString("media_type").equals("image")) {
                            JSONObject oo = (JSONObject) object.getJSONArray("links").get(0);
                            ImageItem imageInfo = new ImageItem();
                            if (o.has("date_created")) {
                                imageInfo.setDateCreated(o.getString("date_created"));
                            }
                            if (o.has("description")) {
                                imageInfo.setDescription(o.getString("description"));
                            }
                            if (o.has("title")) {
                                imageInfo.setTitle(o.getString("title"));
                            }
                            if (oo.has("href")) {
                                imageInfo.setImage(oo.getString("href"));
                            } else continue;
                            imageItemList.add(imageInfo);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        searchImageCallback.onSuccess(imageItemList);
                    }
                });
            }
        }).start();
    }

}
