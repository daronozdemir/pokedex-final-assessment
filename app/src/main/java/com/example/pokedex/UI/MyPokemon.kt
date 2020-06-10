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

class MyPokemon : AppCompatActivity(), OnItemClickListner {
    private var pokedexAdapter: MyPokedexAdapter? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit  var viewModel: MyPokemonViewModel
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
        pokedexAdapter!!.setOnItemClickListner(this)
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

    private fun filter(query: String) {
        val filteredPokemon: MutableList<MyPokemon?> = ArrayList()
        for (pokemon in pokemonList!!) {
            if (pokemon!!.name!!.toLowerCase().contains(query.toLowerCase())) {
                filteredPokemon.add(pokemon)
            }
        }
        pokedexAdapter!!.filteredPokemon(filteredPokemon)
    }

    private fun showToast(message: String) {
        Toast.makeText(this@MyPokemon, message, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_my_pokemon, menu)
        val menuSearch = menu.findItem(R.id.action_my_search)
        val searchView = menuSearch.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                filter(s)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_delete) {
            val alert = AlertDialog.Builder(this, R.style.MyDialogTheme)
            alert.setTitle(R.string.myPokemon)
                    .setMessage("Are you sure you want to delete your Pokédex?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, which ->
                        viewModel.deleteAllPokemons(pokemonList)
                        showToast("Pokédex deleted.")
                    }
                    .setNegativeButton("No") { dialog, which -> dialog.cancel() }
                    .create().show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun OnItemClick(position: Int) {
        val intent = Intent(this, MyDetailActivity::class.java)
        val myPokemon = pokemonList!![position]
        intent.putExtra("pokemon", myPokemon)
        startActivity(intent)
    }
}