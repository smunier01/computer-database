package com.excilys.core.conflict.doublon;

import com.excilys.core.conflict.Conflict;
import com.excilys.core.conflict.ErrorType;
import com.excilys.core.dto.ComputerDTO;

import java.util.List;

public class Doublon extends Conflict {
    private static final ErrorType errorType = ErrorType.DOUBLE;

    private List<ComputerDTO> conflicts;

    public Doublon(ComputerDTO computerDTO, List<ComputerDTO> computerDTOs) {
        this.computerDTO = computerDTO;
        this.conflicts = computerDTOs;
    }

    public static ErrorType getErrorType() {
        return errorType;
    }

    public List<ComputerDTO> getConflicts() {
        return conflicts;
    }

    public void setConflicts(List<ComputerDTO> conflicts) {
        this.conflicts = conflicts;
    }
}
