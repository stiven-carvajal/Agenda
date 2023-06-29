package com.example.agenda.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.agenda.room.agendaDAO
import com.example.agenda.room.agendaData

//Explicación hasta abajo...

class  agenda_repo constructor(
    private val aDAO: agendaDAO
) {

    fun getAll(): LiveData<List<agendaData>> = aDAO.getAll().asLiveData()

    fun tarea(idTarea: Int): agendaData = aDAO.tarea(idTarea)

    suspend fun Guardar(tarea: agendaData){
        aDAO.Guardar(tarea)
    }

    fun actualizar(tarea: agendaData){
        aDAO.actualizar(tarea)
    }

    suspend fun eliminar(tarea: agendaData){
        aDAO.eliminar(tarea)
    }
}

/*
definimos una clase llamada agenda_repo que tiene un constructor que recibe un objeto de
tipo agendaDAO.

getAll(): devuelve un objeto LiveData que contiene una lista de objetos de tipo agendaData,
que se obtienen del objeto agendaDAO usando el método getAll() y el método asLiveData().

tarea(idTarea: Int): devuelve un objeto de tipo agendaData que corresponde a la tarea
con el idTarea dado, que se obtiene del objeto agendaDAO usando el método tarea(idTarea: Int).

Guardar(tarea: agendaData): usa el método suspend para ejecutar una operación en segundo plano
que guarda el objeto tarea de tipo agendaData en el objeto agendaDAO usando el método
Guardar(tarea: agendaData).

actualizar(tarea: agendaData): actualiza el objeto tarea de tipo agendaData en el objeto
agendaDAO usando el método actualizar(tarea: agendaData).

eliminar(tarea: agendaData): usa el método suspend para ejecutar una operación en segundo plano
 que elimina el objeto tarea de tipo agendaData del objeto agendaDAO usando el método
 eliminar(tarea: agendaData).*/
