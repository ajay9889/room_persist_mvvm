package com.anushka.roomdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anushka.roomdemo.db.SubscriberViewModel
import com.anushka.roomdemo.db.subscriberRepository
import java.lang.IllegalArgumentException

class SubscriberViewModelFactory (private val repository: subscriberRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(SubscriberViewModel::class.java))
        {

            return SubscriberViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknow ViewModel Repository")
    }
}