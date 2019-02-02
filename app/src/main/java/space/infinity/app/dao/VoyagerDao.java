package space.infinity.app.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import space.infinity.app.models.Voyager;

@Dao
public interface VoyagerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Voyager... voyagers);

    @Insert
    void insert(List<Voyager> voyagers);

    @Update
    void update(Voyager... voyagers);

    @Update
    void update(List<Voyager> voyagers);

    @Delete
    void delete(Voyager... voyagers);

    @Delete
    void delete(List<Voyager> voyagers);

    @Query(value = "select * from voyagers where id = :id;")
    Voyager getVoyager(long id);

    @Query(value = "select * from voyagers;")
    List<Voyager> getVoyagerList();
    
}
