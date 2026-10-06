package com.example.remotetv.data.samsung

import android.util.Base64
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume

/** Samsung Tizen remote protocol client. TV and phone must be on the same LAN. */
class SamsungRemoteClient(
    private val tvIp: String,
    private val deviceName: String = "RemoteTV Android"
) {
    private val client = OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).build()
    private var socket: WebSocket? = null
    private var token: String? = null

    suspend fun connect(): Result<Unit> = suspendCancellableCoroutine { continuation ->
        val encodedName = Base64.encodeToString(deviceName.toByteArray(), Base64.NO_WRAP)
        val url = "wss://$tvIp:8002/api/v2/channels/samsung.remote.control?name=$encodedName" +
            (token?.let { "&token=$it" } ?: "")
        val request = Request.Builder().url(url).build()
        socket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                continuation.resume(Result.success(Unit))
            }
            override fun onMessage(webSocket: WebSocket, text: String) {
                runCatching { JSONObject(text).optJSONObject("data")?.optString("token") }
                    .getOrNull()?.takeIf { it.isNotBlank() }?.let { token = it }
            }
            override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
                if (continuation.isActive) continuation.resume(Result.failure(t))
            }
        })
        continuation.invokeOnCancellation { socket?.cancel() }
    }

    fun sendKey(key: String): Boolean {
        val payload = JSONObject().apply {
            put("method", "ms.remote.control")
            put("params", JSONObject().apply {
                put("Cmd", "Click")
                put("DataOfCmd", key)
                put("Option", "false")
                put("TypeOfRemote", "SendRemoteKey")
            })
        }
        return socket?.send(payload.toString()) == true
    }

    fun close() { socket?.close(1000, "User closed") }
}
