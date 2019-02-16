package space.infinity.app.model.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import space.infinity.app.model.entity.Astronaut;

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
    LiveData<List<Astronaut>> getAstronautList();
    
}
