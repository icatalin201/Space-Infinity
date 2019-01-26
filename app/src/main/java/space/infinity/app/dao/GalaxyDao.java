package space.infinity.app.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import space.infinity.app.models.Galaxy;

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
    List<Galaxy> getGalaxyList();

}
