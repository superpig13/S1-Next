package me.ykrank.s1next.util

import android.content.Context
import com.fasterxml.jackson.core.JsonProcessingException
import com.github.ykrank.androidtools.util.ErrorParser
import com.github.ykrank.androidtools.util.L
import io.reactivex.exceptions.CompositeException
import me.ykrank.s1next.R
import me.ykrank.s1next.data.api.ApiException
import okhttp3.internal.http2.StreamResetException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

object ErrorUtil : ErrorParser {

    const val BUGLY_APP_ID = "eae39d8732"

    private val TAG_LOG = ErrorUtil::class.java.simpleName

    override fun parse(context: Context, throwable: Throwable): String {
        var msg: String? = null
        var root: Throwable? = throwable

        while (msg == null && root != null) {
            msg = parseNetError(context, root)
            val cause: Throwable? = throwable.cause
            if (cause === null || cause === root) {
                break
            }
            root = cause
        }
        if (msg == null) {
            L.report(throwable)
            return context.getString(R.string.message_unknown_error)
        }
        return msg
    }

    private fun parseNetError(context: Context, throwable: Throwable): String? {
        var msg: String? = null
        when (throwable) {
            is ApiException -> msg = throwable.getLocalizedMessage()
            is JsonProcessingException -> {
                msg = context.getString(R.string.message_server_data_error)
                L.report(throwable)
            }
            is IOException -> {
                msg = context.getString(R.string.message_network_error)
                L.e(throwable)
            }
            is HttpException -> {
                msg = throwable.getLocalizedMessage()
                if (msg.isNullOrEmpty()) {
                    msg = context.getString(R.string.message_server_connect_error)
                }
            }
            is CompositeException -> {
                for (ex in throwable.exceptions) {
                    val exMsg = parseNetError(context, ex)
                    if (exMsg != null) {
                        msg = exMsg
                        break
                    }
                }
            }
        }
        return msg
    }

    override fun ignoreError(throwable: Throwable): Boolean {
        when (throwable) {
            is UnknownHostException -> return true
            is SocketException -> return true
            is SocketTimeoutException -> return true
            is StreamResetException -> return true
            is SSLException -> return true
        }
        return false
    }
}
