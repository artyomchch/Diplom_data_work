import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo


val mapper = Mapper()
val csvMapper = CsvMapper().apply {
    registerModule(
        KotlinModule.Builder()
            .withReflectionCacheSize(512)
            .configure(KotlinFeature.NullToEmptyCollection, false)
            .configure(KotlinFeature.NullToEmptyMap, false)
            .configure(KotlinFeature.NullIsSameAsDefault, false)
            .configure(KotlinFeature.StrictNullChecks, false)
            .build()
    )
}


fun main() {
    val client = KMongo.createClient().coroutine //use coroutine extension
    val database = client.getDatabase("db_methods") //normal java driver usage
    val col = database.getCollection<AppPojoMongo>("collection_methods") //KMongo extension method



    runBlocking {

        val list: List<AppPojoMongo> = col.find(AppPojoMongo::category eq "AUTO_AND_VEHICLES").toList()



        mapper.writeCsvFile(mapper.mapListDbModelToListEntity(list), "base_data.csv")

        mapper.concatAllDataFrames(list, "part2.csv")

       // mapper.writeColumnsPermissionCSV(list, "permission.csv")

       // mapper.writeColumnsIntentFiltersCSV(list, "intent_filters.csv")

       // mapper.writeColumnsInputMethodsCSV(list, "input_methods.csv")

       // mapper.writeColumnsOutputMethodsCSV(list, "output_methods.csv")



        println("Done")
    }
}





