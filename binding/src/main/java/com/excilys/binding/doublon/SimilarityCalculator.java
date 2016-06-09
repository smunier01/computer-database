package com.excilys.binding.doublon;

public interface SimilarityCalculator {

    /**
     * Get the percentage of the similarity between two string.
     * @param left the first string
     * @param right the second string
     * @return
     */
    double getPercentSimilarity(String left, String right);

}
