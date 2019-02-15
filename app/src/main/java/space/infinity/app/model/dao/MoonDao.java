package space.infinity.app.model.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import space.infinity.app.model.entity.Moon;

@Dao
public interface MoonDao {

    @Insert
    void insert(Moon... moons);

    @Insert
    void insert(List<Moon> moons);

    @Update
    void update(Moon... moons);

    @Update
    void update(List<Moon> moons);

    @Delete
    void delete(Moon... moons);

    @Delete
    void delete(List<Moon> moons);

    @Query(value = "select * from moons where id = :id;")
    Moon getMoon(long id);

    @Query(value = "select * from moons;")
    LiveData<List<Moon>> getMoonList();
    
}
