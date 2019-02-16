package space.infinity.app.model.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import space.infinity.app.model.entity.ImageItem;

@Dao
public interface ImageItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ImageItem... imageItems);

    @Insert
    void insert(List<ImageItem> imageItems);

    @Update
    void update(ImageItem... imageItems);

    @Update
    void update(List<ImageItem> imageItems);

    @Delete
    void delete(ImageItem... imageItems);

    @Delete
    void delete(List<ImageItem> imageItems);

    @Query(value = "select * from images where id = :id;")
    ImageItem getImage(long id);

    @Query(value = "select * from images order by id desc limit 1")
    ImageItem getLastImage();

    @Query(value = "select * from images;")
    LiveData<List<ImageItem>> getImageList();

}
