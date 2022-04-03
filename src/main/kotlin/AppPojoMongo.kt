import com.fasterxml.jackson.annotation.JsonProperty


data class AppPojoMongo(
    val activities: List<String>,
    @JsonProperty("app_name")
    val appName: String,
    val category: String,
    val certificate: Certificate,
    @JsonProperty("intent_filters")
    val intentFilters: LinkedHashMap<String, LinkedHashMap<String, List<String>>>?,
    @JsonProperty("max_sdk")
    val maxSdk: Int,
    @JsonProperty("min_sdk")
    val minSdk: Int,
    @JsonProperty("target_sdk")
    val targetSdk: Int,
    val methods: Methods,
    val permissions: List<String>,
    val providers: List<String>,
    val services: List<String>
)

data class Certificate(
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
    val serialNumber: String?
)


data class Methods(
    @JsonProperty("found_input_methods")
    val foundInputMethods: LinkedHashMap<String, List<String>>?,
    @JsonProperty(value = "found_output_methods")
    val foundOutputMethods: LinkedHashMap<String, List<String>>?

)

//data class FoundInputMethods(
//    @JsonProperty(value = "Landroidx/core/location/LocationManagerCompat;")
//    val locationManagerCompat: List<String>?,
//    @JsonProperty(value = "Landroid/location/LocationManager;")
//    val locationManager: List<String>?,
//    @JsonProperty(value = "Lcom/google/android/gms/location/FusedLocationProviderClient;")
//    val fusedLocationProviderClient: List<String>?,
//    @JsonProperty(value = "Landroid/media/AudioRecord;")
//    val audioRecord: List<String>?,
//    @JsonProperty(value = "Landroid/media/MediaRecorder;")
//    val mediaRecorder: List<String>?,
//    @JsonProperty(value = "Landroid/hardware/SensorManager;")
//    val sensorManager: List<String>?,
//    @JsonProperty(value = "Landroid/hardware/Camera;")
//    val camera: List<String>?,
//    @JsonProperty(value = "Landroid/hardware/camera2/CameraManager;")
//    val cameraManager: List<String>?,
//    @JsonProperty(value = "Landroid/hardware/camera2/CameraCaptureSession;")
//    val cameraCaptureSession: List<String>?,
//    @JsonProperty(value = "Landroidx/camera/core/ImageCapture;")
//    val cameraDevice: List<String>?,
//    @JsonProperty(value = "Landroidx/camera/video/PendingRecording;")
//    val pendingRecording: List<String>?,
//    @JsonProperty(value = "Ljava/util/Calendar;")
//    val calendar: List<String>?,
//    @JsonProperty(value = "Landroid/provider/VoicemailContract/Voicemails;")
//    val voicemails: List<String>?,
//    @JsonProperty(value = "Landroid/provider/ContactsContract/Contacts;")
//    val contacts: List<String>?,
//    @JsonProperty(value = "Landroid/net/sip/SipManager;")
//    val sipManager: List<String>?,
//    @JsonProperty(value = "Landroid/telephony/TelephonyManager;")
//    val telephonyManager: List<String>?,
//    @JsonProperty(value = "Landroidx/core/telephony/TelephonyManagerCompat;")
//    val telephonyManagerCompat: List<String>?,
//    @JsonProperty(value = "Landroid/os/Environment;")
//    val environment: List<String>?,
//    @JsonProperty(value = "Landroid/accounts/AccountManager;")
//    val accountManager: List<String>?,
//    @JsonProperty(value = "Lcom/android/billingclient/api/Purchase;")
//    val purchase: List<String>?,
//    @JsonProperty(value = "Lcom/android/billingclient/api/PurchaseHistoryRecord;")
//    val purchaseHistoryRecord: List<String>?,
//    @JsonProperty(value = "Landroid/hardware/fingerprint/FingerprintManager;")
//    val fingerprintManager: List<String>?,
//    @JsonProperty(value = "Landroidx/core/hardware/fingerprint/FingerprintManagerCompat;")
//    val fingerprintManagerCompat: List<String>?,
//    @JsonProperty(value = "Landroid/hardware/biometrics/BiometricPrompt;")
//    val biometricPrompt: List<String>?,
//    @JsonProperty(value = "Landroid/net/wifi/WifiManager;")
//    val wifiManager: List<String>?,
//    @JsonProperty(value = "Landroid/net/ConnectivityManager;")
//    val connectivityManager: List<String>?,
//    @JsonProperty(value = "Landroid/net/NetworkInfo;")
//    val networkInfo: List<String>?,
//    @JsonProperty(value = "Landroid/net/NetworkCapabilities;")
//    val networkCapabilities: List<String>?,
//    @JsonProperty(value = "Landroidx/core/net/ConnectivityManagerCompat;")
//    val connectivityManagerCompat: List<String>?,
//    @JsonProperty(value = "Lcom/google/android/things/bluetooth/BluetoothConfigManager;")
//    val bluetoothConfigManager: List<String>?,
//    @JsonProperty(value = "CallLog/Calls")
//    val callLogCallsConstant: List<String>?,
//    @JsonProperty(value = "Contacts")
//    val contactsConstant: List<String>?,
//    @JsonProperty(value = "Calls")
//    val callsConstant: List<String>?,
//    @JsonProperty(value = "SMS")
//    val smsConstant: List<String>?,
//    @JsonProperty(value = "Profile")
//    val profileConstant: List<String>?
//)
//
//
//data class FoundOutputMethods(
//    @JsonProperty(value = "Ljava/net/URL;")
//    val url: List<String>?,
//    @JsonProperty(value = "Ljava/net/Socket;")
//    val socket: List<String>?,
//    @JsonProperty(value = "Ljava/net/URLConnection;")
//    val urlConnection: List<String>?,
//    @JsonProperty(value = "Lorg/apache/http/conn/HttpRequest;")
//    val httpRequest: List<String>?,
//    @JsonProperty(value = "Lorg/apache/http/conn/HttpResponse;")
//    val httpResponse: List<String>?,
//    @JsonProperty(value = "Landroid/net/Uri;")
//    val uri: List<String>?,
//    @JsonProperty(value = "Landroid/net/LocalServerSocket;")
//    val localServerSocket: List<String>?,
//    @JsonProperty(value = "Landroid/net/LocalSocket;")
//    val localSocket: List<String>?,
//    @JsonProperty(value = "Landroid/net/Network;")
//    val network: List<String>?,
//    @JsonProperty(value = "Lretrofit2/Retrofit\$Builder;")
//    val retrofitBuilder: List<String>?,
//    @JsonProperty(value = "Lretrofit2/RequestFactory\$Builder;")
//    val requestFactoryBuilder: List<String>?,
//    @JsonProperty(value = "Lokhttp3/OkHttpClient\$Builder;")
//    val okHttpClient3: List<String>?,
//    @JsonProperty(value = "Lokhttp4/OkHttpClient\$Builder;")
//    val okHttpClient4: List<String>?,
//    @JsonProperty(value = "Lokhttp2/OkHttpClient\$Builder;")
//    val okHttpClient2: List<String>?,
//    @JsonProperty(value = "Lcom/android/volley/toolbox/Volley;")
//    val volley: List<String>?,
//    @JsonProperty(value = "Lcom/android/volley/RequestQueue;")
//    val requestQueue: List<String>?,
//    @JsonProperty(value = "Lcom/koushikdutta/ion/builder/LoadBuilder;")
//    val loadBuilder: List<String>?,
//    @JsonProperty(value = "Lcom/google/firebase/FirebaseDatabase;")
//    val firebaseDatabase: List<String>?,
//    @JsonProperty(value = "Lcom/google/firebase/FirebaseAuth;")
//    val firebaseAuth: List<String>?,
//    @JsonProperty(value = "LLcom/google/firebase/FirebaseApp;")
//    val firebaseApp: List<String>?,
//    @JsonProperty(value = "Lcom/google/firebase/FirebaseStorage;")
//    val firebaseStorage: List<String>?,
//    @JsonProperty(value = "Landroid/telephony/SmsManager;")
//    val smsManager: List<String>?,
//    @JsonProperty(value = "Landroid/net/sip/SipManager;")
//    val sipManager: List<String>?,
//
//
//    )
