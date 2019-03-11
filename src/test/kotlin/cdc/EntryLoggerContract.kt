package cdc

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.http4k.core.HttpHandler
import org.junit.jupiter.api.Test
import verysecuresystems.UserEntry
import verysecuresystems.Username
import verysecuresystems.external.EntryLogger
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

/**
 * This represents the contract that both the real and fake EntryLogger servers will adhere to.
 */
abstract class EntryLoggerContract(handler: HttpHandler) {

    private val time = Instant.now()
    private val entryLogger = EntryLogger(handler, Clock.fixed(time, ZoneId.systemDefault()))

    @Test
    fun `can log a user entry and it is listed`() {
        assertThat(entryLogger.enter(Username("bob")), equalTo(UserEntry("bob", true, time.toEpochMilli())))
        assertThat(entryLogger.exit(Username("bob")), equalTo(UserEntry("bob", false, time.toEpochMilli())))
    }
}



