package com.excilys.core.conflict.format;

import com.excilys.core.conflict.Conflict;
import com.excilys.core.conflict.ErrorType;
import com.excilys.core.dto.ComputerDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Error extends Conflict{
    private static final ErrorType errorType = ErrorType.FORMAT;

    // list of the variables
    private Map<Fields, List<ErrorMessage>> errorMap;

    public Error(){}

    public Error(ComputerDTO computerDTO, Map<Fields, List<ErrorMessage>> errorMap) {
        super(computerDTO);
        this.errorMap = errorMap;
    }

    /**
     * Return the list corresponding to the key in Errors Object.
     *
     * @param key
     *            the key in the Errors Object for the wanted list
     * @return the wanted list
     */
    public List<ErrorMessage> getListForKey(Fields key) {
        if (this.errorMap.containsKey(key)) {
            return this.errorMap.get(key);
        } else {
            List<ErrorMessage> list = new ArrayList<>();
            this.errorMap.put(key, list);
            return list;

        }
    }

    public Map<Fields, List<ErrorMessage>> getErrorMap() {
        return errorMap;
    }

    public void setErrorMap(Map<Fields, List<ErrorMessage>> errorMap) {
        this.errorMap = errorMap;
    }

    public Map<Fields, List<ErrorMessage>> getErrorMap() {
        return errorMap;
    }
}
