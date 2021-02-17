package com.anushka.roomdemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Subscriber::class], version = 1)
abstract class SubscriptionDatabase : RoomDatabase() {
   abstract val subscriberDao:SubscriberDao;
    companion object{
        @Volatile
        private var INSTANSEDB: SubscriptionDatabase?=null;
        fun getInstance(context: Context): SubscriptionDatabase{
            synchronized(this){
                var instance :SubscriptionDatabase?= INSTANSEDB
                if(instance==null){
                    instance= Room.databaseBuilder(context,
                    SubscriptionDatabase::class.java,
                    "SubscriptionDatabase").build()
                }
                return instance;
            }
        }
    }
}