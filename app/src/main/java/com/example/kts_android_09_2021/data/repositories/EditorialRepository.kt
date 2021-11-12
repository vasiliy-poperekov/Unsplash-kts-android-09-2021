package com.example.kts_android_09_2021.data.repositories

import com.example.kts_android_09_2021.data.db.EditorialDao
import com.example.kts_android_09_2021.data.enteties.EditorialEntity
import com.example.kts_android_09_2021.domain.items.ItemEditorial
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EditorialRepository(
    private val editorialDao: EditorialDao
) {

    suspend fun addEditorialItemsList(editorialItemsList: List<ItemEditorial?>) {
        editorialDao.deleteAllEditorialEntities()
        editorialDao.addListEditorialEntities(
            editorialItemsList.map {
                convertEditorialItemInEntity(it)
            }
        )
    }

    fun getEditorialItemsList(): Flow<List<ItemEditorial>> {
        return editorialDao.getListEditorialEntities().map {
            it.map { editorialEntity ->
                convertEditorialEntityInItem(editorialEntity)
            }
        }
    }

    suspend fun updateItem(itemEditorial: ItemEditorial) {
        editorialDao.updateEditorialEntity(convertEditorialItemInEntity(itemEditorial))
    }

    suspend fun deleteAllItems() {
        editorialDao.deleteAllEditorialEntities()
    }

    private fun convertEditorialItemInEntity(itemEditorial: ItemEditorial?): EditorialEntity {
        return EditorialEntity(
            id = itemEditorial!!.id,
            userName = itemEditorial.userName,
            avatarUrl = itemEditorial.avatarUrl,
            imageUrl = itemEditorial.imageUrl,
            likes = itemEditorial.likes,
            likedByUser = itemEditorial.likedByUser
        )
    }

    private fun convertEditorialEntityInItem(editorialEntity: EditorialEntity): ItemEditorial {
        return ItemEditorial(
            id = editorialEntity.id,
            userName = editorialEntity.userName,
            avatarUrl = editorialEntity.avatarUrl,
            imageUrl = editorialEntity.imageUrl,
            likes = editorialEntity.likes,
            likedByUser = editorialEntity.likedByUser
        )
    }
}