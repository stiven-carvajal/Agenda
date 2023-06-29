package com.example.agenda.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.agenda.repository.agenda_repo
import com.example.agenda.room.agendaData
import com.example.agenda.room.tareasDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//Explicación más abajo.

class aViewModel(application: Application) : AndroidViewModel(application) {
    val Tareas: LiveData<List<agendaData>>
    val repository: agenda_repo

    init {
        val agendaDAO = tareasDataBase.getData(application).aDAO()
        repository = agenda_repo(agendaDAO)
        Tareas = repository.getAll()
    }

    suspend fun tarea(idTarea: Int) : agendaData =
        withContext(Dispatchers.IO){
            repository.tarea(idTarea)
        }

    fun Guardar(tarea: agendaData) =
        viewModelScope.launch(Dispatchers.IO) { repository.Guardar(tarea) }

    fun actualizar(tarea: agendaData) =
        viewModelScope.launch(Dispatchers.IO) { repository.actualizar(tarea) }

    fun eliminar(tarea: agendaData) =
        viewModelScope.launch(Dispatchers.IO){ repository.eliminar(tarea) }
}

/* define una clase llamada **aViewModel** que hereda de la clase **AndroidViewModel**.
Esta clase se utiliza para almacenar y administrar datos relacionados con la interfaz de
 usuario. La clase tiene las siguientes propiedades y funciones:

- **Tareas**: una variable de tipo LiveData que almacena una lista de objetos **agendaData**.
LiveData es una clase que permite observar los cambios en los datos.

- **repository**: una variable de tipo **agenda_repo** que se inicializa con un objeto
**agendaDAO**. Este objeto se obtiene de la clase **tareasDataBase**, que es una base d
e datos Room. Room es una biblioteca que facilita el acceso a los datos almacenados en SQLite.

- **init**: un bloque que se ejecuta cuando se crea una instancia de la clase. Aquí se
 asignan los valores iniciales a las propiedades **Tareas** y **repository**.

- **tarea**: una función suspendida que recibe un parámetro **idTarea** de tipo Int y devuelve
 un objeto **agendaData**. Esta función se ejecuta en el contexto de Dispatchers.IO, que es un
 grupo de subprocesos diseñado para realizar operaciones de entrada/salida². La función llama
 al método **tarea** del repositorio y devuelve el resultado.

- **Guardar**: una función que recibe un parámetro **tarea** de tipo **agendaData** y no
devuelve nada. Esta función se ejecuta en el ámbito del viewModelScope, que es un ámbito de
 corutina vinculado al ciclo de vida del ViewModel. La función llama al método **Guardar**
 del repositorio para insertar o actualizar la tarea en la base de datos.

- **actualizar**: una función que recibe un parámetro **tarea** de tipo **agendaData** y
no devuelve nada. Esta función se ejecuta en el ámbito del viewModelScope y llama al método
**actualizar** del repositorio para modificar la tarea en la base de datos.

- **eliminar**: una función que recibe un parámetro **tarea** de tipo **agendaData** y no
devuelve nada. Esta función se ejecuta en el ámbito del viewModelScope y llama al método
**eliminar** del repositorio para borrar la tarea de la base de datos.
*/