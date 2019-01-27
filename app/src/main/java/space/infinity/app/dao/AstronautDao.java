package space.infinity.app.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import space.infinity.app.models.Astronaut;

@Dao
public interface AstronautDao {

    @Insert
    void insert(Astronaut... astronauts);

    @Insert
    void insert(List<Astronaut> astronauts);

    @Update
    void update(Astronaut... astronauts);

    @Update
    void update(List<Astronaut> astronauts);

    @Delete
    void delete(Astronaut... astronauts);

    @Delete
    void delete(List<Astronaut> astronauts);

    @Query(value = "select * from astronauts where id = :id;")
    Astronaut getAstronaut(long id);

    @Query(value = "select * from astronauts;")
    List<Astronaut> getAstronautList();
    
}
