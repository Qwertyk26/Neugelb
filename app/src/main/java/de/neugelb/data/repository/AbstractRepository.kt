package de.neugelb.data.repository

import de.neugelb.data.server.ServerApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class AbstractRepository: KoinComponent {
    protected val server: ServerApi by inject()
}