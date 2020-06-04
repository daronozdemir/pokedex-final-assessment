package com.example.pokedex.UI

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.pokedex.Model.MyPokemon
import com.example.pokedex.R
import com.example.pokedex.UI.MyPokedexAdapter.OnItemClickListner
import java.util.*

class MyPokemon : AppCompatActivity() {
    private var pokedexAdapter: MyPokedexAdapter? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MyPokemonViewModel
    private var pokemonList: List<MyPokemon?>? = ArrayList()
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_pokemon)
        val toolbar = findViewById<Toolbar>(R.id.toolbarMyPokemon)
        setSupportActionBar(toolbar)
        bottomNavigationView = findViewById(R.id.myPokemonNav)
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.action_Pokedex) {
                finish()
            }
            false
        })
        initObservers()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        pokedexAdapter = MyPokedexAdapter(this, pokemonList)
        recyclerView = findViewById(R.id.myPokeList)
        recyclerView.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = pokedexAdapter
        viewModel.pokemons
    }

    private fun initObservers() {
        viewModel = ViewModelProviders.of(this).get(MyPokemonViewModel::class.java)
        viewModel.pokemons?.observe(this, Observer { pokemons ->
            pokemonList = pokemons
            updateUI()
        })
    }

    private fun updateUI() {
        if (pokedexAdapter == null) {
            pokedexAdapter = MyPokedexAdapter(this, pokemonList)
            recyclerView.adapter = pokedexAdapter
        } else {
            pokedexAdapter!!.swapList(pokemonList)
        }
    }
}