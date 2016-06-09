package com.excilys.binding.doublon;

public class Hamming implements SimilarityCalculator {

    private int getValue(String left, String right) {
        left = left.toLowerCase();
        right = right.toLowerCase();

        if (left.length() != right.length()) {
            while (left.length() < right.length()) {
                left += " ";
            }
            while (left.length() > right.length()) {
                right += " ";
            }
        }

        int distance = 0;

        for (int i = 0; i < left.length(); i++) {
            if (left.charAt(i) != right.charAt(i)) {
                distance++;
            }
        }

        return distance;
    }

    @Override
    public double getPercentSimilarity(String left, String right) {
        return 100 - ( getValue(left, right) / ( (left.length() + right.length()) / 2 ) );
    }
}
