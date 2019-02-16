package space.infinity.app.model.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import space.infinity.app.model.entity.Star;

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
    LiveData<List<Star>> getStarList();
    
}
