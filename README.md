# Timeline App

## Overview

This app visualizes events on a vertical timeline with **compact lanes** and **month/day grouping
**.  
Events are displayed as single, continuous blocks across their date range, without gaps between
days.  
The left rail shows sticky month and day headers, and the right pane displays the event blocks in
horizontal lanes.  
Both sides scroll **synchronously**.

---

## Features

- **Vertical, calendar-like timeline**
    - Sticky headers for months and days
    - Each month as a separate section
- **Compact lane packing**
    - Events that don’t overlap share lanes
- **Single-block events**
    - No daily slicing; events stretch continuously from start to end
- **Bi-directional scroll sync**
    - Left (rail) and right (event plane) stay aligned
- **Horizontal scroll per month**
    - For wide event layouts
- **Dynamic event colors**
    - Background color based on event duration (short = cooler colors, long = warmer colors)

---

## Tech Decisions

### Architecture

- **ViewModel-driven logic**  
  All heavy calculations — section building, lane assignment, per-item height mapping, scroll sync
  mapping — are in the ViewModel.
- **UI as a “dumb view”**  
  Composables just render state; no business logic in the UI layer.

### Data Flow

1. Input list of `Event(startDate, endDate, name)`
2. **ViewModel**:
    - Calculates timeline bounds
    - Splits into month sections
    - Assigns events to lanes
    - Computes list item heights and cumulative offsets for scroll sync
    - Applies background color per event
3. **UI**:
    - Renders rail (month/day headers) and plane (event blocks) as two synced `LazyColumn`s
    - Applies horizontal scroll to plane

### Layout

- Left rail: Fixed width, sticky month headers, daily rows
- Right pane: Month header + canvas with day grid and positioned event blocks
- Vertical padding between events for clarity

---

## Build & Run

**Requirements:**

- Android Studio Koala+ (or compatible)
- Kotlin 1.9+
- Min SDK 24 (Java 8+ core library desugaring enabled for `LocalDate`)

**Run:**

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle
4. Run on emulator or device

---

## Sample Data

To quickly preview the timeline, the ViewModel includes a `generateSampleEvents()` function that
produces a realistic mix of short and long events across multiple months.

```kotlin
fun generateSampleEvents(): List<Event> {
    val today = LocalDate.now()
    return listOf(
        Event(1, today.minusDays(2), today.plusDays(3), "Conference"),
        Event(2, today.minusDays(10), today.minusDays(5), "Workshop"),
        Event(3, today.plusDays(1), today.plusDays(1), "One-day Meeting"),
        Event(4, today.plusDays(4), today.plusDays(15), "Research Sprint"),
        Event(5, today.plusDays(20), today.plusDays(40), "Product Launch")
    )
}
```

## Time Spent

~3 hours initial implementation + ~1 hour for refactor & cleanup.

---

## What I Like

- Scroll sync between lists
- Single-block events instead of daily slices
- Modularized into domain, ViewModel, and UI layers
- Clear separation of logic from UI
- Easy to extend for zooming, drag & drop, or editing events

---

## If I Had More Time

- Add pinch-to-zoom for lane widths
- Allow event drag-and-drop to change dates
- Animate event placement
- Improve color palette accessibility (contrast for all durations)
- Lane labeling or collapsing

---

## Inspirations

- Google Calendar’s month-day list layout
- Swimlane Gantt charts
- Sticky header patterns from Jetpack Compose samples
