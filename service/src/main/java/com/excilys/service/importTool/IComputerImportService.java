package com.excilys.service.importTool;


import com.excilys.core.conflict.Conflict;
import com.excilys.core.conflict.Rapport;

import java.io.File;
import java.util.List;

public interface IComputerImportService {

   List<Conflict> importComputersFromCSV();
//   Rapport importComputersFromXML();
}
