import Categories.*
import PII.*
import SignsPII.*
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.File
import java.io.FileWriter

class Mapper {

    fun concatAllDataFrames(list: List<AppPojoMongo>, nameDataFrame: String) {


        val conRows: MutableList<List<Any>> = mutableListOf()
        val permissions = writeColumnsPermissionCSV(list)
        val intentFilters = writeColumnsIntentFiltersCSV(list)
        val inputMethods = writeColumnsInputMethodsCSV(list)
        val outputMethods = writeColumnsOutputMethodsCSV(list)
        searchPIIMethods(list)


        writeCsvFile(mapListDbModelToListEntity(list), NAME_MAIN_FILE)

        val file = File(NAME_MAIN_FILE)
        val rows: List<List<String>> = csvReader().readAll(file)



        writeColumnsInputMethodsCSV(list).indices.forEach {
            conRows.add(listOf(rows[it], permissions[it], intentFilters[it], inputMethods[it], outputMethods[it]).flatten())
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

        return rows
    } //READ_EPG_DATA

    private fun convertListUniquePermissions(listApps: List<AppPojoMongo>): List<String> {
        val mapOfPermission = mutableMapOf<String, Int>()

        listApps.forEach {
            it.permissions
                .forEach { perm -> mapOfPermission.merge((perm.split(".").last()), 1, Int::plus) }
        }

        return mapOfPermission.filter { it.value > 10 }.map { it.key }.toList()
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

    private fun searchPIIMethods(listApps: List<AppPojoMongo>) {
        val setPiiMethods = mutableSetOf<String>()
        val setIcpMethods = mutableSetOf<String>()
        listApps.forEach { app ->
            app.methods.foundInputMethods?.forEach { methods ->
                val piiName = signsPii(methods.key)
                val icpName = signsIcp(methods.key)
                if (piiName != UNKNOWN_METHOD) setPiiMethods.add(piiName)
                if (icpName != UNKNOWN_METHOD) setIcpMethods.add(icpName)

            }

            print("PII: ${setPiiMethods.size.toFloat() / SignsPII.values().size * 100}   ")
            setPiiMethods.clear()
            println("ICP: ${setIcpMethods.size.toFloat() / SignsICP.values().size * 100}      $setIcpMethods")
            setIcpMethods.clear()
            //  println(setIcpMethods)
        }

    }


    private fun mapDbToCsvValue(appPojoMongo: AppPojoMongo) =
        with(appPojoMongo) {
            AppPojoCSV(
                appName = appName,
                activities = activities.size,
                category = changeCategory(category),
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

    private fun mapListDbModelToListEntity(list: List<AppPojoMongo>) = list.map {
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

    private fun changeCategory(category: String): String {
        val categories = when (Categories.valueOf(category)) {
            GAME_CASINO, GAME_SIMULATION, GAME_CARD, GAME_CASUAL, GAME_SPORTS, GAME_MUSIC, GAME_BOARD, GAME_TRIVIA, GAME_PUZZLE, GAME_ADVENTURE, GAME_STRATEGY,
            GAME_ARCADE, GAME_RACING, GAME_ACTION, GAME_ROLE_PLAYING, GAME_EDUCATIONAL, GAME_WORD -> return GAMES.name
            BOOKS_AND_REFERENCE, COMICS, NEWS_AND_MAGAZINES, EVENTS, LIBRARIES_AND_DEMO -> return BOOKS_AND_NEWS.name
            SOCIAL, DATING, COMMUNICATION -> return COMMUNICATION.name
            TRAVEL_AND_LOCAL, MAPS_AND_NAVIGATION -> TRAVEL.name
            SHOPPING, FOOD_AND_DRINK, ENTERTAINMENT, LIFESTYLE, BEAUTY -> return ENTERTAINMENT.name
            HEALTH_AND_FITNESS, AUTO_AND_VEHICLES, SPORTS -> return LIFESTYLE.name
            BUSINESS, PARENTING, WEATHER -> return OTHER.name
            MUSIC_AND_AUDIO, PERSONALIZATION, HOUSE_AND_HOME, PHOTOGRAPHY, VIDEO_PLAYERS, PRODUCTIVITY, TOOLS -> return TOOLS.name
            EDUCATION, FINANCE, MEDICAL, ART_AND_DESIGN -> return EDUCATION.name
            else -> return UNDEFINED
        }
        return categories
    }

    private fun signsPii(methods: String): String {
        val nameClass = methods.split("/").last().replace(";", "")
        PII.values().forEach {
            if (it.name.contains(nameClass)) {
                return when (PII.valueOf(nameClass)) {
                    Voicemails, SipManager, Calls, TelephonyManager, Contacts, Calendar, AudioRecord, MediaRecorder, SMS, Profile, TelephonyManagerCompat, Camera -> MAIN_INFORMATION.name
                    CameraManager, CameraCaptureSession, CameraDevice, ImageCapture, PendingRecording -> IRIS_OF_EYES_OR_CAMERA.name
                    SensorManager -> HEALTH.name
                    Environment -> PASSPORT_DATA.name
                    LocationManager, LocationManagerCompat, FusedLocationProviderClient -> LOCATION.name
                    FingerprintManager, FingerprintManagerCompat, BiometricPrompt -> FINGERPRINT.name
                    Purchase, PurchaseHistoryRecord -> BANK_CARDS.name
                }
            }
        }
        return UNKNOWN_METHOD
    }

    private fun signsIcp(methods: String): String {
        val nameClass = methods.split("/").last().replace(";", "")
        ICP.values().forEach {
            if (it.name.contains(nameClass)) {
                return when (ICP.valueOf(nameClass)) {  //
                    ICP.BluetoothConfigManager -> SignsICP.BLUETOOTH.name
                    ICP.ConnectivityManager, ICP.ConnectivityManagerCompat, ICP.NetworkInfo, ICP.NetworkCapabilities -> SignsICP.NETWORK_MONITOR.name
                    ICP.WifiManager -> SignsICP.WIFI.name
                    ICP.AccountManager -> SignsICP.MAIN_INFORMATION.name
                }
            }
        }
        return UNKNOWN_METHOD
    }

    companion object {
        const val NAME_MAIN_FILE = "base_data.csv"
        const val UNDEFINED = "UNDEFINED_CATEGORY"
        const val UNKNOWN_METHOD = "UNKNOWN_METHOD"

    }

}