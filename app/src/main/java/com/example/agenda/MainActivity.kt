package com.example.agenda

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agenda.databinding.ActivityMainBinding
import com.example.agenda.room.agendaData
import com.example.agenda.viewModel.aViewModel
import java.util.Vector

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var agendaAdapter: adapterAgenda
    private lateinit var viewModel: aViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()
        binding.agendaRec.layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(aViewModel::class.java)

        setObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //Pondremos un observador que nos notifique si ha habído cambios en la lista "Tareas"
    fun setObserver(){
        viewModel.Tareas.observe(this){ tareas ->
            tareas?.let {
                agendaAdapter = adapterAgenda(it)
                binding.agendaRec.adapter = agendaAdapter
                agendaAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.add_task -> {
                val intent = Intent(this, setTask::class.java)
                flag = false
                startActivity(intent)

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //Objetos compartidos
    companion object{
        var flag = false
        lateinit var TaskOne: agendaData
        const val CHANNEL_ID = "reminder_channel"//El id de nuestro reminder channel
    }

    //Comprueba si la version de Android es Oreo o superior
    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Si lo es, crea un canal de notificaciones
            //Le asigna un nombre y una descripcion
            val name = "Tienes algo importante!!!"
            val descriptionText = "XDXDXD"
            val importance = NotificationManager.IMPORTANCE_DEFAULT //Importancia por defecto
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            //Registra el canal con el sistema
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}