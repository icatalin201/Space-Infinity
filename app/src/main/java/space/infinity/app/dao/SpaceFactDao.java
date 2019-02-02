package space.infinity.app.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import space.infinity.app.models.SpaceFact;

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
    List<SpaceFact> getSpaceFactList();
    
}
