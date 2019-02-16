package space.infinity.app.model.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import space.infinity.app.model.entity.Planet;

@Dao
public interface PlanetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
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
    LiveData<List<Planet>> getPlanetList();
    
}
