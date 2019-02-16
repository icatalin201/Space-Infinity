package space.infinity.app.model.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import space.infinity.app.model.entity.SpaceFact;

@Dao
public interface SpaceFactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SpaceFact... spaceFacts);

    @Insert
    void insert(List<SpaceFact> spaceFacts);

    @Update
    void update(SpaceFact... spaceFacts);

    @Update
    void update(List<SpaceFact> spaceFacts);

    @Delete
    void delete(SpaceFact... spaceFacts);

    @Delete
    void delete(List<SpaceFact> spaceFacts);

    @Query(value = "select * from space_facts where id = :id;")
    SpaceFact getSpaceFact(long id);

    @Query(value = "select * from space_facts;")
    LiveData<List<SpaceFact>> getSpaceFactList();

    @Query(value = "select * from space_facts where is_favorite = 1;")
    LiveData<List<SpaceFact>> getFavoriteSpaceFactList();
    
}
