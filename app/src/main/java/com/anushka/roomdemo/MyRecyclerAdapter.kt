package com.anushka.roomdemo
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.anushka.roomdemo.databinding.ListItemBinding
import com.anushka.roomdemo.db.Subscriber

class MyRecyclerAdapter ( private  val clickListener: (Subscriber)->Unit): RecyclerView.Adapter<ViewHolder>() {
    private val subscriberList: ArrayList<Subscriber> = arrayListOf();
    fun setList(incommingList: List<Subscriber>){
        subscriberList.clear()
        subscriberList.addAll(incommingList)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater =LayoutInflater.from(parent.context)
        val bindingUtil : ListItemBinding=DataBindingUtil.inflate(inflater,R.layout.list_item, parent, false )
        return ViewHolder(bindingUtil)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(subscriberList[position], clickListener)
    }

    override fun getItemCount(): Int {

        return subscriberList.size;
    }


}

class ViewHolder(val binding: ListItemBinding ): RecyclerView.ViewHolder(binding.root){

    fun bind(subscriber: Subscriber, onClickListener: (Subscriber)->Unit){
        binding.name.text=subscriber.name
        binding.email.text=subscriber.email

        binding.cardView.setOnClickListener {
            onClickListener(subscriber)
        }
    }

}