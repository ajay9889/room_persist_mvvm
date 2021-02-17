package com.anushka.roomdemo.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscriberDao {
    @Insert
    suspend fun insertSubscriber(subscriber: Subscriber): Long;

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber): Int;

    @Delete
    suspend fun deletSubscription(subscriber: Subscriber)

    @Query("DELETE FROM `subriber_data_table`")
    suspend fun deleteAll()

    @Query("SELECT * FROM `subriber_data_table`")
     fun getSubscriber() : LiveData< List<Subscriber>>
}