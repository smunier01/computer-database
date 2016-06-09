package com.excilys.core.doublon.error;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Error {

    // list of the variables
    private Map<Fields, List<String>> errorMap;

    /**
     * Return the list corresponding to the key in Errors Object.
     *
     * @param key
     *            the key in the Errors Object for the wanted list
     * @return the wanted list
     */
    public List<String> getListForKey(Fields key) {
        if (this.errorMap.containsKey(key)) {
            return this.errorMap.get(key);
        } else {
            List<String> list = new ArrayList<>();
            this.errorMap.put(key, list);
            return list;

        }
    }

}
