package com.anushka.roomdemo.db

import android.text.TextUtils
import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anushka.roomdemo.Event
import kotlinx.coroutines.*
import java.util.regex.Pattern

class SubscriberViewModel(private val subscriberRepository: subscriberRepository) : ViewModel() , Observable{

    var isSelectedDeleteUpdate: Boolean=false;

    lateinit var selectedSubsscriberUpdateDelete: Subscriber
    @Bindable
    val inputName = MutableLiveData<String>();
    @Bindable
    val inputEmail = MutableLiveData<String>();

    @Bindable
    val clickButton1 = MutableLiveData<String>();
    @Bindable
    val clickButton2 = MutableLiveData<String>();

    private val eventMessage=MutableLiveData<Event<String>>();
    val message: LiveData<Event<String>>
    get() =eventMessage

    init {
        clickButton1.value="Save"
        clickButton2.value="Clear All"
    }

    fun saveOrUpdate(){
        // validation
        if(inputName.value==null || TextUtils.isEmpty(inputName.value.toString())){
            eventMessage.value=Event("Enter name");
        }else if(inputEmail.value==null || TextUtils.isEmpty(inputEmail.value.toString())){
            eventMessage.value=Event("Enter Email");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value.toString()).matches()){
            eventMessage.value=Event("Enter valid Email");
        }else {
            if (isSelectedDeleteUpdate) {
                selectedSubsscriberUpdateDelete.name = inputName.value.toString()
                selectedSubsscriberUpdateDelete.email = inputEmail.value.toString()
                update(selectedSubsscriberUpdateDelete)
                clickButton1.value = "Save"
                clickButton2.value = "Clear All"
                isSelectedDeleteUpdate = false
            } else {
                val name: String = inputName.value!!
                val email: String = inputEmail.value!!
                insert(Subscriber(0, name, email))
                inputName.value = null
                inputEmail.value = null
            }
        }

    }

    fun clearAllOrDelete(){

        if(isSelectedDeleteUpdate){
            delete(selectedSubsscriberUpdateDelete)
            clickButton1.value="Save"
            clickButton2.value="Clear All"
            isSelectedDeleteUpdate=false
            inputName.value=null
            inputEmail.value=null
        }else{
            deleteAll();
            clickButton1.value="Save"
            clickButton2.value="Clear All"
            inputName.value=null
            inputEmail.value=null
        }
    }

    var getAllSubscriber  = subscriberRepository.getAllSubscriber()
//    lateinit var deffered : Deferred<LiveData<List<Subscriber>>>
//     fun getAllSubscriber():LiveData<List<Subscriber>>  {
//
//         viewModelScope.launch(Dispatchers.IO) {
//             deffered =  async {
//                 return@async subscriberRepository.getAllSubscriber();
//             }
//         }
//         return deffered!!.await()
//    }

    fun initUpdateDelete(subscriber: Subscriber){
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isSelectedDeleteUpdate=true;
        selectedSubsscriberUpdateDelete = subscriber
        clickButton1.value="Update"
        clickButton2.value="Delete"

    }
    fun insert(subscriber: Subscriber): Job =viewModelScope.launch {
        subscriberRepository.insertSubscriber(subscriber)
        eventMessage.value=Event("Inserted Sucessfully");
    }

    fun update(subscriber: Subscriber): Job =viewModelScope.launch {
        subscriberRepository.updateSubscriber(subscriber)
        eventMessage.value=Event("Updated Sucessfully");
    }


    fun delete(subscriber: Subscriber): Job =viewModelScope.launch {
        subscriberRepository.deleteSubscriber(subscriber)
        eventMessage.value=Event("Deleted Item Sucessfully");

    }
    fun deleteAll(): Job = viewModelScope.launch {
        subscriberRepository.deletAll()
        eventMessage.value=Event("Cleared Sucessfully");
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

}