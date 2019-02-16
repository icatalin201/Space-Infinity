package space.infinity.app.model.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import space.infinity.app.model.entity.Galaxy;

@Dao
public interface GalaxyDao {

    @Insert
    void insert(Galaxy... galaxies);

    @Insert
    void insert(List<Galaxy> galaxyList);

    @Update
    void update(Galaxy... galaxies);

    @Update
    void update(List<Galaxy> galaxyList);

    @Delete
    void delete(Galaxy... galaxies);

    @Delete
    void delete(List<Galaxy> galaxyList);

    @Query(value = "select * from galaxies where id = :id;")
    Galaxy getGalaxy(long id);

    @Query(value = "select * from galaxies;")
    LiveData<List<Galaxy>> getGalaxyList();

}
