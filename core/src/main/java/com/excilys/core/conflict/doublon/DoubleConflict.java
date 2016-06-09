package com.excilys.core.conflict.doublon;

import com.excilys.core.conflict.Conflict;
import com.excilys.core.conflict.ErrorType;
import com.excilys.core.dto.ComputerDTO;

import java.util.List;

public class DoubleConflict  extends Conflict{
    private static final ErrorType errorType = ErrorType.DOUBLE;

    private List<ComputerDTO> conflicts;
}
