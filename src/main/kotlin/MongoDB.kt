import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo
import java.io.FileWriter


val csvMapper = CsvMapper().apply {
    registerModule(KotlinModule())
}


fun main() {
    val client = KMongo.createClient().coroutine //use coroutine extension
    val database = client.getDatabase("db_methods") //normal java driver usage
    val col = database.getCollection<AppPojoMongo>("collection_methods") //KMongo extension method


//async now
    runBlocking {

        val list: List<AppPojoMongo> = col.find(AppPojoMongo::category eq "AUTO_AND_VEHICLES").toList()

        list

        writeCsvFile(mapListDbModelToListEntity(list), "check_data.csv")
        println("Done")
    }
}

fun convertDataList(list: List<AppPojoMongo>): List<String> {
    return listOf()
}

private fun mapDbToCsvValue(appPojoMongo: AppPojoMongo) =
    with(appPojoMongo) {
        AppPojoCSV(
            appName = appName,
            activities = activities.size,
            category = category,
            maxSdk = maxSdk,
            minSdk = minSdk,
            targetSdk = targetSdk,
            sha1 = certificate.sha1,
            sha256 = certificate.sha256,
            issuerHumanFriendly = certificate.issuerHumanFriendly,
            subjectHumanFriendly = certificate.subjectHumanFriendly,
            hashAlgo = certificate.hashAlgo,
            signatureAlgo = certificate.signatureAlgo,
            serialNumber = certificate.serialNumber,
            providers = providers.size,
            services = services.size
        )
    }

fun mapListDbModelToListEntity(list: List<AppPojoMongo>) = list.map {
    mapDbToCsvValue(it)
}


private inline fun <reified T> writeCsvFile(data: Collection<T>, fileName: String) {
    FileWriter(fileName).use { writer ->
        csvMapper.writer(csvMapper.schemaFor(T::class.java).withHeader())
            .writeValues(writer)
            .writeAll(data)
            .close()
    }
}