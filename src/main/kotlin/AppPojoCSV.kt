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
    val sha1: String?,
    val sha256: String?,
    @JsonProperty("issuer_human_friendly")
    val issuerHumanFriendly: String?,
    @JsonProperty("subject_human_friendly")
    val subjectHumanFriendly: String?,
    @JsonProperty("hash_algo")
    val hashAlgo: String?,
    @JsonProperty("signature_algo")
    val signatureAlgo: String?,
    @JsonProperty("serial_number")
    val serialNumber: String?,
    val providers: Int,
    val services: Int
)
