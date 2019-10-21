package com.jefftorcato.saudadeadmin.data

import com.jefftorcato.saudadeadmin.data.model.LoggedInUser
import com.jefftorcato.saudadeadmin.networking.HttpApi
import com.jefftorcato.saudadeadmin.providers.ServerInfo
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            //val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            //return Result.Success(fakeUser)
            val API_ENDPOINT : String = ServerInfo.SERVER_ADDRESS + "/api/login"
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            var res : String = HttpApi
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

