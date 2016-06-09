package com.excilys.core.parser;

import java.io.File;
import java.io.IOException;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.excilys.core.doublon.error.Error;
import com.excilys.core.doublon.model.Rapport;
import com.excilys.core.dto.ComputerDTO;

import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Created by Maxime Angot on 09/06/16.
 */
public class XmlReader {

    private static String DATE_REGEX = "(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((19|20)\\d\\d)|((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])|^$";

    public static Rapport parseFile(MultipartFile file) {
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

    private static NodeList readFile(File file) {
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

    private static Rapport mapDatas(NodeList elts) {
        Rapport pReturn = new Rapport();
        List<ComputerDTO> list = new ArrayList<ComputerDTO>(elts.getLength());
        List<String> errors = new ArrayList<String>();

        for (int i = 0; i < elts.getLength(); i++) {
            Node nNode = elts.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                ComputerDTO temp = new ComputerDTO();
                temp.setCompanyName(eElement.getElementsByTagName("name").item(0).getTextContent());
                temp.setIntroduced(eElement.getElementsByTagName("introduced").item(0).getTextContent());
                temp.setDiscontinued(eElement.getElementsByTagName("discontinued").item(0).getTextContent());
                temp.setCompanyName(eElement.getElementsByTagName("company_name").item(0).getTextContent());
                Timestamp s = new Timestamp();
                s.
                internalValidator.validate(temps);
            }

        }
        return pReturn;
    }

    private static Error internalValidator(ComputerDTO computer) {

        // computer name
        if (computer == null || computer.getName().equals("") || computer.getName().equals(" ")) {

        }

        Pattern pattern;
        Matcher matcher;
        // introduced date
        if ((computer.getIntroduced() != null) & !"".equals(computer.getIntroduced())) {
            pattern = Pattern.compile(DATE_REGEX);
            matcher = pattern.matcher(computer.getIntroduced());
            if (!matcher.matches()) {

            }
        }

        // discontinued date
        if ((computer.getDiscontinued() != null) && !"".equals(computer.getDiscontinued())) {
            pattern = Pattern.compile(DATE_REGEX);
            matcher = pattern.matcher(computer.getDiscontinued());
            if (!matcher.matches()) {

            }
        }

        // company name
        if ((computer.getCompanyName() != null) || computer.getCompanyName().equals("") || computer.getCompanyName().equals(" ")) {

        }

    }
}
