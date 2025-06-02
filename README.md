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
2. Open the project in Android Studio (Arctic Fox or newer recommended)  
3. Add your API keys and backend URL configuration:
   - Maps API keys as needed (backend handles most map services)  
   - OAuth credentials for Facebook and Google login  
   - Backend endpoint URL (point to your Ktor backend or a deployed instance)  
4. Build and run the app on an emulator or physical device  

---

Grubio combines modern Android development tools and architecture patterns to deliver a smooth and responsive food delivery experience.

---

If you want me to help add screenshots or contribution guidelines, just say the word!
