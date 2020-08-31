package co.scifin.ws

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.http.cio.websocket.Frame
import kotlinx.coroutines.channels.filterNotNull
import kotlinx.coroutines.channels.map
import kotlinx.coroutines.runBlocking

object WsClientApp
{
    @JvmStatic
    fun main(args: Array<String>)
    {
        runBlocking {
            val client = HttpClient(CIO).config { install(WebSockets) }

            client.ws(method = HttpMethod.Get, host = "127.0.0.1", port = 8080, path = "/myws/echo")
            {
                send(Frame.Text("Hello World"))

                for (message in incoming.map { it as? Frame.Text }.filterNotNull())
                {
                    println("Server said: "+message.readText())
                }
            }
        }
    }
}
