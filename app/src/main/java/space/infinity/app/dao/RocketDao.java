package space.infinity.app.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import space.infinity.app.models.Rocket;

@Dao
public interface RocketDao {

    @Insert
    void insert(Rocket... rockets);

    @Insert
    void insert(List<Rocket> rockets);

    @Update
    void update(Rocket... rockets);

    @Update
    void update(List<Rocket> rockets);

    @Delete
    void delete(Rocket... rockets);

    @Delete
    void delete(List<Rocket> rockets);

    @Query(value = "select * from rockets where id = :id;")
    Rocket getRocket(long id);

    @Query(value = "select * from rockets;")
    List<Rocket> getRocketList();
}
