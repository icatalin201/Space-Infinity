package space.infinity.app.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import space.infinity.app.models.Star;

@Dao
public interface StarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Star... stars);

    @Insert
    void insert(List<Star> stars);

    @Update
    void update(Star... stars);

    @Update
    void update(List<Star> stars);

    @Delete
    void delete(Star... stars);

    @Delete
    void delete(List<Star> stars);

    @Query(value = "select * from stars where id = :id;")
    Star getStar(long id);

    @Query(value = "select * from stars;")
    List<Star> getStarList();
    
}
