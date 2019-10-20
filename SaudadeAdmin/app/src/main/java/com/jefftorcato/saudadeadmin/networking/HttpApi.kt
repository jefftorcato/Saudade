package com.jefftorcato.saudadeadmin.networking

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class HttpApi {

    fun executePost(targetURL : String, urlParameters: String): String? {

        lateinit var connection : HttpURLConnection

        try {
            val url : URL = URL(targetURL)
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

            connection.setRequestProperty("Content-Length",
                urlParameters.toByteArray().size.toString()
            )
            connection.setRequestProperty("Content-Language", "en-US");

            connection.useCaches = false
            connection.doOutput = true

            //Send Request
            val wr = DataOutputStream(connection.outputStream)
            wr.writeBytes(urlParameters)
            wr.close()

            //Get Response
            val iStream : InputStream = connection.inputStream
            val rd = BufferedReader(InputStreamReader(iStream))
            val response = StringBuilder()
            var line : String? = null

            while ({line = rd.readLine(); line}() != null) {
                response.append(line)
                response.append('\r')
            }
            rd.close()
            return response.toString()

        } catch (e : Exception) {
            e.printStackTrace()
            return null
        } finally {
            connection.disconnect()
        }
    }

    fun executeGet(targetURL: String) : String? {

        lateinit var connection : HttpURLConnection

        try {
            //Create Connection
            val url = URL(targetURL)
            url.openConnection() as HttpURLConnection

            //Get Response
            val iStream : InputStream = connection.inputStream
            val rd = BufferedReader(InputStreamReader(iStream))
            val response = StringBuilder()
            var line : String? = null

            while ({line = rd.readLine(); line}() != null) {
                response.append(line)
                response.append('\r')
            }
            rd.close()
            return response.toString()

        } catch (e : Exception) {
            e.printStackTrace()
            return null
        } finally {
            connection.disconnect()
        }
    }
}