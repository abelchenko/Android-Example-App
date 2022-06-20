package com.example.exampleapplication.service

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject constructor() {
    suspend fun getList(): List<Entity> {
        //Backend interaction and non UI logic
        return emptyList()
    }

    suspend fun getEntity(id: Long): Entity {
        //Backend interaction and non UI logic
        return Entity(-1, "name")
    }
}

data class Entity(val id: Long, val name: String)
