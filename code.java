import java.util.Random;
import java.util.Arrays;

interface SortAlgorithm {
    void sort(int[] array);
    String getName();
    default boolean validate(int[] array) {
        int[] copy = array.clone();
        sort(copy);
        for (int i = 0; i < copy.length - 1; i++) {
            if (copy[i] > copy[i + 1]) return false;
        }
        return true;
    }
}

class DeterministicQuickSort implements SortAlgorithm {
    public void sort(int[] arr) {
        if (arr == null || arr.length == 0) return;
        quickSort(arr, 0, arr.length - 1);
    }

    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public String getName() {
        return "Детерминированный QuickSort";
    }
}

class RandomizedQuickSort implements SortAlgorithm {
    private Random random = new Random();

    public void sort(int[] arr) {
        if (arr == null || arr.length == 0) return;
        quickSort(arr, 0, arr.length - 1);
    }

    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int randomIndex = random.nextInt(high - low + 1) + low;
            swap(arr, randomIndex, high);

            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public String getName() {
        return "Рандомизированный QuickSort";
    }
}

class MedianOfThreeQuickSort implements SortAlgorithm {
    public void sort(int[] arr) {
        if (arr == null || arr.length == 0) return;
        quickSort(arr, 0, arr.length - 1);
    }

    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = medianOfThree(arr, low, high);
            swap(arr, pivotIndex, high);

            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int medianOfThree(int[] arr, int low, int high) {
        int mid = low + (high - low) / 2;

        if (arr[low] > arr[mid]) swap(arr, low, mid);
        if (arr[low] > arr[high]) swap(arr, low, high);
        if (arr[mid] > arr[high]) swap(arr, mid, high);

        return mid;
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public String getName() {
        return "QuickSort с медианой трех";
    }
}

class HeapSort implements SortAlgorithm {
    public void sort(int[] arr) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    private void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public String getName() {
        return "HeapSort";
    }
}

class MergeSort implements SortAlgorithm {
    public void sort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        mergeSort(arr, 0, arr.length - 1);
    }

    private void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
    }

    private void merge(int[] arr, int left, int mid, int right) {
        int[] leftArray = Arrays.copyOfRange(arr, left, mid + 1);
        int[] rightArray = Arrays.copyOfRange(arr, mid + 1, right + 1);

        int i = 0, j = 0, k = left;

        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] <= rightArray[j]) {
                arr[k++] = leftArray[i++];
            } else {
                arr[k++] = rightArray[j++];
            }
        }

        while (i < leftArray.length) {
            arr[k++] = leftArray[i++];
        }

        while (j < rightArray.length) {
            arr[k++] = rightArray[j++];
        }
    }

    public String getName() {
        return "MergeSort";
    }
}

public class SortingTest {

    private static int[] generateArray(int size, String type) {
        Random random = new Random(12345);
        int[] arr = new int[size];

        switch (type) {
            case "RANDOM":
                for (int i = 0; i < size; i++) {
                    arr[i] = random.nextInt(size * 10);
                }
                break;

            case "SORTED_ASC":
                for (int i = 0; i < size; i++) {
                    arr[i] = i;
                }
                break;

            case "SORTED_DESC":
                for (int i = 0; i < size; i++) {
                    arr[i] = size - i - 1;
                }
                break;

            case "KILLER_SEQUENCE":
                for (int i = 0; i < size; i++) {
                    arr[i] = i % 100;
                }
                break;
        }

        return arr;
    }

    private static void printTableHeader() {
        System.out.printf("%-10s %-20s %-35s %-15s %-10s%n",
                "Размер", "Тип данных", "Алгоритм", "Время (мс)", "Статус");
    }

    private static void printTableRow(int size, String dataType, String algorithm, double time, boolean valid) {
        System.out.printf("%-10d %-20s %-35s %-15.3f %-10s%n",
                size, dataType, algorithm, time, valid ? "успешно" : "ошибка");
    }

    public static void main(String[] args) {
        SortAlgorithm[] algorithms = {
                new DeterministicQuickSort(),
                new RandomizedQuickSort(),
                new MedianOfThreeQuickSort(),
                new HeapSort(),
                new MergeSort()
        };

        int[] sizes = {1000, 5000, 10000};
        String[] dataTypes = {"RANDOM", "SORTED_ASC", "SORTED_DESC", "KILLER_SEQUENCE"};
        String[] dataTypeNames = {"Случайные", "Отсорт. (возр.)", "Отсорт. (убыв.)", "Киллер-послед."};

        printTableHeader();

        for (int size : sizes) {
            for (int dt = 0; dt < dataTypes.length; dt++) {
                int[] testData = generateArray(size, dataTypes[dt]);

                for (SortAlgorithm algorithm : algorithms) {
                    int[] dataCopy = testData.clone();

                    long startTime = System.nanoTime();
                    algorithm.sort(dataCopy);
                    long endTime = System.nanoTime();

                    double elapsedMs = (endTime - startTime) / 1_000_000.0;
                    boolean isValid = algorithm.validate(dataCopy);

                    printTableRow(size, dataTypeNames[dt], algorithm.getName(), elapsedMs, isValid);
                }
            }
        }
    }
}