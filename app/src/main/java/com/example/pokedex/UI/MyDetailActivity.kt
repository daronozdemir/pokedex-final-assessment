package com.example.pokedex.UI

import android.app.Service
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
import com.example.pokedex.R
import com.example.pokedex.UI.MyDetailActivity
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MyDetailActivity : AppCompatActivity(), SensorEventListener {
    val ID = "#ID "
    private val executor: Executor = Executors.newSingleThreadExecutor()
    private var myPokemonViewModel: MyPokemonViewModel? = null
    private var sensorManager: SensorManager? = null
    private var sensor: Sensor? = null
    private var pokemon: MyPokemon? = null
    private var pokeFrontImage: ImageView? = null
    private var pokeBackImage: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_detail)
        val toolbar = findViewById<Toolbar>(R.id.toolbarMyDetail)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
        initViews()
    }

    private fun initViews() {
        myPokemonViewModel = ViewModelProviders.of(this).get(MyPokemonViewModel::class.java)
        pokemon = intent.getSerializableExtra("pokemon") as MyPokemon
        val name = findViewById<TextView>(R.id.myPokeName)
        val id = findViewById<TextView>(R.id.myId)
        val weight = findViewById<TextView>(R.id.myWeight)
        val height = findViewById<TextView>(R.id.myHeight)
        val hp = findViewById<TextView>(R.id.myHp)
        val attack = findViewById<TextView>(R.id.myAttack)
        val defence = findViewById<TextView>(R.id.myDefence)
        val speed = findViewById<TextView>(R.id.mySpeed)
        val specialAttack = findViewById<TextView>(R.id.mySpecialAttack)
        val specialDefence = findViewById<TextView>(R.id.mySpecialDefence)
        pokeFrontImage = findViewById(R.id.myPokeFrontImage)
        pokeBackImage = findViewById(R.id.myPokeBackImage)
        val type = findViewById<TextView>(R.id.myTypeTv)
        val type1 = findViewById<TextView>(R.id.myType1)
        val type2 = findViewById<TextView>(R.id.myType2)
        val description = findViewById<TextView>(R.id.myDescription)
        val color = findViewById<TextView>(R.id.myColor)
        val addPokemon = findViewById<FloatingActionButton>(R.id.deletePokemon)
        sensorManager = getSystemService(Service.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
        name.text = pokemon!!.name
        id.text = ID + java.lang.String.valueOf(pokemon!!.id)
        weight.text = java.lang.String.valueOf(pokemon!!.weight)
        height.text = java.lang.String.valueOf(pokemon!!.height)
        hp.text = java.lang.String.valueOf(pokemon!!.hp)
        attack.text = java.lang.String.valueOf(pokemon!!.attack)
        defence.text = java.lang.String.valueOf(pokemon!!.defence)
        speed.text = java.lang.String.valueOf(pokemon!!.speed)
        specialAttack.text = java.lang.String.valueOf(pokemon!!.specialAttack)
        specialDefence.text = java.lang.String.valueOf(pokemon!!.specialDefence)
        type1.text = pokemon!!.type
        type2!!.text = pokemon!!.type2
        color.text = pokemon!!.color
        description.text = pokemon!!.description
        if (type2 == null) {
            type!!.setText(R.string.type)
            type2.visibility = View.INVISIBLE
        } else type?.setText(R.string.types)
        Glide.with(applicationContext)
                .load(pokemon!!.frontImage)
                .centerCrop()
                .transition(DrawableTransitionOptions().crossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(pokeFrontImage!!)
        Glide.with(applicationContext)
                .load(pokemon!!.backImage)
                .centerCrop()
                .transition(DrawableTransitionOptions().crossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(pokeBackImage!!)
        addPokemon.setOnClickListener {
            val alert = AlertDialog.Builder(this@MyDetailActivity, R.style.MyDialogTheme)
            alert.setTitle(R.string.myPokemon)
                    .setMessage("Are you sure you want to delete " + pokemon!!.name!!.toUpperCase() + " from your Pokédex?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, which ->
                        deletePokemon(pokemon)
                        finish()
                        showToast(pokemon!!.name!!.toUpperCase() + " is deleted from your Pokédex.")
                    }
                    .setNegativeButton("No") { dialog, _ -> dialog.cancel() }.create().show()
        }
    }

    private fun deletePokemon(pokemon: MyPokemon?) {
        executor.execute { myPokemonViewModel!!.delete(pokemon) }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_my_detail, menu)
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
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/" + pokemon!!.id + ".png")
                    .centerCrop()
                    .transition(DrawableTransitionOptions().crossFade())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(pokeFrontImage!!)
            Glide.with(applicationContext)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/" + pokemon!!.id + ".png")
                    .centerCrop()
                    .transition(DrawableTransitionOptions().crossFade())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(pokeBackImage!!)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
}