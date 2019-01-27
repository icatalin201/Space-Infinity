package space.infinity.app.database;

import android.arch.persistence.room.Room;
import android.content.Context;

public class AppDatabaseHelper {

    public static AppDatabase getDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "infinity.db")
                .fallbackToDestructiveMigration()
                .build();
    }

}
