package space.infinity.app.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import space.infinity.app.models.Moon;

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
    List<Moon> getMoonList();
    
}
