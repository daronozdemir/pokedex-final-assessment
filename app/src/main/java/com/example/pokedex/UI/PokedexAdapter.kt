package com.example.pokedex.UI

import android.content.Context
import android.content.Intent
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
import com.example.pokedex.Model.Pokemon
import com.example.pokedex.R

class PokedexAdapter(private val context: Context, private var pokemonList: List<Pokemon>?) : RecyclerView.Adapter<PokedexAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.grid_cell, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.pokemonName.text = pokemonList!![viewHolder.adapterPosition].name
        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemonList!![viewHolder.adapterPosition].id + ".png")
                .centerCrop()
                .transition(DrawableTransitionOptions().crossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.pokemonImage)
        viewHolder.pokedexCell.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("name", pokemonList!![viewHolder.adapterPosition].name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return pokemonList!!.size
    }

    fun swapList(newList: List<Pokemon>?) {
        pokemonList = newList
        if (newList != null) {
            notifyDataSetChanged()
        }
    }

    fun filteredPokemon(filteredPokemon: List<Pokemon>?) {
        pokemonList = filteredPokemon
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokedexCell: ConstraintLayout
        val pokemonImage: ImageView
        val pokemonName: TextView

        init {
            pokedexCell = itemView.findViewById(R.id.pokedexCell)
            pokemonImage = itemView.findViewById(R.id.pokemonImage)
            pokemonName = itemView.findViewById(R.id.pokemonName)
        }
    }

}