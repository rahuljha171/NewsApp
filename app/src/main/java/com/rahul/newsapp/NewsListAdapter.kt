package com.rahul.newsapp

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter( private val listener:NewsItemclicked): RecyclerView.Adapter<NewsViesHolder>() {

    private val items:ArrayList<News> = ArrayList()

    override fun getItemCount(): Int {
return items.size
    }

    override fun onBindViewHolder(holder: NewsViesHolder, position: Int) {
val currentItem=items[position]
        holder.titleview.text=currentItem.title
        holder.author.text=currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViesHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewHolder= NewsViesHolder(view)
view.setOnClickListener{
listener.onItemclicked(items[viewHolder.adapterPosition])
}
return viewHolder
    }
fun updateNews(updatedNews:ArrayList<News>)
{
    items.clear()
 items.addAll(updatedNews)
    notifyDataSetChanged()



}

}
class NewsViesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
val titleview:TextView=itemView.findViewById(R.id.title)
    val image:ImageView=itemView.findViewById(R.id.image)
    val author:TextView=itemView.findViewById(R.id.author)

}
interface NewsItemclicked{
    fun onItemclicked(item: News)

}