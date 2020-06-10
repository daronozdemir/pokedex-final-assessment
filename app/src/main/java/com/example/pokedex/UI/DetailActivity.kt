package com.example.pokedex.UI

import android.app.Service
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.pokedex.Model.MyPokemon
import com.example.pokedex.Model.PokemonDetail
import com.example.pokedex.Model.PokemonSpecies
import com.example.pokedex.R
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class DetailActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var viewModel: DetailActivityViewModel
    private var name: TextView? = null
    private var id: TextView? = null
    private var height: TextView? = null
    private var weight: TextView? = null
    private var hp: TextView? = null
    private var attack: TextView? = null
    private var defence: TextView? = null
    private var speed: TextView? = null
    private var specialAttack: TextView? = null
    private var specialDefence: TextView? = null
    private var type: TextView? = null
    private var type1: TextView? = null
    private var type2: TextView? = null
    private var description: TextView? = null
    private var color: TextView? = null
    private var pokeFrontImage: ImageView? = null
    private var pokeBackImage: ImageView? = null
    private var pokemonName: String? = null
    private var addPokemon: FloatingActionButton? = null
    private var pokemonDetail: PokemonDetail? = null
    private var pokemonSpecie: PokemonSpecies? = PokemonSpecies()
    private val executor: Executor = Executors.newSingleThreadExecutor()
    private var myPokemonViewModel: MyPokemonViewModel? = null
    private var sensorManager: SensorManager? = null
    private var sensor: Sensor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val toolbar = findViewById<Toolbar>(R.id.toolbarDetail)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
        initViews()
        initObservers()
        viewModel.getPokemons(pokemonName)
        viewModel.getPokemonSpecies(pokemonName)
    }

    private fun initViews() {
        name = findViewById(R.id.name)
        id = findViewById(R.id.id)
        weight = findViewById(R.id.weight)
        height = findViewById(R.id.height)
        hp = findViewById(R.id.hp)
        attack = findViewById(R.id.attack)
        defence = findViewById(R.id.defence)
        speed = findViewById(R.id.speed)
        specialAttack = findViewById(R.id.specialAttack)
        specialDefence = findViewById(R.id.specialDefence)
        pokeFrontImage = findViewById(R.id.pokeFrontImage)
        pokeBackImage = findViewById(R.id.pokeBackImage)
        type = findViewById(R.id.typeTv)
        type1 = findViewById(R.id.type1)
        type2 = findViewById(R.id.type2)
        description = findViewById(R.id.description)
        color = findViewById(R.id.color)
        addPokemon = findViewById(R.id.addPokemon)
        myPokemonViewModel = ViewModelProviders.of(this).get(MyPokemonViewModel::class.java)
        sensorManager = getSystemService(Service.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
        if (intent.hasExtra("name")) {
            pokemonName = intent.getStringExtra("name")
        }
    }

    private fun initObservers() {
        viewModel = ViewModelProviders.of(this).get(DetailActivityViewModel::class.java)
        viewModel.error.observe(this, Observer { s -> showToast(s) })
        viewModel.pokemon.observe(this, Observer { pokemonDetails ->
            pokemonDetail = pokemonDetails
            name!!.text = pokemonDetail!!.name
            id!!.text = "ID #" + pokemonDetail!!.id
            Glide.with(applicationContext)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemonDetail!!.id + ".png")
                    .centerCrop()
                    .transition(DrawableTransitionOptions().crossFade())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(pokeFrontImage!!)
            Glide.with(applicationContext)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/" + pokemonDetail!!.id + ".png")
                    .centerCrop()
                    .transition(DrawableTransitionOptions().crossFade())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(pokeBackImage!!)
            height!!.text = "${pokemonDetail!!.height} M"
            weight!!.text = "${pokemonDetail!!.weight} KG"
            hp!!.text = pokemonDetail!!.stats[5].base_stat.toString()
            attack!!.text = java.lang.String.valueOf(pokemonDetail!!.stats[4].base_stat)
            defence!!.text = java.lang.String.valueOf(pokemonDetail!!.stats[3].base_stat)
            speed!!.text = java.lang.String.valueOf(pokemonDetail!!.stats[0].base_stat)
            specialAttack!!.text = java.lang.String.valueOf(pokemonDetail!!.stats[2].base_stat)
            specialDefence!!.text = java.lang.String.valueOf(pokemonDetail!!.stats[1].base_stat)
            if (pokemonDetail!!.types.size > 1) {
                type!!.text = getString(R.string.types)
                type1!!.text = pokemonDetail!!.types[0].type!!.name
                type2!!.text = pokemonDetail!!.types[1].type!!.name
            } else {
                type!!.text = getString(R.string.type)
                type1!!.text = pokemonDetail!!.types[0].type!!.name
                type2!!.visibility = View.INVISIBLE
            }
        })
        viewModel.pokemonSpecie.observe(this, Observer { pokemonSpecies ->
            pokemonSpecie = pokemonSpecies
            if (pokemonSpecie!!.flavorTextEntries!!.size < 60) {
                description!!.text = pokemonSpecie!!.flavorTextEntries!![1].flavorText
            } else if (pokemonSpecie!!.flavorTextEntries!!.size > 60) {
                description!!.text = pokemonSpecie!!.flavorTextEntries!![2].flavorText
            }
            color!!.text = pokemonSpecie!!.color!!.name
        })
        addPokemon!!.setOnClickListener {
            val alert = AlertDialog.Builder(this@DetailActivity, R.style.MyDialogTheme)
            alert.setTitle(R.string.app_name)
                    .setMessage("Do you want to add " + pokemonDetail!!.name!!.toUpperCase() + " to your Pokédex?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, which ->
                        val id = pokemonDetail!!.id
                        val name = pokemonDetail!!.name
                        val frontImage = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemonDetail!!.id + ".png"
                        val backImage = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/" + pokemonDetail!!.id + ".png"
                        val height = pokemonDetail!!.height
                        val weight = pokemonDetail!!.weight
                        val hp = pokemonDetail!!.stats[5].base_stat
                        val attack = pokemonDetail!!.stats[4].base_stat
                        val defence = pokemonDetail!!.stats[3].base_stat
                        val speed = pokemonDetail!!.stats[0].base_stat
                        val specialAttack = pokemonDetail!!.stats[2].base_stat
                        val specialDefence = pokemonDetail!!.stats[1].base_stat
                        val type1: String?
                        val type2: String?
                        if (pokemonDetail!!.types.size > 1) {
                            type1 = pokemonDetail!!.types[0].type!!.name
                            type2 = pokemonDetail!!.types[1].type!!.name
                        } else {
                            type1 = pokemonDetail!!.types[0].type!!.name
                            type2 = null
                        }
                        val color = pokemonSpecie!!.color!!.name
                        var description: String? = null
                        if (pokemonSpecie!!.flavorTextEntries!!.size < 60) {
                            description = pokemonSpecie!!.flavorTextEntries!![1].flavorText
                        } else if (pokemonSpecie!!.flavorTextEntries!!.size > 60) {
                            description = pokemonSpecie!!.flavorTextEntries!![2].flavorText
                        }
                        val pokemon = MyPokemon(id, name, frontImage, backImage, height, weight, hp, attack, defence, speed, specialAttack, specialDefence, type1, type2, color, description)
                        insertPokemon(pokemon)
                        showToast(name.toUpperCase() + " is added to your Pokédex.")
                    }
                    .setNegativeButton("No") { dialog, which -> dialog.cancel() }
                    .create().show()
        }
    }

    private fun insertPokemon(pokemon: MyPokemon) {
        executor.execute { myPokemonViewModel!!.insert(pokemon) }
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.values[0] > 20000) {
            Glide.with(applicationContext)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/" + pokemonDetail?.id + ".png")
                    .centerCrop()
                    .transition(DrawableTransitionOptions().crossFade())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(pokeFrontImage!!)
            Glide.with(applicationContext)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/" + pokemonDetail?.id + ".png")
                    .centerCrop()
                    .transition(DrawableTransitionOptions().crossFade())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(pokeBackImage!!)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
}