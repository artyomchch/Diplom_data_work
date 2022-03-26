import com.fasterxml.jackson.annotation.JsonProperty

data class AppPojoCSV(
    val activities: Int,
    @JsonProperty("app_name")
    val appName: String,
    val category: String,
    @JsonProperty("max_sdk")
    val maxSdk: Int,
    @JsonProperty("min_sdk")
    val minSdk: Int,
    @JsonProperty("target_sdk")
    val targetSdk: Int,
   // val permissions: List<String>,
  //  val providers: List<String>,
//    val services: List<String>
)
