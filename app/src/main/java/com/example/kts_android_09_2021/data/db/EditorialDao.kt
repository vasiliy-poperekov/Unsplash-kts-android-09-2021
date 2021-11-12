package com.example.kts_android_09_2021.data.db

import androidx.room.*
import com.example.kts_android_09_2021.domain.contracts.EditorialContract
import com.example.kts_android_09_2021.data.enteties.EditorialEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EditorialDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListEditorialEntities(editorialsList: List<EditorialEntity>)

    @Query("DELETE FROM ${EditorialContract.TABLE_NAME}")
    suspend fun deleteAllEditorialEntities()

    @Query("SELECT * FROM ${EditorialContract.TABLE_NAME}")
    fun getListEditorialEntities(): Flow<List<EditorialEntity>>

    @Update
    suspend fun updateEditorialEntity(editorialEntity: EditorialEntity)
}