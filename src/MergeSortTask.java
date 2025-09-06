import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class MergeSortTask extends RecursiveTask<int[]> {
    private final int[] array;
    private static final int THRESHOLD = 100;

    MergeSortTask(int[] array) {
        this.array = array;
    }

    @Override
    protected int[] compute() {
        if (array.length <= 1) {
            return array;
        }

        if (array.length < THRESHOLD) {
            return sequentialMergeSort(array);
        }

        int midpoint = array.length / 2;

        int[] left = Arrays.copyOfRange(array, 0, midpoint);
        int[] right = Arrays.copyOfRange(array, midpoint, array.length);

        MergeSortTask leftTask = new MergeSortTask(left);
        MergeSortTask rightTask = new MergeSortTask(right);

        invokeAll(leftTask, rightTask);

        int[] sortedLeft = leftTask.join();
        int[] sortedRight = rightTask.join();

        return merge(sortedLeft, sortedRight);
    }

    private int[] sequentialMergeSort(int[] arr) {
        if (arr.length <= 1) {
            return arr;
        }

        int midpoint = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, midpoint);
        int[] right = Arrays.copyOfRange(arr, midpoint, arr.length);

        left = sequentialMergeSort(left);
        right = sequentialMergeSort(right);

        return merge(left, right);
    }

    private int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];

        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        while (i < left.length) {
            result[k++] = left[i++];
        }

        while (j < right.length) {
            result[k++] = right[j++];
        }

        return result;
    }
}