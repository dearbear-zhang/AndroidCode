package com.mine.dearbear.mytest

import kotlinx.coroutines.*
import org.junit.Test

/**
 * Create by dearbear
 * on 2021/2/19
 */
class MainActivityKtTest {
    @Test
    fun main() {
        GlobalScope.launch(Dispatchers.Default) {
            var dataGet = withContext(Dispatchers.IO) {
                val xx = "test"
                return@withContext xx
            }
            println(dataGet)
            val testasy = async {

            }

            testasy.await()
        }
    }
}