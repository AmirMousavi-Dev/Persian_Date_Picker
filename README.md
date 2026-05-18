# Persian Date Picker for Jetpack Compose

A simple, robust, and highly customizable Persian (Jalali) date picker for Jetpack Compose.

## Features

- **Native RTL Support**: Built-in support for Right-to-Left layout direction.
- **Highly Customizable**: Full control over colors, fonts, and dimensions.
- **Leap Year Support**: Accurate Jalali calendar calculations.
- **State Management**: Includes `PersianDatePickerState` for easy control and state hoisting.

## Screenshots

<p align="center">
  <img width="400" height="830" src="https://github.com/user-attachments/assets/169af662-f363-456d-9549-5e057d2823c7" alt="Persian Date Picker Dialog" />
  <img width="400" height="830" src="https://github.com/user-attachments/assets/613cddff-6162-40a3-a8b4-7139c9685847" alt="Year Selection View" />
  <img width="400" height="830" src="https://github.com/user-attachments/assets/d2904521-0b30-4cac-b669-a7c90f035324" alt="Month Selection View" />
</p>

## Installation

### 1. Add the JitPack repository to your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### 2. Add the dependency to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.AmirMousavi-Dev:Persian_Date_Picker:v1.2.0")
}
```

## Usage

### Using State (Recommended)

```kotlin
val datePickerState = rememberPersianDatePickerState()

Button(onClick = { datePickerState.show() }) {
    Text("نمایش انتخاب‌گر تاریخ")
}

Text(text = datePickerState.fullDateName)

PersianDatePickerDialog(state = datePickerState)
```

### Manual Usage

```kotlin
var isDatePickerVisible by remember { mutableStateOf(false) }
var selectedDate by remember { mutableStateOf("") }

if (isDatePickerVisible) {
    PersianDatePickerDialog(
        onDismissRequest = { isDatePickerVisible = false },
        onConfirm = { persianDate ->
            selectedDate = persianDate.formattedDate
            isDatePickerVisible = false
        }
    )
}
```

### PersianDate Properties

The `PersianDate` object provides several useful properties:

- `year`, `month`, `day`: Basic date components.
- `dayName`: Persian name of the day (e.g., "سه‌شنبه").
- `monthName`: Persian name of the month (e.g., "مهر").
- `formattedDate`: Standard string representation (e.g., "1403/07/10").
- `fullDateName`: User-friendly Persian string (e.g., "سه‌شنبه ۱۰ مهر").

## Customization

You can customize the look and feel using `DatePickerDefaults.colors()`:

```kotlin
PersianDatePickerDialog(
    onDismissRequest = { /* ... */ },
    onConfirm = { /* ... */ },
    colors = DatePickerDefaults.colors(
        headerBackgroundColor = MaterialTheme.colorScheme.primary,
        headerTextColor = MaterialTheme.colorScheme.onPrimary,
        // ... more color options
    ),
    fontFamily = myCustomFontFamily
)
```

## License

```
Copyright 2024 Amir Mousavi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
