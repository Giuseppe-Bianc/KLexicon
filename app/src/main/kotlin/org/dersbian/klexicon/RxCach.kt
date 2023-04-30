package org.dersbian.klexicon

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

object RxCach {
    val cache: Cache<String, Regex> by lazy {
        Caffeine.newBuilder()
            .maximumSize(15)
            .expireAfterWrite(1, TimeUnit.SECONDS)
            .weakKeys()
            .weakValues()
            .build()
    }

    @JvmStatic
    fun getOrPut(pattern: String): Regex = cache.get(pattern) { Pattern.compile(pattern).toRegex() }
}