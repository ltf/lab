import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.WARN

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{MM/dd-HH:mm:ss} %-5level - %msg%n"
    }
}

logger('ltf.jmonitor', WARN)
root(DEBUG, ["STDOUT"])