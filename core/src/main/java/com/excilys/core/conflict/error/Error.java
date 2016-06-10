package com.excilys.core.conflict.error;

import com.excilys.core.conflict.format.Fields;

import java.util.*;

public class Error {

    // list of the variables
    private Map<Fields, List<String>> errorMap;

    /**
     * The default constructor.
     */
    public Error() {
        this.errorMap = new HashMap<Fields, List<String>>();
    }

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

    public Map<Fields, List<String>> getErrorMap() {
        return errorMap;
    }
}
