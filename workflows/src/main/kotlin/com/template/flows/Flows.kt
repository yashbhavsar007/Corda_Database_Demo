package com.template.flows


import co.paralleluniverse.fibers.Suspendable
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.InitiatingFlow
import net.corda.core.flows.StartableByRPC
import net.corda.core.utilities.ProgressTracker

const val TABLE_NAME = "crypto_values"
const val TABLE_NAME2 = "user_data"

/**
 * Adds a crypto token and associated value to the table of crypto values.
 */
@InitiatingFlow
@StartableByRPC
class AddTokenValueFlow(private val token: String, private val value: Int) : FlowLogic<Unit>() {
    override val progressTracker: ProgressTracker = ProgressTracker()

    @Suspendable
    override fun call() {
        val databaseService = serviceHub.cordaService(CryptoValuesDatabaseService::class.java)
        // BE CAREFUL when accessing the node's database in flows:
        // 1. The operation must be executed in a BLOCKING way. Flows don't
        //    currently support suspending to await a database operation's
        //    response
        // 2. The operation must be idempotent. If the flow fails and has to
        //    restart from a checkpoint, the operation will also be replayed
        databaseService.addTokenValue(token, value)
    }
}


/**
 * Adds a crypto token and associated value to the table of crypto values.
 */
@InitiatingFlow
@StartableByRPC
class AddDatabaseFlow(private val name: String, private val email: String ) : FlowLogic<Unit>() {
    override val progressTracker: ProgressTracker = ProgressTracker()

    @Suspendable
    override fun call() {
        val databaseService = serviceHub.cordaService(CryptoValuesDatabaseService::class.java)
        // BE CAREFUL when accessing the node's database in flows:
        // 1. The operation must be executed in a BLOCKING way. Flows don't
        //    currently support suspending to await a database operation's
        //    response
        // 2. The operation must be idempotent. If the flow fails and has to
        //    restart from a checkpoint, the operation will also be replayed
        databaseService.addDatabaseValue(name, email)
    }
}


/**
 * Updates the value of a crypto token in the table of crypto values.
 */
@InitiatingFlow
@StartableByRPC
class UpdateDatabaseFlow(private val name: String, private val email: String) : FlowLogic<Unit>() {
    override val progressTracker: ProgressTracker = ProgressTracker()

    @Suspendable
    override fun call() {
        val databaseService = serviceHub.cordaService(CryptoValuesDatabaseService::class.java)
        databaseService.updateDatabaseValue(name, email)
    }
}

/**
 * Updates the value of a crypto token in the table of crypto values.
 */
@InitiatingFlow
@StartableByRPC
class UpdateTokenValueFlow(private val token: String, private val value: Int) : FlowLogic<Unit>() {
    override val progressTracker: ProgressTracker = ProgressTracker()

    @Suspendable
    override fun call() {
        val databaseService = serviceHub.cordaService(CryptoValuesDatabaseService::class.java)
        databaseService.updateTokenValue(token, value)
    }
}


/**
 * Deletes crypto token in the table of crypto values.
 */
@InitiatingFlow
@StartableByRPC
class DeleteTokenValueFlow(private val token: String) : FlowLogic<Unit>() {
    override val progressTracker: ProgressTracker = ProgressTracker()

    @Suspendable
    override fun call() {
        val databaseService = serviceHub.cordaService(CryptoValuesDatabaseService::class.java)
        databaseService.deleteTokenValue(token)
    }
}

/**
 * Deletes crypto token in the table of crypto values.
 */
@InitiatingFlow
@StartableByRPC
class DeleteDatabaseFlow(private val name: String) : FlowLogic<Unit>() {
    override val progressTracker: ProgressTracker = ProgressTracker()

    @Suspendable
    override fun call() {
        val databaseService = serviceHub.cordaService(CryptoValuesDatabaseService::class.java)
        databaseService.deleteDatabaseValue(name)
    }
}


/**
 * Retrieves the value of a crypto token in the table of crypto values.
 */
@InitiatingFlow
@StartableByRPC
class QueryTokenValueFlow(private val token: String) : FlowLogic<Int>() {
    override val progressTracker: ProgressTracker = ProgressTracker()

    @Suspendable
    override fun call(): Int {
        val databaseService = serviceHub.cordaService(CryptoValuesDatabaseService::class.java)
        return databaseService.queryTokenValue(token)
    }
}

/**
 * Retrieves the value of a crypto token in the table of crypto values.
 */
@InitiatingFlow
@StartableByRPC
class QueryDatabaseFlow(private val name: String) : FlowLogic<String>() {
    override val progressTracker: ProgressTracker = ProgressTracker()

    @Suspendable
    override fun call(): String {
        val databaseService = serviceHub.cordaService(CryptoValuesDatabaseService::class.java)
        return databaseService.queryDatabaseValue(name)
    }
}