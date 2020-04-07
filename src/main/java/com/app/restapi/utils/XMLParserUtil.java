package com.app.restapi.utils;

import com.app.restapi.pojo.RequestResponsePojo;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;

/**
 * @author Максим Зеленский
 * @since 06.04.2020
 */
public class XMLParserUtil {
    private static final String REQUEST_TEMPLATE_XML = "request_template.xml";
    private static final String RESPONSE_TEMPLATE_XML = "response_template.xml";
    private static final String END_LINE = Character.valueOf((char)13).toString() + Character.valueOf((char)10).toString();
    private static final String TAB = "    ";
    private static final String DOUBLE_TAB = TAB + TAB;
    // Data to replace
    private static final String FAMILY_NAME = "FAMILY_NAME";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String PATRONYMIC = "PATRONYMIC";
    private static final String BIRTH_DATE = "BIRTH_DATE";
    private static final String GENDER = "GENDER";
    // Birth place data to replace
    private static final String PLACE_TYPE = "PLACE_TYPE";
    private static final String SETTLEMENT = "SETTLEMENT";
    private static final String DISTRICT = "DISTRICT";
    private static final String REGION = "REGION";
    private static final String COUNTRY = "COUNTRY";
    // Passport data to replace
    private static final String SERIES = "SERIES";
    private static final String NUMBER = "NUMBER";
    private static final String ISSUE_DATE = "ISSUE_DATE";
    private static final String ISSUER = "ISSUER";
    // Lines to remove
    private static final String PATRONYMIC_LINE = TAB + "<smev:Patronymic>" + PATRONYMIC + "</smev:Patronymic>" + END_LINE;
    // Birth place lines to remove
    private static final String PLACE_TYPE_LINE = DOUBLE_TAB + "<pfr:PlaceType>" + PLACE_TYPE + "</pfr:PlaceType>" + END_LINE;
    private static final String SETTLEMENT_LINE = DOUBLE_TAB + "<pfr:Settlement>" + SETTLEMENT + "</pfr:Settlement>" + END_LINE;
    private static final String DISTRICT_LINE = DOUBLE_TAB + "<pfr:District>" + DISTRICT + "</pfr:District>" + END_LINE;
    private static final String REGION_LINE = DOUBLE_TAB + "<pfr:Region>" + REGION + "</pfr:Region>" + END_LINE;
    private static final String COUNTRY_LINE = DOUBLE_TAB + "<pfr:Country>" + COUNTRY + "</pfr:Country>" + END_LINE;
    // Passport data lines to remove
    private static final String SERIES_LINE = DOUBLE_TAB + "<smev:Series>" + SERIES + "</smev:Series>" + END_LINE;
    private static final String NUMBER_LINE = DOUBLE_TAB + "<smev:Number>" + NUMBER + "</smev:Number>" + END_LINE;
    private static final String ISSUE_DATE_LINE = DOUBLE_TAB + "<smev:IssueDate>" + ISSUE_DATE + "</smev:IssueDate>" + END_LINE;
    private static final String ISSUER_LINE = DOUBLE_TAB + "<smev:Issuer>" + ISSUER + "</smev:Issuer>" + END_LINE;
    // Blocks to remove
    private static final String BIRTH_PLACE_BLOCK = TAB + "<tns:BirthPlace>" + END_LINE + PLACE_TYPE_LINE
            + SETTLEMENT_LINE + DISTRICT_LINE + REGION_LINE + COUNTRY_LINE + TAB + "</tns:BirthPlace>" + END_LINE;
    private static final String PASSPORT_DATA_BLOCK = TAB + "<smev:PassportRF>" + END_LINE + SERIES_LINE
            + NUMBER_LINE + ISSUE_DATE_LINE + ISSUER_LINE + TAB + "</smev:PassportRF>" + END_LINE;

    public static String getRequestXML(RequestResponsePojo requestData) throws Exception{
        String reqTemplate = readFromFile(REQUEST_TEMPLATE_XML);
        if (reqTemplate == null) {
            throw new Exception("Can't read file " + REQUEST_TEMPLATE_XML);
        }

        // Simple data
        reqTemplate = reqTemplate.replace(FAMILY_NAME, requestData.getFamily_name());
        reqTemplate = reqTemplate.replace(FIRST_NAME, requestData.getFirst_name());
        if (requestData.getPatronymic() != null) {
            reqTemplate = reqTemplate.replace(PATRONYMIC, requestData.getPatronymic());
        } else {
            // Remove line
            reqTemplate = reqTemplate.replace(PATRONYMIC_LINE, "");
        }
        reqTemplate = reqTemplate.replace(BIRTH_DATE, (new Date(requestData.getBirth_date())).toString());
        reqTemplate = reqTemplate.replace(GENDER, requestData.getGender().equals("МУЖСКОЙ") ? "Male" : "Female");

        // Birth place data
        if (requestData.getPlace_type() != null && requestData.getSettlement() != null) {
            reqTemplate = reqTemplate.replace(PLACE_TYPE, requestData.getPlace_type());
            reqTemplate = reqTemplate.replace(SETTLEMENT, requestData.getSettlement());
            // District
            if (requestData.getDistrict() != null) {
                reqTemplate = reqTemplate.replace(DISTRICT, requestData.getDistrict());
            } else {
                // Remove district
                reqTemplate = reqTemplate.replace(DISTRICT_LINE, "");
            }
            // Region
            if (requestData.getRegion() != null) {
                reqTemplate = reqTemplate.replace(REGION, requestData.getRegion());
            } else {
                // Remove region
                reqTemplate = reqTemplate.replace(REGION_LINE, "");
            }
            // Country
            if (requestData.getCountry() != null) {
                reqTemplate = reqTemplate.replace(COUNTRY, requestData.getCountry());
            } else {
                // Remove country
                reqTemplate = reqTemplate.replace(COUNTRY_LINE, "");
            }
        } else {
            // Remove birth place block
            reqTemplate = reqTemplate.replace(BIRTH_PLACE_BLOCK, "");
        }

        // Passport data
        if (requestData.getPassport_series() != null && requestData.getPassport_number() != null &&
            requestData.getPassport_issue_date() != null && requestData.getPassport_issuer() != null) {
            reqTemplate = reqTemplate.replace(SERIES, requestData.getPassport_series());
            reqTemplate = reqTemplate.replace(NUMBER, requestData.getPassport_number());
            reqTemplate = reqTemplate.replace(ISSUE_DATE, (new Date(requestData.getPassport_issue_date())).toString());
            reqTemplate = reqTemplate.replace(ISSUER, requestData.getPassport_issuer());
        } else {
            // Remove passport data block
            reqTemplate = reqTemplate.replace(PASSPORT_DATA_BLOCK, "");
        }

        return reqTemplate;
    }

    private static String readFromFile(String fileName) {
        URL res = XMLParserUtil.class.getClassLoader().getResource(fileName);
        if (res == null) {
            return null;
        } else {
            try {
                return new String(Files.readAllBytes(Paths.get(res.toURI())));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
                return null;
            }
        }
    }


    // Tags
    private static final String FAMILY_NAME_TAG = "FamilyName";
    private static final String FIRST_NAME_TAG = "FirstName";
    private static final String PATRONYMIC_TAG = "Patronymic";
    private static final String BIRTH_DATE_TAG = "BirthDate";
    private static final String GENDER_TAG = "Gender";
    private static final String SNILS_TAG = "Snils";
    private static final String PLACE_TYPE_TAG = "PlaceType";
    private static final String SETTLEMENT_TAG = "Settlement";
    private static final String DISTRICT_TAG = "District";
    private static final String REGION_TAG = "Region";
    private static final String COUNTRY_TAG = "Country";
    private static final String SERIES_TAG = "Series";
    private static final String NUMBER_TAG = "Number";
    private static final String ISSUE_DATE_TAG = "IssueDate";
    private static final String ISSUER_TAG = "Issuer";

    public static RequestResponsePojo getPojoFromRequestXML(String xml) {
        RequestResponsePojo obj = new RequestResponsePojo();
        try {
            byte[] byteArray = xml.getBytes(StandardCharsets.UTF_8);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = inputFactory.createXMLEventReader(inputStream);

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    switch (event.asStartElement().getName().getLocalPart()) {
                        case FAMILY_NAME_TAG:
                            event = eventReader.nextEvent();
                            obj.setFamily_name(event.asCharacters().getData());
                            break;
                        case FIRST_NAME_TAG:
                            event = eventReader.nextEvent();
                            obj.setFirst_name(event.asCharacters().getData());
                            break;
                        case PATRONYMIC_TAG:
                            event = eventReader.nextEvent();
                            obj.setPatronymic(event.asCharacters().getData());
                            break;
                        case BIRTH_DATE_TAG:
                            event = eventReader.nextEvent();
                            Date date = Date.valueOf(event.asCharacters().getData());
                            obj.setBirth_date(date.getTime());
                            break;
                        case GENDER_TAG:
                            event = eventReader.nextEvent();
                            String gender = event.asCharacters().getData().equals("Male") ? "MУЖСКОЙ" : "ЖЕНСКИЙ";
                            obj.setGender(gender);
                            break;
                        case PLACE_TYPE_TAG:
                            event = eventReader.nextEvent();
                            obj.setPlace_type(event.asCharacters().getData());
                            break;
                        case SETTLEMENT_TAG:
                            event = eventReader.nextEvent();
                            obj.setSettlement(event.asCharacters().getData());
                            break;
                        case DISTRICT_TAG:
                            event = eventReader.nextEvent();
                            obj.setDistrict(event.asCharacters().getData());
                            break;
                        case REGION_TAG:
                            event = eventReader.nextEvent();
                            obj.setRegion(event.asCharacters().getData());
                            break;
                        case COUNTRY_TAG:
                            event = eventReader.nextEvent();
                            obj.setCountry(event.asCharacters().getData());
                            break;
                        case SERIES_TAG:
                            event = eventReader.nextEvent();
                            obj.setPassport_series(event.asCharacters().getData());
                            break;
                        case NUMBER_TAG:
                            event = eventReader.nextEvent();
                            obj.setPassport_number(event.asCharacters().getData());
                            break;
                        case ISSUE_DATE_TAG:
                            event = eventReader.nextEvent();
                            date = Date.valueOf(event.asCharacters().getData());
                            obj.setPassport_issue_date(date.getTime());
                            break;
                        case ISSUER_TAG:
                            event = eventReader.nextEvent();
                            obj.setPassport_issuer(event.asCharacters().getData());
                            break;
                        default:
                            break;
                    }
                }

            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static String getSnilsFromResponseXML(String xml) {
        try {
            byte[] byteArray = xml.getBytes(StandardCharsets.UTF_8);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = inputFactory.createXMLEventReader(inputStream);

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    if (event.asStartElement().getName()
                            .getLocalPart().equals(SNILS_TAG)) {
                        event = eventReader.nextEvent();
                        return (event.asCharacters().getData());
                    }
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return null;
    }

}
