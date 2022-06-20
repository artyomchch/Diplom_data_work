enum class PII {
    FingerprintManager, FingerprintManagerCompat, BiometricPrompt,
    Environment, Profile, Purchase, PurchaseHistoryRecord
}
enum class SignsPII {
    MAIN_INFORMATION, BANK_CARDS, PASSPORT_DATA, FINGERPRINT
}
enum class ICP {
    AccountManager, WifiManager, ConnectivityManager, LocationManagerCompat, LocationManager, FusedLocationProviderClient,
    NetworkInfo, NetworkCapabilities, ConnectivityManagerCompat, BluetoothConfigManager, AudioRecord, MediaRecorder, Environment, SensorManager,
    Camera, CameraManager, CameraCaptureSession, CameraDevice, ImageCapture, Calendar, Contacts, Calls, SMS, SipManager, PendingRecording, Voicemails,
    TelephonyManager, TelephonyManagerCompat
}
enum class SignsICP {
    MAIN_INFORMATION, WIFI, BLUETOOTH, NETWORK_MONITOR, LOCATION, MEDIA_DATA, IRIS_OF_EYES_OR_CAMERA, HEALTH
}