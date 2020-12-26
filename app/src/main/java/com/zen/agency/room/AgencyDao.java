package com.zen.agency.room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

@Dao
public interface AgencyDao {
    @Insert
    void insert(Agency agency);
    @Update
    void update(Agency agency);
    @Delete
    void delete(Agency agency);

    @Query("DELETE  FROM agency_table")
    void deleteAllNotes();
    @Query("SELECT *  FROM agency_table  order by id desc")
    LiveData<List<Agency>> getAllAgency();
    @Query("SELECT *  FROM agency_table where userId=:userId order by id desc")
    LiveData<List<Agency>> getAllAgency(String userId);



    @Query("SELECT *  FROM agency_table where surface  between :minSurface and :maxSurface and type= :type  and roomNum between" +
            ":minNum and :maxNum  and address LIKE :address and price  between :minPrice and :maxPrice")
    LiveData<List<Agency>> getAllAgency(Double minSurface, Double maxSurface,String type,Double minPrice,Double maxPrice,Double minNum,Double maxNum,String address);

    @RawQuery(observedEntities = Agency.class)
    LiveData<List<Agency>> getAll(SupportSQLiteQuery query);
}
