package com.excilys.service.importTool.impl;

import au.com.bytecode.opencsv.CSVReader;
import com.excilys.binding.validation.ComputerValidator;
import com.excilys.binding.validation.ValidatorUtil;
import com.excilys.core.conflict.Conflict;
import com.excilys.core.conflict.Rapport;
import com.excilys.core.conflict.format.Error;
import com.excilys.core.conflict.format.ErrorMessage;
import com.excilys.core.conflict.format.Fields;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.core.model.Computer;
import com.excilys.service.importTool.IComputerImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ComputerImportService implements IComputerImportService {

    @Autowired
    private ComputerValidator computerValidator;
    public static final String RESOURCE = "/home/nbelleme/Bureau/dev/cdb/service/src/main/resources/computer.csv";

    @Override
    public List<Conflict> importComputersFromCSV() {

        File file = new File(RESOURCE);
        List<Conflict> conflicts = new ArrayList<>();
        try {
            FileReader fr = new FileReader(file);
            CSVReader csvReader = new CSVReader(fr, ',');
            csvReader.readNext();
            String[] nextLine;
            List<Computer> computers = new ArrayList<>();

            ValidatorUtil validatorUtil = new ValidatorUtil();
            while ((nextLine = csvReader.readNext()) != null) {
                String name = nextLine[0];
                String introducedString = nextLine[1];
                String discontinuedString = nextLine[2];
                String companyName = nextLine[3];

                ComputerDTO computerDTO = new ComputerDTO.Builder()
                        .name(name)
                        .introduced(introducedString)
                        .discontinued(discontinuedString)
                        .build();

                Map<Fields, List<ErrorMessage>> errors = computerValidator.validateComputerDTO(computerDTO);
                System.out.println(errors.toString());
                Error conflict = new Error();
                conflict.setComputerDTO(computerDTO);
                conflict.setErrorMap(errors);
                conflicts.add(conflict);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return conflicts;
    }

    private Rapport mapDatas(NodeList elts) {
        Rapport pReturn = new Rapport();
        List<ComputerDTO> conflicComputer = new ArrayList<ComputerDTO>();
        List<String> errors = new ArrayList<String>();
        List<Conflict> conflicts = new ArrayList<>();

        for (int i = 0; i < elts.getLength(); i++) {
            Node nNode = elts.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                ComputerDTO temp = new ComputerDTO();
                temp.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                temp.setIntroduced(eElement.getElementsByTagName("introduced").item(0).getTextContent());
                temp.setDiscontinued(eElement.getElementsByTagName("discontinued").item(0).getTextContent());
                temp.setCompanyName(eElement.getElementsByTagName("company_name").item(0).getTextContent());

                Map<Fields, List<ErrorMessage>> err = computerValidator.validateComputerDTO(temp);
                if (err.size() == 0) {
                    pReturn.getToImport().add(temp);
                } else {
                    Error conflict = new Error();
                    conflict.setComputerDTO(temp);
                    conflict.setErrorMap(err);
                    conflicts.add(conflict);

                    pReturn.setRefuse(conflicts);
                }
            }
        }

        return pReturn;
    }

    private  NodeList readFile(File file) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = dBuilder.parse(file);
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //optional, but recommended
        //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();


        return doc.getElementsByTagName("computer");
    }

    public  Rapport importComputersFromXML(MultipartFile file) {
        File convFile = new File(file.getOriginalFilename());
        try {
            file.transferTo(convFile);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mapDatas(readFile(convFile));
    }
}
