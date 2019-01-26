package space.infinity.app.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import space.infinity.app.models.ImageItem;

@Dao
public interface ImageItemDao {

    @Insert
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

    @Query(value = "select * from images;")
    List<ImageItem> getImageList();

}
