package restapi.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import restapi.pojo.RequestResponsePojo;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

/**
 * @author Максим Зеленский
 * @since 06.04.2020
 */
public class XMLParserUtil {

    public static String getXmlRequest(RequestResponsePojo requestData) throws Exception {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        document.setXmlStandalone(true);

        // Root element
        Element rootElement = document.createElementNS("http://kvs.pfr.com/snils-by-additionalData/1.0.1",
                "tns:SnilsByAdditionalDataRequest");
        rootElement.setAttribute("xmlns:smev", "urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1");
        rootElement.setAttribute("xmlns:pfr", "http://common.kvs.pfr.com/1.0.0");
        document.appendChild(rootElement);

        // Family name
        Element childElement = document.createElement("smev:FamilyName");
        childElement.appendChild(document.createTextNode(requestData.getFamily_name()));
        rootElement.appendChild(childElement);
        // First name
        childElement = document.createElement("smev:FirstName");
        childElement.appendChild(document.createTextNode(requestData.getFirst_name()));
        rootElement.appendChild(childElement);
        // Patronymic
        if (requestData.getPatronymic() != null) {
            childElement = document.createElement("smev:Patronymic");
            childElement.appendChild(document.createTextNode(requestData.getPatronymic()));
            rootElement.appendChild(childElement);
        }

        // Birth date
        childElement = document.createElement("tns:BirthDate");
        childElement.appendChild(document.createTextNode(new Date(requestData.getBirth_date()).toString() ));
        rootElement.appendChild(childElement);
        // Gender
        childElement = document.createElement("tns:Gender");
        childElement.appendChild(document.createTextNode(requestData.getGender().equals("МУЖСКОЙ") ? "Male" : "Female" ));
        rootElement.appendChild(childElement);

        //Birth place
        if (requestData.getPlace_type() != null && requestData.getSettlement() != null) {
            childElement = document.createElement("tns:BirthPlace");
            // Place type
            Element childOfChildElement = document.createElement("pfr:PlaceType");
            childOfChildElement.appendChild(document.createTextNode(requestData.getPlace_type()));
            childElement.appendChild(childOfChildElement);
            // Settlement
            childOfChildElement = document.createElement("pfr:Settlement");
            childOfChildElement.appendChild(document.createTextNode(requestData.getSettlement()));
            childElement.appendChild(childOfChildElement);
            // District
            if (requestData.getDistrict() != null) {
                childOfChildElement = document.createElement("pfr:District");
                childOfChildElement.appendChild(document.createTextNode(requestData.getDistrict()));
                childElement.appendChild(childOfChildElement);
            }
            // Region pfr:Region
            if (requestData.getRegion() != null) {
                childOfChildElement = document.createElement("pfr:Region");
                childOfChildElement.appendChild(document.createTextNode(requestData.getRegion()));
                childElement.appendChild(childOfChildElement);
            }
            // Country pfr:Country
            if (requestData.getCountry() != null) {
                childOfChildElement = document.createElement("pfr:Country");
                childOfChildElement.appendChild(document.createTextNode(requestData.getCountry()));
                childElement.appendChild(childOfChildElement);
            }
            rootElement.appendChild(childElement);
        }

        // Passport data
        if (requestData.getPassport_series() != null && requestData.getPassport_number() != null &&
                requestData.getPassport_issue_date() != null && requestData.getPassport_issuer() != null) {
            childElement = document.createElement("smev:PassportRF");
            // Series
            Element childOfChildElement = document.createElement("smev:Series");
            childOfChildElement.appendChild(document.createTextNode(requestData.getPassport_series()));
            childElement.appendChild(childOfChildElement);
            // Number
            childOfChildElement = document.createElement("smev:Number");
            childOfChildElement.appendChild(document.createTextNode(requestData.getPassport_number()));
            childElement.appendChild(childOfChildElement);
            // IssueDate
            childOfChildElement = document.createElement("smev:IssueDate");
            childOfChildElement.appendChild(document.createTextNode(new Date(requestData.getPassport_issue_date()).toString()));
            childElement.appendChild(childOfChildElement);
            // Issuer
            childOfChildElement = document.createElement("smev:Issuer");
            childOfChildElement.appendChild(document.createTextNode(requestData.getPassport_issuer()));
            childElement.appendChild(childOfChildElement);
            rootElement.appendChild(childElement);
        }


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource domSource = new DOMSource(document);
        StringWriter writer = new StringWriter();
        StreamResult streamResult = new StreamResult(writer);

        transformer.transform(domSource, streamResult);

        return writer.toString();
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
                            String gender = event.asCharacters().getData().equals("Male") ? "MALE" : "FEMALE";
                            obj.setGender(gender);
                            break;
                        case PLACE_TYPE_TAG:
                            event = eventReader.nextEvent();
                            String placeType = event.asCharacters().getData().equals("ОСОБОЕ") ? "SPECIAL" : "STANDART";
                            obj.setPlace_type(placeType);
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
