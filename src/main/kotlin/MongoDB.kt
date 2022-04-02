import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.util.idValue
import java.io.FileWriter


val csvMapper = CsvMapper().apply {
    registerModule(KotlinModule())
}


fun main() {
    val client = KMongo.createClient().coroutine //use coroutine extension
    val database = client.getDatabase("db_methods") //normal java driver usage
    val col = database.getCollection<AppPojoMongo>("collection_methods") //KMongo extension method



    runBlocking {

        val list: List<AppPojoMongo> = col.find(AppPojoMongo::category eq "AUTO_AND_VEHICLES").toList()

        list.forEach {
            it.intentFilters?.values?.forEach { cat->
                cat.values.forEach{ str->
                    println(str)
                }


            }
            }

        list
        writeColumnsPermissionCSV(list)

        writeCsvFile(mapListDbModelToListEntity(list), "check_data.csv")
        println("Done")
    }
}

fun convertListUniquePermissions(listApps: List<AppPojoMongo>): List<String> {
    val mapOfPermission = mutableMapOf<String, Int>()

    listApps.forEach {
        it.permissions
            .forEach { perm -> mapOfPermission.merge((perm.split(".").last()), 1, Int::plus) }
    }

    return mapOfPermission.filter { it.value > 2 }.map { it.key }.toList()
}

fun writeColumnsPermissionCSV(list: List<AppPojoMongo>) {
    val uniquePermissions = convertListUniquePermissions(list)
    val codePermissionApp = mutableListOf<Int>()
    val rows: MutableList<MutableList<Any>> = mutableListOf()
    var trigger = false
    rows.add(uniquePermissions.toMutableList())


    list.forEach {
        uniquePermissions.forEach { perm ->
            it.permissions.forEach { permissionFullName ->
                if (permissionFullName.split(".").last() == (perm)) trigger = true
            }
            if (trigger) {
                codePermissionApp.add(1)
                trigger = false
            } else codePermissionApp.add(0)
        }

        rows.add(codePermissionApp.toMutableList())
        codePermissionApp.clear()

    }
    csvWriter().writeAll(rows, "test.csv")
}
//    val map = mapOf("122" to 2, "3455" to 3)
//    println(map.flatMap { (key, value) -> key.take(value).toList() }) // [1, 2, 3, 4, 5]

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