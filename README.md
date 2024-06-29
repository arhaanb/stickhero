# Stick Hero

> Final project for CSE201 (Advanced Programming), Built by [Arhaan Bahadur](https://arhaanb.com) (2022093) and [Ishir Bhardwaj](https://ishirbhardwaj.github.io/) (2022223).

## OOP Concepts Used
- Interfaces - Movable which is implemented by Stick and Player classes
- Abstract Classes - Obstacle class which is extended by the Pillar class
- Abstraction - Users are only shown simple UI elements to work with. The complex code is hidden
- Generics have been used in a few utility functions (Class: Utility)
- Encapsulation - Getters and Setters are used throughout the project
- Design Patterns
  - Builder Pattern for building the scene and setting themes (Class: Theme)
  - Singleton Pattern for the player (Class: Sprite)
- File reading/writing for storing/loading player data
- JUnit
  - Adding a new pillar should calculate and generate the next one in front of the last one (x coordinate should be larger than previous rectangle)
  - Calculating cherry position: new cherries generated should be between two pillars and not overlapping
  - A few functions use multiple custom variations of Random. These have been tested.

## Features
- Sound effects
- Reviving with cherries

## Bonus
- Themes can be bought with collected cherries
- Multithreading: implemented using the `Platform.runLater()` functions. The project is structured in a way to make UI updates on a separate thread (and background logic functions are running on another thread) - Check PlayController for implementation.

## Steps to run
> Make sure JDK SE 21.0 is installed

### To run the game
- ```mvn compile```
- ```mvn javafx:run```

### To run tests
- ```mvn test```

Alternatively, the application can be launched inside IntelliJ by opening the HelloApplication.java file inside src\main\java\com\arhaanb\stickhero\stickhero and running it (using Shift+F10).

## How it works
The application functions as follows:

1. Left-clicking initiates the generation of a stick, while right-clicking toggles the ability to collect cherries.
2. The player's score increments by 1 for each successfully crossed pillar, and the cherry count increases by 1 for every collected cherry.
3. Two cherries are required to revive the player in case of failure.
4. Each thematic variation costs 4 cherries.

Upon the conclusion of a game session, either due to player demise or manual termination using the close button, persistent data, including the owned and selected themes, last score, total cherries, and high score are saved in distinct files located at the project's root.

Themes are priced at 4 cherries each, and the purchase button is enabled only if the user has the requisite number of cherries.
