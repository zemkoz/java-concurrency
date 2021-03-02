package cz.zemko.tutorials.javaconcurrency.parallel.mergesort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public final class MergeSortTask<T extends Comparable<T>> extends RecursiveTask<List<T>> {
    private static final int LIMIT_SEQUENTIAL_SORT = 50;

    private final List<T> aList;
    private final int leftIndex;
    private final int rightIndex;

    private MergeSortTask(final List<T> aList, final int leftIndex, final int rightIndex) {
        this.aList = aList;
        this.leftIndex = leftIndex;
        this.rightIndex = rightIndex;
    }

    public static <T extends Comparable<T>> MergeSortTask<T> of(final List<T> aList) {
        return new MergeSortTask<>(aList, 0, aList.size() - 1);
    }

    @Override
    protected List<T> compute() {
        if (rightIndex - leftIndex < LIMIT_SEQUENTIAL_SORT) {
            return sequentialMergeSort(aList, leftIndex, rightIndex);
        }

        var middleIndex = (leftIndex + rightIndex) / 2;

        var leftSortTask = new MergeSortTask<>(aList, leftIndex, middleIndex);
        var rightSortTask = new MergeSortTask<>(aList, middleIndex + 1, rightIndex);

        invokeAll(leftSortTask, rightSortTask);

        var leftSortedList = leftSortTask.join();
        var rightSortedList = rightSortTask.join();
        return mergeTwoSortedLists(leftSortedList, rightSortedList);
    }

    private static <T extends Comparable<T>> List<T> sequentialMergeSort(List<T> aList, int leftIndex, int rightIndex) {
        if (leftIndex >= rightIndex) {
            if (aList.isEmpty()) {
                return Collections.emptyList();
            } else {
                return Collections.singletonList(aList.get(leftIndex));
            }
        }

        var middleIndex = (leftIndex + rightIndex) / 2;
        var leftSortedList = sequentialMergeSort(aList, leftIndex, middleIndex);
        var rightSortedList = sequentialMergeSort(aList, middleIndex + 1, rightIndex);
        return mergeTwoSortedLists(leftSortedList, rightSortedList);
    }

    private static <T extends Comparable<T>> List<T> mergeTwoSortedLists(final List<T> leftList, final List<T> rightList) {
        List<T> mergedList = new ArrayList<>(leftList.size() + rightList.size());
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < leftList.size() && rightIndex < rightList.size()) {
            var leftValue = leftList.get(leftIndex);
            var rightValue = rightList.get(rightIndex);
            if (leftValue.compareTo(rightValue) < 0) {
                mergedList.add(leftValue);
                leftIndex++;
            } else {
                mergedList.add(rightValue);
                rightIndex++;
            }
        }

        for (; leftIndex < leftList.size(); leftIndex++) {
            mergedList.add(leftList.get(leftIndex));
        }

        for (; rightIndex < rightList.size(); rightIndex++) {
            mergedList.add(rightList.get(rightIndex));
        }
        return mergedList;
    }
}
