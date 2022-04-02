import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.FileWriter

class Mapper {

    fun writeColumnsPermissionCSV(list: List<AppPojoMongo>, nameFrame: String) {
        val uniquePermissions = convertListUniquePermissions(list)
        val codePermissionApp = mutableListOf<Int>()
        val rows: MutableList<List<Any>> = mutableListOf()
        var trigger = false
        rows.add(uniquePermissions.toList())


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

            rows.add(codePermissionApp.toList())
            codePermissionApp.clear()

        }
        csvWriter().writeAll(rows, nameFrame)
    }

    private fun convertListUniquePermissions(listApps: List<AppPojoMongo>): List<String> {
        val mapOfPermission = mutableMapOf<String, Int>()

        listApps.forEach {
            it.permissions
                .forEach { perm -> mapOfPermission.merge((perm.split(".").last()), 1, Int::plus) }
        }

        return mapOfPermission.filter { it.value > 2 }.map { it.key }.toList()
    }


    fun writeColumnsIntentFiltersCSV(list: List<AppPojoMongo>, nameFrame: String) {
        val uniqueIntentFilters = convertListUniqueIntentFilters(list)
        val codeIntentFilterApp = mutableListOf<Int>()
        val rows: MutableList<List<Any>> = mutableListOf()
        var trigger = false
        rows.add(uniqueIntentFilters.toList())

        list.forEach {
            uniqueIntentFilters.forEach { intentsFilters ->
                it.intentFilters?.values?.forEach { cat ->
                    cat.values.forEach { listAction ->
                        listAction.forEach { action ->
                            if (action.split(".").last() == (intentsFilters)) trigger = true
                        }
                    }
                }
                if (trigger) {
                    codeIntentFilterApp.add(1)
                    trigger = false
                } else codeIntentFilterApp.add(0)

            }
            rows.add(codeIntentFilterApp.toList())
            codeIntentFilterApp.clear()
        }
        csvWriter().writeAll(rows, nameFrame)

    }


    private fun convertListUniqueIntentFilters(listApps: List<AppPojoMongo>): List<String> {
        val mapOfIntent = mutableMapOf<String, Int>()
        listApps.forEach {
            it.intentFilters?.values?.forEach { cat ->
                cat.values.forEach { listAction ->
                    listAction.forEach { action ->
                        mapOfIntent.merge((action.split(".").last()), 1, Int::plus)
                    }
                }
            }
        }
        return mapOfIntent.filter { it.value > 5 }.map { it.key }.toList()
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


    inline fun <reified T> writeCsvFile(data: Collection<T>, fileName: String) {
        FileWriter(fileName).use { writer ->
            csvMapper.writer(csvMapper.schemaFor(T::class.java).withHeader())
                .writeValues(writer)
                .writeAll(data)
                .close()
        }
    }

}