package space.infinity.app.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import space.infinity.app.models.Galaxy;
import space.infinity.app.models.Moon;
import space.infinity.app.models.Planet;
import space.infinity.app.models.Star;

@Dao
public interface CosmicDao {

    @Query(value = "select * from galaxies where id = :id;")
    Galaxy getGalaxy(long id);

    @Query(value = "select * from galaxies;")
    List<Galaxy> getGalaxyList();

    @Query(value = "select * from moons where id = :id;")
    Moon getMoon(long id);

    @Query(value = "select * from moons;")
    List<Moon> getMoonList();

    @Query(value = "select * from planets where id = :id;")
    Planet getPlanet(long id);

    @Query(value = "select * from planets;")
    List<Planet> getPlanetList();

    @Query(value = "select * from stars where id = :id;")
    Star getStar(long id);

    @Query(value = "select * from stars;")
    List<Star> getStarList();
    
}
