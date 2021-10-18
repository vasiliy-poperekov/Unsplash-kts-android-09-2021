package com.example.kts_android_09_2021.db.repositories

import com.example.kts_android_09_2021.db.Database
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemEditorial
import com.example.kts_android_09_2021.utils.Converter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EditorialRepository {
    private val editorialDao = Database.appDatabase.editorialDao()

    suspend fun addEditorialItemsList(editorialItemsList: List<ItemEditorial?>) {
        editorialDao.deleteAllEditorialEntities()
        editorialDao.addListEditorialEntities(
            editorialItemsList.map {
                Converter.convertEditorialItemInEntity(it)
            }
        )
    }

    fun getEditorialItemsList(): Flow<List<ItemEditorial>> {
        return editorialDao.getListEditorialEntities().map {
            it.map { editorialEntity ->
                Converter.convertEditorialEntityInItem(editorialEntity)
            }
        }
    }

    suspend fun updateItem(itemEditorial: ItemEditorial) {
        editorialDao.updateEditorialEntity(Converter.convertEditorialItemInEntity(itemEditorial))
    }

    suspend fun deleteAllItems() {
        editorialDao.deleteAllEditorialEntities()
    }
}