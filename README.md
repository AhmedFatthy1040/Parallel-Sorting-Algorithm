# Parallel Sorting Algorithm

This project implements a parallel version of the merge sort algorithm using Java's Fork/Join framework. The user interface allows dynamic addition of numbers to the array, and the sorting process is visualized using a graphical user interface.

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

## Features <a name="features"></a>

- Parallel implementation of the merge sort algorithm.
- Dynamic addition of numbers to the array through the GUI.
- Visualized sorting process with original and sorted arrays displayed.

## Requirements <a name="requirements"></a>

- Java Development Kit (JDK) 8 or later.
- Any Java IDE or build tool (e.g., IntelliJ, Eclipse, Maven).

## Usage <a name="usage"></a>

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/ParallelSortingAlgorithm.git
   
2. Open the project in your preferred Java IDE.

3. Run the Main class.

4. Enter numbers using the input field and click "Add" to add them to the array.

5. Click "Sort" to visualize the parallel merge sort process.

## Project Structure <a name="project-structure"></a>
The project follows the following structure:

- ParallelSortingAlgorithm
  - src
    - ParallelMergeSortGUI.java: Main GUI class.
    - MergeSortTask.java: Parallel merge sort implementation.
    - Main.java: Entry point.

## Contributing <a name="contributing"></a>
Feel free to contribute by opening issues, providing suggestions, or creating pull requests. Your contributions are highly appreciated.

## License <a name="license"></a>
This project is licensed under the [MIT License](LICENSE).
