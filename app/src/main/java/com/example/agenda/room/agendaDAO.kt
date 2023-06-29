package com.example.agenda.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//Explicación mas abajo...

@Dao
interface agendaDAO {

    @Query("SELECT * from tareas")
    fun getAll(): Flow<List<agendaData>>

    @Query("SELECT * from tareas WHERE id = :idTarea")
    fun tarea(idTarea: Int): agendaData

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun Guardar(datos: agendaData)

    @Update
    fun actualizar(datos: agendaData)

    @Delete
    suspend fun eliminar(datos: agendaData)

}

/*
El código define una interfaz agendaDAO que está anotada con @Dao.
Esta interfaz contiene métodos que insertan, actualizan, eliminan y consultan datos de
la tabla tareas, que es una entidad definida por la clase agendaData.

Los métodos que insertan, actualizan y eliminan datos están anotados con
@Insert, @Update y @Delete respectivamente. Estas son anotaciones de conveniencia
que te permiten definir estos métodos sin escribir código SQL. Puedes pasar uno o más
objetos de la entidad como parámetros y Room se encarga de insertarlos, actualizarlos o
eliminarlos de la tabla correspondiente.

El método que consulta datos está anotado con @Query y recibe una cadena con la sentencia SQL
que quieres ejecutar. Este método devuelve un objeto Flow que emite una lista de objetos
agendaData cada vez que hay un cambio en la tabla tareas. Esto te permite obtener los datos
de forma asíncrona usando corutinas de Kotlin.*/
