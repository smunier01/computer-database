package com.excilys.binding.doublon;

public class Levenshtein implements SimilarityCalculator {

    private int getValue(String left, String right) {
        int[][] matrix = new int[left.length() + 1][right.length() + 1];

        for (int i = 0; i <= left.length(); i++) {
            matrix[i][0] = i;
        }

        for (int j = 0; j <= right.length(); j++) {
            matrix[0][j] = j;
        }

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                if (left.charAt(i - 1) == right.charAt(j - 1)) {
                    matrix[i][j] = matrix[i - 1][j - 1];
                } else {
                    int minimum = Integer.MAX_VALUE;
                    if ((matrix[i - 1][j]) + 1 < minimum) {
                        minimum = (matrix[i - 1][j]) + 1;
                    }

                    if ((matrix[i][j - 1]) + 1 < minimum) {
                        minimum = (matrix[i][j - 1]) + 1;
                    }

                    if ((matrix[i - 1][j - 1]) + 1 < minimum) {
                        minimum = (matrix[i - 1][j - 1]) + 1;
                    }

                    matrix[i][j] = minimum;
                }
            }
        }
        return matrix[left.length()][right.length()];
    }

    @Override
    public double getPercentSimilarity(String left, String right) {
        return 100 - ( getValue(left, right) / ( (left.length() + right.length()) / 2 ) );
    }
}
