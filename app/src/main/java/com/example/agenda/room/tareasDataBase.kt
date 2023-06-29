package com.example.agenda.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Explicación más abajo

@Database(entities = [agendaData::class], version = 1)
abstract class tareasDataBase : RoomDatabase() {

    abstract fun aDAO(): agendaDAO

    companion object{

        @Volatile
        private var INSTANSE: tareasDataBase? = null

        fun getData(context: Context): tareasDataBase{
            val temp = INSTANSE
            if (temp != null){
                return temp
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    tareasDataBase::class.java,
                    "BDT"
                ).build()
                INSTANSE = instance
                return instance
            }
        }

    }

}

/*
El código define una clase abstracta **tareasDataBase** que está anotada con @Database. Esta
clase representa la base de datos de la aplicación y sirve como el punto principal de acceso
a los datos persistidos.

La anotación @Database indica que esta clase es una base de datos de Room y que tiene una entidad
**agendaData** y una versión **1**. Cada entidad corresponde a una tabla en la base de datos.
La versión se usa para controlar las migraciones de la base de datos cuando cambia el esquema.

La clase tareasDataBase tiene un método abstracto **aDAO()** que devuelve una instancia del DAO
**agendaDAO**. Este método permite acceder a los métodos del DAO para insertar, actualizar,
eliminar y consultar datos en la base de datos.

La clase tareasDataBase también tiene un objeto **companion** que contiene una variable
**INSTANSE** y un método **getData()**. La variable INSTANSE es volátil y almacena una
referencia a la instancia única de la base de datos. El método getData recibe un contexto y
devuelve la instancia de la base de datos. Si la instancia no existe, se crea usando el método
**Room.databaseBuilder()** que recibe el contexto, la clase de la base de datos y el nombre de
la base de datos. El método getData está sincronizado para evitar que se creen múltiples
instancias de la base de datos al mismo tiempo.*/
