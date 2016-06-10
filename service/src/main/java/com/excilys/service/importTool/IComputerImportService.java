package com.excilys.service.importTool;


import com.excilys.core.conflict.Conflict;
import com.excilys.core.conflict.Rapport;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IComputerImportService {

    Rapport importComputersFromCSV(MultipartFile file);

    Rapport importComputersFromXML(MultipartFile file);
}
