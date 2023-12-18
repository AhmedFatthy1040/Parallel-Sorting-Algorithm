# Parallel Sorting Algorithm

This project implements a parallel version of the merge sort algorithm using Java's Fork/Join framework. The user interface allows dynamic addition of numbers to the array, and the sorting process is visualized using a graphical user interface.

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Algorithm Details](#algorithm-details)
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

## Algorithm Details <a name="algorithm-details"></a>

### Merge Sort

Merge sort is a divide-and-conquer algorithm that works by recursively breaking down an array into smaller subarrays until each subarray consists of a single element. It then merges these subarrays in a sorted manner to produce a fully sorted array. The key steps of the merge sort algorithm are as follows:

1. **Divide:** The array is divided into two halves.
2. **Recursively Sort:** Each half of the array is recursively sorted using the merge sort algorithm.
3. **Merge:** The sorted halves are merged to produce a single sorted array.

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Parallel Merge Sort

In this project, the merge sort algorithm is parallelized using Java's Fork/Join framework. The array is divided into subarrays, and each subarray is sorted independently in parallel. The sorted subarrays are then merged to obtain the final sorted result. This parallelization can lead to improved sorting performance, especially for large datasets.

- **Time Complexity:** O(n log n) - Parallelization provides more efficient sorting for large datasets.
- **Space Complexity:** O(n) - Similar to the sequential merge sort, with additional overhead for parallelization.

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
