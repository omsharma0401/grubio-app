# Grubio App

Grubio is a modern food delivery app that lets users browse food, manage their cart in real time, and select delivery locations with ease. It features a clean and smooth UI built entirely with Jetpack Compose.

---

## Features & Tech Stack

- **Jetpack Compose UI**: Fully declarative and reactive UI for a fresh and maintainable design  
- **MVVM Architecture**: Separates UI, business logic, and data for clean, scalable code  
- **Kotlin Coroutines + Flow**: Uses StateFlow and SharedFlow to manage app state and navigation events reactively  
- **Animated Splash Screen & Transitions**: Polished splash screen and smooth SharedElement transitions for a fluid user experience  
- **Dynamic Cart Management**: Real-time cart updates ensure consistent state across all screens using Kotlin Flow  
- **Maps Compose API Integration**: Interactive delivery address selection with reverse geocoding for precise location picking — all location data and Maps API handled by backend  
- **Authentication**: Supports Facebook login, Google login, and email/password login — all managed through the backend  
- **Retrofit for Networking**: Manages API calls to communicate with the backend  
- **Backend Powered by Ktor**: Connects to a Kotlin-based backend that handles orders, authentication, and location services ([backend repo here](https://github.com/furqanullah717/food_delivery_ktor))  

---

## How to Run

1. Clone this repo  
2. Open the project in Android Studio  
3. Create or update the `local.properties` file in the root of the project with your own credentials and URLs. It should look like this:

   ```properties
   WEB_CLIENT_ID="your-google-web-client-id.apps.googleusercontent.com"

   FACEBOOK_APP_ID="your-facebook-app-id"
   FACEBOOK_CLIENT_TOKEN="your-facebook-client-token"
   FB_LOGIN_PROTOCOL_SCHEME="your-facebook-protocol-scheme"

   MAPS_API_KEY="your-google-maps-api-key"

   # Use this for running on a physical device or connected device on the same network
   # BASE_URL="http://your-local-ip:8080"

   # Use this for running on Android Emulator
   BASE_URL="http://10.0.2.2:8080"
   
4. Make sure your backend (Ktor server) is running and accessible via the BASE_URL you set

5. Build and run the app on an emulator or physical device
