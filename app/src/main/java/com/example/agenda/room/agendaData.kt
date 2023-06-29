package com.example.agenda.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*Explicación abajo*/

@Entity(tableName = "tareas")
data class agendaData(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "tarea") var tarea: String,
    @ColumnInfo(name = "fecha") var fecha: String,
)

/*El código define una clase **agendaData** que está anotada con @Entity. Esta clase representa
 un objeto que quieres almacenar en la base de datos. Cada instancia de esta clase corresponde
 a una fila de datos en la tabla asociada.

La anotación @Entity indica que esta clase es una entidad y que la tabla se llama **tareas**.
 Por defecto, Room usa el nombre de la clase como el nombre de la tabla, pero puedes cambiarlo
 con la propiedad tableName.

La clase agendaData tiene tres campos: **id**, **tarea** y **fecha**. Cada campo corresponde
 a una columna en la tabla **tareas**. Por defecto, Room usa el nombre del campo como el nombre
 de la columna, pero puedes cambiarlo con la anotación @ColumnInfo y la propiedad name.

El campo **id** está anotado con @PrimaryKey y tiene la propiedad autoGenerate en true.
Esto significa que este campo es la clave primaria que identifica de forma única cada fila
en la tabla y que Room asigna automáticamente un valor a este campo cuando se inserta una
nueva instancia de agendaData.

Los campos **tarea** y **fecha** son cadenas que almacenan el nombre y la fecha de una
tarea. Estos campos no tienen ninguna anotación especial, así que Room los trata como
columnas normales.
*/