package com.anushka.roomdemo.db

import androidx.lifecycle.LiveData

class subscriberRepository (private val dao: SubscriberDao) {

     fun getAllSubscriber(): LiveData<List<Subscriber>> {
        return dao.getSubscriber();
    }

    suspend fun insertSubscriber(subscriber: Subscriber){
        dao.insertSubscriber(subscriber)
    }
    suspend fun updateSubscriber(subscriber: Subscriber){
        dao.updateSubscriber(subscriber)
    }
    suspend fun deleteSubscriber(subscriber: Subscriber){
        dao.deletSubscription(subscriber)
    }

    suspend fun deletAll(){
        dao.deleteAll()
    }

}