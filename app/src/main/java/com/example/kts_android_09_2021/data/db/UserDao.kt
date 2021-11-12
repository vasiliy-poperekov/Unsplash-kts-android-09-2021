package com.example.kts_android_09_2021.data.db

import androidx.room.*
import com.example.kts_android_09_2021.domain.contracts.UserContract
import com.example.kts_android_09_2021.data.enteties.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserEntity(userEntity: UserEntity)

    @Query("DELETE FROM ${UserContract.TABLE_NAME}")
    suspend fun deleteUserEntity()

    @Query("SELECT * FROM ${UserContract.TABLE_NAME}")
    suspend fun getUserEntity(): UserEntity

    @Update
    suspend fun updateUserEntity(userEntity: UserEntity)
}