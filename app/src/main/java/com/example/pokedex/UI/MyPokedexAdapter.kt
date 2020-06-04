package com.example.pokedex.UI

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.pokedex.Model.MyPokemon
import com.example.pokedex.R

class MyPokedexAdapter(private val context: Context, private var pokemonList: List<MyPokemon?>?) : RecyclerView.Adapter<MyPokedexAdapter.ViewHolder>() {
    private var onItemClickListner: OnItemClickListner? = null

    interface OnItemClickListner {
        fun OnItemClick(position: Int)
    }

    fun setOnItemClickListner(onItemClickListner: OnItemClickListner?) {
        this.onItemClickListner = onItemClickListner
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.grid_cell, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.name.text = pokemonList!![viewHolder.adapterPosition]!!.name
        Glide.with(context)
                .load(pokemonList!![viewHolder.adapterPosition]!!.frontImage)
                .centerCrop()
                .transition(DrawableTransitionOptions().crossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.image)
        viewHolder.pokedexCell.setOnClickListener {
            if (onItemClickListner != null) {
                val position = viewHolder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListner!!.OnItemClick(position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return pokemonList!!.size
    }

    fun swapList(newList: List<MyPokemon?>?) {
        pokemonList = newList
        if (newList != null) {
            notifyDataSetChanged()
        }
    }

    fun filteredPokemon(filteredPokemon: List<MyPokemon?>?) {
        pokemonList = filteredPokemon
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokedexCell: ConstraintLayout
        val name: TextView
        val image: ImageView

        init {
            pokedexCell = itemView.findViewById(R.id.pokedexCell)
            name = itemView.findViewById(R.id.pokemonName)
            image = itemView.findViewById(R.id.pokemonImage)
        }
    }

}