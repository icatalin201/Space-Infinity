package space.infinity.app.models;

import android.content.Context;

public abstract class ActivityHelper {

    private Context context;

    public ActivityHelper(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void onStop() { }
    public void onPause() { }
    public void onResume() { }
    public abstract void onStart();
    public abstract void onDestroy();
    public abstract void showLayout();
}
