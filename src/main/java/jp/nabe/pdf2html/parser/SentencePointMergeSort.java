package jp.nabe.pdf2html.parser;

import java.util.List;
import java.util.ListIterator;

public class SentencePointMergeSort {
    private static final int INSERTIONSORT_THRESHOLD = 7;

    public void sort(List<Sentence> list) {
        Sentence[] a = list.toArray(new Sentence[0]);
        sort(a);
        ListIterator<Sentence> i = list.listIterator();
        for (int j=0; j<a.length; j++) {
            i.next();
            i.set((Sentence) a[j]);
        }
    }

    public void sort(Sentence[] a) {
        Sentence[] aux = a.clone();
        sort(aux, a, 0, a.length, 0);
    }

    public void sort(Sentence[] src, Sentence[] dest, int low, int high, int off) {
        int length = high - low;

        // Insertion sort on smallest arrays
        if (length < INSERTIONSORT_THRESHOLD) {
            for (int i = low; i < high; i++)
                for (int j = i; j > low && compare(dest[j - 1], dest[j]) > 0; j--)
                    swap(dest, j, j - 1);
            return;
        }

        // Recursively sort halves of dest into src
        int destLow  = low;
        int destHigh = high;
        low  += off;
        high += off;
        int mid = (low + high) >>> 1;
        sort(dest, src, low, mid, -off);
        sort(dest, src, mid, high, -off);

        // If list is already sorted, just copy from src to dest.  This is an
        // optimization that results in faster sorts for nearly ordered lists.
        if (compare(src[mid-1], src[mid]) <= 0) {
            System.arraycopy(src, low, dest, destLow, length);
            return;
        }

        // Merge sorted halves (now in src) into dest
        for (int i = destLow, p = low, q = mid; i < destHigh; i++) {
            if (q >= high || p < mid && compare(src[p], src[q]) <= 0) {
                dest[i] = src[p++];
            } else {
                dest[i] = src[q++];
            }
        }
    }

    protected int compare(Sentence s1, Sentence s2) {
        if (s1 == null) {
            if (s2 == null) {
                return 0;
            } else {
                return 1;
            }
        } else if (s2 == null) {
            return -1;
        }
        if (s1.equals(s2)) {
            return 0;
        }

        if (s1.near(s2)) {
            return 0;
        }

        return s1.compareTo(s2);
    }

    protected void swap(Sentence[] x, int a, int b) {
        Sentence t = x[a];
        x[a] = x[b];
        x[b] = t;
    }
}
