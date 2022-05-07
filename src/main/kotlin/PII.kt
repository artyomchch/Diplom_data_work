enum class PII {
    LocationManagerCompat, LocationManager, FusedLocationProviderClient, AudioRecord, MediaRecorder, SensorManager, Camera, CameraManager, CameraCaptureSession,
    CameraDevice, ImageCapture, PendingRecording, Calendar, Voicemails, Contacts, FingerprintManager, FingerprintManagerCompat, BiometricPrompt, SipManager,
    TelephonyManager, TelephonyManagerCompat, Environment, Calls, SMS, Profile, Purchase, PurchaseHistoryRecord
}

enum class SignsPII {
    MAIN_INFORMATION, HEALTH, IRIS_OF_EYES_OR_CAMERA, BANK_CARDS, PASSPORT_DATA, FINGERPRINT, LOCATION
}

enum class ICP {
    AccountManager, WifiManager, ConnectivityManager,
    NetworkInfo, NetworkCapabilities, ConnectivityManagerCompat, BluetoothConfigManager
}

enum class SignsICP {
    MAIN_INFORMATION, WIFI, BLUETOOTH, NETWORK_MONITOR
}