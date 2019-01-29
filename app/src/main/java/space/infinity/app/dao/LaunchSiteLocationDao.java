package space.infinity.app.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import space.infinity.app.models.LaunchSiteLocation;

@Dao
public interface LaunchSiteLocationDao {

    @Insert
    void insert(LaunchSiteLocation... launchSitesLocations);

    @Insert
    void insert(List<LaunchSiteLocation> launchSitesLocations);

    @Update
    void update(LaunchSiteLocation... launchSitesLocations);

    @Update
    void update(List<LaunchSiteLocation> launchSitesLocations);

    @Delete
    void delete(LaunchSiteLocation... launchSitesLocations);

    @Delete
    void delete(List<LaunchSiteLocation> launchSitesLocations);

    @Query(value = "select * from launch_site_locations where launch_site_id = :launchSiteId;")
    List<LaunchSiteLocation> getLaunchSiteLocations(long launchSiteId);
    
}
