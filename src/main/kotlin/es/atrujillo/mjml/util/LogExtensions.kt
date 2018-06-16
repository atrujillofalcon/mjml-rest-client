package es.atrujillo.mjml.util

import org.slf4j.LoggerFactory

internal fun Any.logDebug(msg: String) {
    LoggerFactory.getLogger(this::class.java).debug(msg)
}

internal fun Any.logInfo(msg: String) {
    LoggerFactory.getLogger(this::class.java).info(msg)
}

internal fun Any.logWarn(msg: String) {
    LoggerFactory.getLogger(this::class.java).warn(msg)
}

internal fun Any.logError(msg: String) {
    LoggerFactory.getLogger(this::class.java).error(msg)
}

internal fun Any.logError(msg: String, e: Throwable) {
    LoggerFactory.getLogger(this::class.java).error(msg, e)
}