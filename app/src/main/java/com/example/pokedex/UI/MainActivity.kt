package com.example.pokedex.UI

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.pokedex.Model.Pokemon
import com.example.pokedex.R
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var bottomNavigationView: BottomNavigationView
    private var pokemonList: List<Pokemon>? = ArrayList()
    private var sortedList: List<Pokemon>? = ArrayList()
    private var filteredList: List<Pokemon>? = ArrayList()
    private var pokedexAdapter: PokedexAdapter? = null
    private lateinit var viewModel: MainActivityViewModel
    private var limit = 0
    var isLimit = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        bottomNavigationView = findViewById(R.id.pokedexNav)
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.action_MyPokemon) {
                val intent = Intent(this@MainActivity, MyPokemon::class.java)
                startActivity(intent)
            }
            false
        })
        initObservers()
        initRecyclerView()
        sort()
    }

    private fun initRecyclerView() {
        pokedexAdapter = PokedexAdapter(this, pokemonList)
        recyclerView = findViewById(R.id.pokeList)
        recyclerView.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = pokedexAdapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                    if (isLimit) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            isLimit = false
                            limit = viewModel.count
                            viewModel.getPokemons(limit)
                        }
                    }
                }
            }
        })
        isLimit = true
        limit = 20
        viewModel.getPokemons(limit)
    }

    private fun initObservers() {
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.error.observe(this, Observer { s -> showToast(s) })
        viewModel.pokemon.observe(this, Observer { pokemons ->
            pokemonList = pokemons
            updateUI()
        })
    }

    private fun updateUI() {
        if (pokedexAdapter == null) {
            pokedexAdapter = PokedexAdapter(this, pokemonList)
            recyclerView.adapter = pokedexAdapter
        } else {
            pokedexAdapter!!.swapList(pokemonList)
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG)
                .show()
    }

    private fun filter(query: String) {
        val filteredPokemon: MutableList<Pokemon> = ArrayList()
        filteredList = if (pokemonList!!.size < 150) {
            pokemonList
        } else {
            sortedList
        }
        for (pokemon in filteredList!!) {
            if (pokemon.name.toLowerCase().contains(query.toLowerCase())) {
                filteredPokemon.add(pokemon)
            }
        }
        pokedexAdapter!!.filteredPokemon(filteredPokemon)
    }

    private fun sort() {
        val spinner = findViewById<View>(R.id.typeFilter) as Spinner
        val arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.sort_types, R.layout.simple_spinner_item)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                if (pokemonList!!.size < 150) {
                    viewModel.getPokemons(viewModel.count)
                } else {
                    when (position) {
                        0 -> {
                            sortedList = pokemonList
                            pokedexAdapter!!.filteredPokemon(pokemonList)
                        }
                        1 -> {
                            sortedList = pokemonList!!.subList(0, 151)
                            pokedexAdapter!!.filteredPokemon(sortedList)
                        }
                        2 -> {
                            sortedList = pokemonList!!.subList(151, 251)
                            pokedexAdapter!!.filteredPokemon(sortedList)
                        }
                        3 -> {
                            sortedList = pokemonList!!.subList(251, 386)
                            pokedexAdapter!!.filteredPokemon(sortedList)
                        }
                        4 -> {
                            sortedList = pokemonList!!.subList(386, 493)
                            pokedexAdapter!!.filteredPokemon(sortedList)
                        }
                        5 -> {
                            sortedList = pokemonList!!.subList(493, 649)
                            pokedexAdapter!!.filteredPokemon(sortedList)
                        }
                        6 -> {
                            sortedList = pokemonList!!.subList(649, 721)
                            pokedexAdapter!!.filteredPokemon(sortedList)
                        }
                        7 -> {
                            sortedList = pokemonList!!.subList(721, 807)
                            pokedexAdapter!!.filteredPokemon(sortedList)
                        }
                        8 -> {
                            sortedList = pokemonList!!.subList(807, pokemonList!!.size)
                            pokedexAdapter!!.filteredPokemon(sortedList)
                        }
                        else -> {
                            sortedList = pokemonList
                            pokedexAdapter!!.filteredPokemon(pokemonList)
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                pokedexAdapter!!.filteredPokemon(pokemonList)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        val menuSearch = menu.findItem(R.id.action_search)
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
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)
    }
}
