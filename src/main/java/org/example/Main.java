package org.example;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SAXException, IOException {
        final String xsdPath = "sample1.xsd";
        final String xmlPath = "sample1.xml";
        listParsingExceptions(xsdPath, xmlPath);
    }

    private static Validator initValidator(final String xsdPath) throws SAXException {
        final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        final Source schemaFile = new StreamSource(xsdPath);
        final Schema schema = factory.newSchema(schemaFile);
        return schema.newValidator();
    }

    public static List<SAXParseException> listParsingExceptions(final String xsdPath, final String xmlPath) throws IOException, SAXException {
        final XmlErrorHandler xsdErrorHandler = new XmlErrorHandler();
        final Validator validator = initValidator(xsdPath);
        validator.setErrorHandler(xsdErrorHandler);

        try {
            System.out.println(validator.getClass().getName());
            validator.validate(new StreamSource(xmlPath));
        } catch (SAXParseException e)
        {
            // ...
        }

        System.out.println("ERRORS : ");
        xsdErrorHandler.getExceptions().forEach(e -> System.out.println(e.getMessage()));
        return xsdErrorHandler.getExceptions();
    }
}