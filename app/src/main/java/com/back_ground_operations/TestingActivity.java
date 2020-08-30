package com.back_ground_operations;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.w3c.dom.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TestingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);


        String s1 = "lik fkjdfhkjdshfkdsfskd";
        String s2 = "Manoj like to code";

        int res = isSubstring(s1, s2);

        if (res == -1)
            Log.e("Checking String", "Not present");
        else
            Log.e("Checking String", "Present at index " + res);
    }

    private int isSubstring(String s1, String s2) {
        int M = s1.length();
        int N = s2.length();
        /* A loop to slide pat[] one by one */
        for (int i = 0; i <= N - M; i++) {
            int j;
            /* For current index i, check for
            pattern match */
            for (j = 0; j < M; j++)
                if (s2.charAt(i + j) != s1.charAt(j))
                    break;

            if (j == M)
                return i;
        }
        return -1;
    }

    private void findDuplicateElementsInString(String stringValue) {
        HashMap<Character, Integer> charHash = new HashMap<>();

        char[] array = stringValue.toCharArray();

        for (int i = 0; i < array.length; i++) {
            if (charHash.get(Character.toLowerCase(array[i])) == null) {
                charHash.put(Character.toLowerCase(array[i]), 1);
            } else {
                charHash.put(Character.toLowerCase(array[i]), charHash.get(Character.toLowerCase(array[i])) + 1);
            }
        }

        Set<Map.Entry<Character, Integer>> setValue = charHash.entrySet();

        for (Map.Entry<Character, Integer> event : setValue) {
            Log.e("char occurrence", event.getKey() + "==============>" + event.getValue());
        }
    }

    /*
     * to find the present duplicate elements in an array using hash map
     * */
    private void findDuplicateElements() {
        int[] numbers = new int[]{43, 65, 5, 34, 54523, 656, 735, 243, 54523, 34, 12567, 78, 65, 3};

        HashMap<Integer, Integer> values = new HashMap<>();

        for (int number : numbers) {
            if (values.get(number) == null) {
                values.put(number, 1);
            } else {
                values.put(number, values.get(number) + 1);
            }
        }

        Set<Map.Entry<Integer, Integer>> data = values.entrySet();

        for (Map.Entry<Integer, Integer> event : data) {
            if (event.getValue() > 1) {
                Log.e("values", event.getKey() + "=====>" + event.getValue());
            }
        }
    }

    /*
     * to find the largest and second largest even if duplicate elements are there
     * */
    private void largestAndSecondLargestOfArray() {
        int[] numbers = new int[]{43, 65, 5, 34, 54523, 656, 735, 243, 54524, 23, 12567, 78, 65, 3};
        int highest = Integer.MIN_VALUE;//         -2147483648
        int secondHighest = Integer.MIN_VALUE;//        2147483647

        for (int number : numbers) {
            if (number > highest) {
                secondHighest = highest;
                highest = number;
            }

            if (number < highest && number > secondHighest) {
                secondHighest = number;
            }
        }

        Log.e("Highest", "largestAndSecondLargestOfArray: " + highest);
        Log.e("Second Highest", "largestAndSecondLargestOfArray: " + secondHighest);
    }
}
