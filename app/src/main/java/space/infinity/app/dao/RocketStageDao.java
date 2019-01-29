package space.infinity.app.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import space.infinity.app.models.RocketStage;

@Dao
public interface RocketStageDao {

    @Insert
    void insert(RocketStage... rocketStages);

    @Insert
    void insert(List<RocketStage> rocketStages);

    @Update
    void update(RocketStage... rocketStages);

    @Update
    void update(List<RocketStage> rocketStages);

    @Delete
    void delete(RocketStage... rocketStages);

    @Delete
    void delete(List<RocketStage> rocketStages);

    @Query(value = "select * from rocket_stages where rocket_id = :rocketId;")
    List<RocketStage> getRocketStages(long rocketId);
    
}
