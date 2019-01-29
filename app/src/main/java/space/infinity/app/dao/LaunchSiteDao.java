package space.infinity.app.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import space.infinity.app.models.LaunchSite;

@Dao
public interface LaunchSiteDao {

    @Insert
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
    List<LaunchSite> getLaunchSites();
    
}
