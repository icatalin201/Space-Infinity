package space.infinity.app.model.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import space.infinity.app.model.entity.LaunchSite;

@Dao
public interface LaunchSiteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LaunchSite... launchSites);

    @Insert
    void insert(List<LaunchSite> launchSites);

    @Update
    void update(LaunchSite... launchSites);

    @Update
    void update(List<LaunchSite> launchSites);

    @Delete
    void delete(LaunchSite... launchSites);

    @Delete
    void delete(List<LaunchSite> launchSites);

    @Query(value = "select * from launch_sites;")
    LiveData<List<LaunchSite>> getLaunchSites();
    
}
