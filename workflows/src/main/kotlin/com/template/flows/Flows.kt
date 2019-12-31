package com.template.flows


import co.paralleluniverse.fibers.Suspendable
import net.corda.core.DeleteForDJVM
import net.corda.core.KeepForDJVM
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.InitiatingFlow
import net.corda.core.flows.StartableByRPC
import net.corda.core.serialization.CordaSerializable
import net.corda.core.utilities.ProgressTracker
import java.sql.ResultSet
import java.util.*
import java.text.SimpleDateFormat
import javax.xml.transform.Result

const val TABLE_NAME = "crypto_values"
const val TABLE_NAME2 = "user_data"


/**
 * Adds a crypto token and associated value to the table of crypto values.
 */
@InitiatingFlow
@StartableByRPC
@KeepForDJVM
class AddDatabaseFlow(private val name: String, private val email: String, private val date: Date, private val contact: Int, private val country: String) : @DeleteForDJVM FlowLogic<Unit>() {
    override val progressTracker: ProgressTracker = ProgressTracker()

    @Suspendable
    override fun call() {
        val databaseService = serviceHub.cordaService(CryptoValuesDatabaseService::class.java)

        // println(date.time)
        // BE CAREFUL when accessing the node's database in flows:
        // 1. The operation must be executed in a BLOCKING way. Flows don't
        //    currently support suspending to await a database operation's
        //    response
        // 2. The operation must be idempotent. If the flow fails and has to
        //    restart from a checkpoint, the operation will also be replayed
        UniqueIdentifier()
        databaseService.addDatabaseValue(name, email, date.time, contact, country)
    }
}

@CordaSerializable
@KeepForDJVM
data class UniqueIdentifier @JvmOverloads @DeleteForDJVM constructor(
        val externalId: String? = null,
        val id: UUID = UUID.randomUUID()
) : Comparable<UniqueIdentifier> {
    override fun compareTo(other: UniqueIdentifier): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
class QueryDatabaseFlow(private val email: String) : FlowLogic<String>() {
    override val progressTracker: ProgressTracker = ProgressTracker()

    @Suspendable
    override fun call(): String {
        val databaseService = serviceHub.cordaService(CryptoValuesDatabaseService::class.java)
        val res = databaseService.queryDatabaseValue(email)
        //val temp = arrayOf(res)
//        val array = arrayOfNulls<String>(res.size)
//        res.toArray(array)


        //    return res
        return "and ${res} and ${res.get("DATE")} , contact is: ${res.get("CONTACT")} email is: ${res.get("EMAIL")} , country is: ${res.get("COUNTRY")} , and name is: ${res.get("NAME")} "
    }
}
