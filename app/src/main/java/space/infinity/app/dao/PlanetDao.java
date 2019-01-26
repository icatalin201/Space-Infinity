package space.infinity.app.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import space.infinity.app.models.Planet;

@Dao
public interface PlanetDao {

    @Insert
    void insert(Planet... planets);

    @Insert
    void insert(List<Planet> planets);

    @Update
    void update(Planet... planets);

    @Update
    void update(List<Planet> planets);

    @Delete
    void delete(Planet... planets);

    @Delete
    void delete(List<Planet> planets);

    @Query(value = "select * from planets where id = :id;")
    Planet getPlanet(long id);

    @Query(value = "select * from planets;")
    List<Planet> getPlanetList();
    
}
