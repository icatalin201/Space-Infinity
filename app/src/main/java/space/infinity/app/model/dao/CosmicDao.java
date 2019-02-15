package space.infinity.app.model.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import space.infinity.app.model.entity.Galaxy;
import space.infinity.app.model.entity.Moon;
import space.infinity.app.model.entity.Planet;
import space.infinity.app.model.entity.Star;

@Dao
public interface CosmicDao {

    @Query(value = "select * from galaxies where id = :id;")
    Galaxy getGalaxy(long id);

    @Query(value = "select * from galaxies;")
    LiveData<List<Galaxy>> getGalaxyList();

    @Query(value = "select * from moons where id = :id;")
    Moon getMoon(long id);

    @Query(value = "select * from moons;")
    LiveData<List<Moon>> getMoonList();

    @Query(value = "select * from planets where id = :id;")
    Planet getPlanet(long id);

    @Query(value = "select * from planets;")
    LiveData<List<Planet>> getPlanetList();

    @Query(value = "select * from stars where id = :id;")
    Star getStar(long id);

    @Query(value = "select * from stars;")
    LiveData<List<Star>> getStarList();
    
}
