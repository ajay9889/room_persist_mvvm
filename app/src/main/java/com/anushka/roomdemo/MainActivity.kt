package com.anushka.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anushka.roomdemo.databinding.ActivityMainBinding
import com.anushka.roomdemo.db.Subscriber
import com.anushka.roomdemo.db.SubscriberViewModel
import com.anushka.roomdemo.db.SubscriptionDatabase
import com.anushka.roomdemo.db.subscriberRepository

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: SubscriberViewModel
    lateinit var repository: subscriberRepository
    lateinit var adapter: MyRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao = SubscriptionDatabase.getInstance(this).subscriberDao;
        repository=subscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository);
        viewModel=ViewModelProvider(this, factory).get(SubscriberViewModel::class.java);
        binding.lifecycleOwner = this
        binding.mySubscriberViewModel = viewModel
        getUserList()

        viewModel.message.observe(this, Observer {

            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }

        })
    }


    fun getUserList(){
        adapter  =MyRecyclerAdapter( {selectedItem :Subscriber->onClickItem(selectedItem)})

        binding.recyclerView.layoutManager=LinearLayoutManager(this , RecyclerView.VERTICAL,false);
        binding.recyclerView.adapter =adapter
        viewModel.getAllSubscriber.observe(this, Observer {

            adapter.setList(it)
            adapter.notifyDataSetChanged()

        })
    }

    fun onClickItem(subscriber: Subscriber){

        viewModel.initUpdateDelete(subscriber)
    }

}
