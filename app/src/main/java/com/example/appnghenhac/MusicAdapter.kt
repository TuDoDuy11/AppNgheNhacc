package com.example.appnghenhac

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MusicAdapter(private val musicList: List<Music>, private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<MusicAdapter.ViewHolder>() {
    private var selectedPosition: Int = RecyclerView.NO_POSITION
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        val textArtist: TextView = itemView.findViewById(R.id.textArtist)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_music, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val music = musicList[position]
        holder.textTitle.text = music.title
        holder.textArtist.text = music.artist

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(position)
        }

        val backgroundColor = if (position == selectedPosition) {
            Color.parseColor("#DDDDDD")
        } else {
            Color.TRANSPARENT
        }
        holder.itemView.setBackgroundColor(backgroundColor)

        holder.itemView.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged()

            onItemClickListener.onItemClick(position)
        }

    }

    override fun getItemCount(): Int {
        return musicList.size
    }
}