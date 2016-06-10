package com.excilys.service.doublon;



import com.excilys.core.conflict.Rapport;
import com.excilys.core.dto.ComputerDTO;
import java.util.List;

public interface DoublonService {

    /**
     * Use to generate the rapport of the computer conflict.
     *
     * @param computers list to check
     * @return a rapport
     */
    Rapport getRapport(List<ComputerDTO> computers);

}
