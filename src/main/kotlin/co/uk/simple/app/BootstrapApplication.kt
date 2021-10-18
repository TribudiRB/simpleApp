package co.uk.simple.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BootstrapApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<BootstrapApplication>(*args)
        }
    }
}

