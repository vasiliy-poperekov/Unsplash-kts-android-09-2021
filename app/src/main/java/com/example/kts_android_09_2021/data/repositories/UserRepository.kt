package com.example.kts_android_09_2021.data.repositories

import com.example.kts_android_09_2021.data.db.UserDao
import com.example.kts_android_09_2021.data.enteties.UserEntity
import com.example.kts_android_09_2021.domain.enteties.profile.AuthorizedUser

class UserRepository(
    private val userDao: UserDao
) {

    suspend fun addAuthorizedUser(authorizedUser: AuthorizedUser) {
        userDao.addUserEntity(
            convertAuthorizedUserInUserEntity(authorizedUser)
        )
    }

    suspend fun getAuthorizedUser(): AuthorizedUser {
        return convertUserEntityInAuthorizedUser(userDao.getUserEntity())
    }

    suspend fun updateAuthorizedUser(authorizedUser: AuthorizedUser) {
        userDao.updateUserEntity(
            convertAuthorizedUserInUserEntity(authorizedUser)
        )
    }

    suspend fun deleteAuthorizedUser() {
        userDao.deleteUserEntity()
    }

    private fun convertUserEntityInAuthorizedUser(userEntity: UserEntity): AuthorizedUser =
        AuthorizedUser(
            id = userEntity.id,
            username = userEntity.username,
            firstName = userEntity.firstName,
            lastName = userEntity.lastName,
            portfolioUrl = userEntity.portfolioUrl,
            location = userEntity.location,
            avatarPhoto = userEntity.avatarPhoto,
            bio = userEntity.bio,
            totalPhotos = userEntity.totalPhotos,
            totalLikes = userEntity.totalLikes
        )

    private fun convertAuthorizedUserInUserEntity(authorizedUser: AuthorizedUser): UserEntity =
        UserEntity(
            id = authorizedUser.id,
            username = authorizedUser.username,
            firstName = authorizedUser.firstName,
            lastName = authorizedUser.lastName,
            portfolioUrl = authorizedUser.portfolioUrl,
            location = authorizedUser.location,
            avatarPhoto = authorizedUser.avatarPhoto,
            bio = authorizedUser.bio,
            totalPhotos = authorizedUser.totalPhotos,
            totalLikes = authorizedUser.totalLikes
        )
}