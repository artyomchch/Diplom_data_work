import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.FileWriter

class Mapper {

    fun concatAllDataFrames(list: List<AppPojoMongo>, nameDataFrame: String) {
        val conRows: MutableList<List<Any>> = mutableListOf()
        val permissions = writeColumnsPermissionCSV(list)
        val intentFilters = writeColumnsIntentFiltersCSV(list)
        val inputMethods = writeColumnsInputMethodsCSV(list)
        val outputMethods = writeColumnsOutputMethodsCSV(list)

        writeColumnsInputMethodsCSV(list).indices.forEach {
            conRows.add(listOf(permissions[it], intentFilters[it], inputMethods[it], outputMethods[it]).flatten())
        }
        csvWriter().writeAll(conRows, nameDataFrame)
    }


    private fun writeColumnsPermissionCSV(list: List<AppPojoMongo>): MutableList<List<Any>> {
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
        // csvWriter().writeAll(rows, nameFrame)
        return rows
    }

    private fun convertListUniquePermissions(listApps: List<AppPojoMongo>): List<String> {
        val mapOfPermission = mutableMapOf<String, Int>()

        listApps.forEach {
            it.permissions
                .forEach { perm -> mapOfPermission.merge((perm.split(".").last()), 1, Int::plus) }
        }

        return mapOfPermission.filter { it.value > 2 }.map { it.key }.toList()
    }


    private fun writeColumnsIntentFiltersCSV(list: List<AppPojoMongo>): MutableList<List<Any>> {
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
        //csvWriter().writeAll(rows, nameFrame)
        return rows

    }

    private fun convertListInputMethods(listApps: List<AppPojoMongo>): List<String> {
        val mapOfInputMethods = mutableMapOf<String, Int>()

        listApps.forEach {
            it.methods.foundInputMethods
                ?.forEach { methods ->
                    methods.value.forEach { method ->
                        if (!method.contains("/") && (!method.contains("."))) mapOfInputMethods.merge((method), 1, Int::plus)
                    }
                }
        }

        return mapOfInputMethods.map { it.key }.toList()
    }

    private fun writeColumnsInputMethodsCSV(list: List<AppPojoMongo>): MutableList<List<Any>> {
        val uniqueInputMethods = convertListInputMethods(list)
        val codeInputMethods = mutableListOf<Int>()
        val rows: MutableList<List<Any>> = mutableListOf()
        var trigger = false
        rows.add(uniqueInputMethods.toList())


        list.forEach {
            uniqueInputMethods.forEach { uniqueMethods ->
                it.methods.foundInputMethods?.forEach { methods ->
                    methods.value.forEach { method ->
                        if (method == uniqueMethods) trigger = true
                    }
                }
                if (trigger) {
                    codeInputMethods.add(1)
                    trigger = false
                } else codeInputMethods.add(0)
            }

            rows.add(codeInputMethods.toList())
            codeInputMethods.clear()

        }
        // csvWriter().writeAll(rows, nameFrame)
        return rows
    }

    private fun convertListOutputMethods(listApps: List<AppPojoMongo>): List<String> {
        val mapOfOutputMethods = mutableMapOf<String, Int>()

        listApps.forEach {
            it.methods.foundOutputMethods
                ?.forEach { methods ->
                    methods.value.forEach { method ->
                        if (!method.contains("/") && (!method.contains("."))) mapOfOutputMethods.merge((method), 1, Int::plus)
                    }
                }
        }

        return mapOfOutputMethods.filter { it.value > 2 }.map { it.key }.toList()
    }

    private fun writeColumnsOutputMethodsCSV(list: List<AppPojoMongo>): MutableList<List<Any>> {
        val uniqueOutputMethods = convertListOutputMethods(list)
        val codeOutputMethods = mutableListOf<Int>()
        val rows: MutableList<List<Any>> = mutableListOf()
        var trigger = false
        rows.add(uniqueOutputMethods.toList())


        list.forEach {
            uniqueOutputMethods.forEach { uniqueMethods ->
                it.methods.foundOutputMethods?.forEach { methods ->
                    methods.value.forEach { method ->
                        if (method == uniqueMethods) trigger = true
                    }
                }
                if (trigger) {
                    codeOutputMethods.add(1)
                    trigger = false
                } else codeOutputMethods.add(0)
            }

            rows.add(codeOutputMethods.toList())
            codeOutputMethods.clear()

        }
        // csvWriter().writeAll(rows, nameFrame)
        return rows
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