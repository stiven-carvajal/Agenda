package com.example.agenda

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.agenda.MainActivity.Companion.CHANNEL_ID
import com.example.agenda.MainActivity.Companion.TaskOne
import com.example.agenda.MainActivity.Companion.flag
import com.example.agenda.databinding.ActivitySetTaskBinding
import com.example.agenda.room.agendaData
import com.example.agenda.viewModel.aViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.logging.SimpleFormatter

class setTask : AppCompatActivity() {
    private lateinit var binding: ActivitySetTaskBinding
    private lateinit var viewModel: aViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySetTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Obtener ViewModel
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(aViewModel::class.java)
        setDateListener()

        if(flag){
            editSet()
        }
    }

    //Para crear el menu de la pantalla principal.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)

        //Si no se va a editar, entonces removemos eliminar
        if(!flag){
            menu?.removeItem(R.id.delete_task)
        }

        return super.onCreateOptionsMenu(menu)
    }

    //Función que gestiona la accion de los items en el menu cuando se pulsan.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.save_task ->{
                //Validaciones de campos.
                if(binding.TaskET.text.toString().isEmpty()){
                    binding.TaskET.error = "Obligatorio"
                    binding.TaskET.requestFocus()
                }else if(binding.dFecha.text.toString().isEmpty()) {
                    binding.dFecha.error = "Obligatorio"
                    binding.dFecha.requestFocus()
                }else {
                    setTimer()

                    if (flag == false) {
                        Guardar()
                    } else {
                        Actualizar()
                    }
                }
                return true
            }
            R.id.delete_task ->{
                Eliminar()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //Un listener para lanzar nuestro DatePicker.
    fun setDateListener(){
        binding.dFecha.setOnClickListener{
            showDatePicker()
        }
    }

    //Mostrar nuestro datePicker
    fun showDatePicker(){
        val datePicker = dataPickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    //Aquí le establecemos el valor a nuestro campo fecha una vez se haya seleccionado 1 día en el dataPicker.
    fun onDateSelected(day: Int, month: Int, year: Int) {
        var mes = month
        mes += 1
        binding.dFecha.setText("$day/$mes/$year")
    }

    //Guardar la tarea.
    fun Guardar(){


        val tarea = binding.TaskET.text.toString()
        val fecha = binding.dFecha.text.toString()

        val nuevaTarea = agendaData(0, tarea, fecha)

        viewModel.Guardar(nuevaTarea)
        finish()
    }

    //En caso de edición, esta función se encarga de establecer los valores a los campos.
    //para cuando ya se vaya a editar.
    fun editSet(){
        val tarea = TaskOne

        binding.TaskET.setText(tarea.tarea)
        binding.dFecha.setText(tarea.fecha)
    }

    //Actualizar la tarea.
    fun Actualizar(){

        val tarea = TaskOne
        tarea.tarea = binding.TaskET.text.toString()
        tarea.fecha = binding.dFecha.text.toString()

        viewModel.actualizar(tarea)
        finish()

    }

    //Eliminar nuestra tarea.
    fun Eliminar(){

        val tarea = TaskOne
        viewModel.eliminar(tarea)
        finish()

    }

    //Función que se encargaría de esstablecer 1 notificación por tarea creada,
    //Esta mal implementada, y hay que mejorarla mas, en caso de que se pueda.
    //No sirve XD.
    fun setTimer(){
        // Definimos una intencion para lanzar la actividad taskId
        val intent = Intent (this, taskId::class.java).apply {
            // Le agregamos flags para iniciar una nueva tarea y limpiar la pila de tareas
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // Creamos un PendingIntent para la notificacion, verificando la version de Android
        val pendingIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Si es Android 6 o superior, creamos el PendingIntent con ciertos flags
            PendingIntent.getActivity (this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        } else {
            // Sino, solo le pasamos el flag de actualizar
            PendingIntent.getActivity (this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        //Creamos la notificacion con el builder, indicando el icono, titulo, texto, etc. Y agregamos el PendingIntent
        val builder = NotificationCompat.Builder (this, CHANNEL_ID)
            .setSmallIcon (R.drawable.notify_import)
            .setContentTitle ("Recordatorio")
            .setContentText (binding.TaskET.text)
            .setPriority (NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent (pendingIntent)
            .setAutoCancel (true)

        //Obtenemos la fecha del calendario
        val calendar = Calendar.getInstance()

        // Calculamos la diferencia entre la fecha del reminder y la actual
        val diff = dateFormater().timeInMillis - calendar.timeInMillis

        //Obtenemos el AlarmManager
        val alarmManager = getSystemService (Context.ALARM_SERVICE) as AlarmManager
        //Seteamos la alarma para la fecha calculada
        alarmManager.set (AlarmManager.RTC_WAKEUP,calendar.timeInMillis + diff,pendingIntent)

        //Obtenemos el NotificationManager
        val notificationManager: NotificationManager =
            ContextCompat.getSystemService (this, NotificationManager::class.java) as NotificationManager
        //Mostramos la notificacion
        notificationManager.notify (1, builder.build ())
    }

    //Esta funcion se uso para devolver un tipo calendario para calcular en factor de
    //milisegundos.
    fun dateFormater(): Calendar{
        val fechaString = binding.dFecha.text.toString()
        val formatter = SimpleDateFormat("dd/mm/yyyy")
        val date = formatter.parse(fechaString)
        val calendar = Calendar.getInstance()
        calendar.setTime(date)
        return calendar
    }

}