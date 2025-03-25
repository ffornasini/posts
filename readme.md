# Posts Pagination with Search and Favorites

## Overview
This project is an Android application built using **Jetpack Compose**. It provides a Material based interface for managing posts and favorites, including search functionality.

**Notes**:
1. The error handling related to favorites management has been intentionally omitted, as this is only a proof of concept, and errors rarely occur locally
2. Only a few tests have been implemented specifically for pagination functionality. Additionally, previews (including some interactive ones) have been utilized to verify the UI behavior and ensure smooth user experience.

## Features
- **Post Management**: View, search, and filter posts using paginated data loading.
- **Favorites**: Add and remove posts from a favorites list with real-time state updates.
- **Search**: Includes dynamic search bar functionality with hint suggestions.
- **Detail View**: Displays detailed information for selected posts, including their favorite status.

## Technologies Used
- **Kotlin**
- **Jetpack Compose**
- **Paging 3**: For managing paginated data.
- **Hilt**: Dependency injection framework.
- **Flow**: Reactive data streams for real-time updates.
- **Room**: Local database for storing favorite posts and search hints.
- **Retrofit**: Network API management to fetch posts.

## Project Structure
The project is organized using an MVVM (Model-View-ViewModel) architecture:

- **View Layer**:
    - `FavoriteScreen`: Displays favorite posts with navigation support.
    - `SearchScreen`: Implements search functionality and suggestions.
    - `DetailScreen`: Shows detailed post view and favorite management.

- **ViewModel Layer**:
    - `SearchViewModel`: Manages search queries, posts, and hint suggestions.
    - `FavoriteViewModel`: Handles favorite posts.
    - `DetailViewModel`: Manages post states and favorite status with API and database integration.

- **Repository Layer**:
    - `PostRepository`: Fetches posts and manages favorites.
    - `HintRepository`: Provides search hint data.

- **Data Layer**:
    - `Api`: Service needed to fetch data from a REST API.
    - `Database`: Service needed to store data locally in an SQLITE database.

## How to Build and Run
1. Open the project in Android Studio.
2. Build the project and connect a device or emulator.
3. Run the application by clicking the "Run" button in Android Studio

## How to Run Tests
1. Navigate to the PostPagingSourceTest class.
2. Right-click on the file and select Run 'PostPagingSourceTest'.