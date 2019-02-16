package space.infinity.app.model.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import space.infinity.app.model.entity.Voyager;

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
    LiveData<List<Voyager>> getVoyagerList();
    
}
