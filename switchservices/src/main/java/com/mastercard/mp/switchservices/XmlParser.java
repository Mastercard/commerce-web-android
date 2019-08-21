package com.mastercard.mp.switchservices;

import java.io.StringWriter;
import java.io.Writer;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * XmlParser class to convert AuthorizeCheckout request to XML and convert AuthorizeCheckout
 * response to a data object
 */
public class XmlParser {
  private Serializer serializer;

  public XmlParser() {
    serializer = new Persister();
  }

  public <T extends Object> String generateXml(T request) throws Exception {
    Writer writer = new StringWriter();
    serializer.write(request, writer);

    return writer.toString();
  }

  public <T> T deserializeXml(Class<T> clazz, String xml) throws Exception {
    return serializer.read(clazz, xml);
  }
}