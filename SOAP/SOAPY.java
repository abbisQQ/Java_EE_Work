package demo.view;

import java.net.HttpURLConnection;

import java.net.URL;

import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;


public class SOAPY {
    public SOAPY() {
        super();
    }


    public static void main(String[] args) {

        //Part One sending the request
        try {

            URL url =
                new URL("http://www.holidaywebservice.com/HolidayService_v2/HolidayService2.asmx?op=GetHolidaysAvailable");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
            String countryCode = "Canada";
            String xml =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\"> " +
                " <soap12:Body> " +
                " <GetHolidaysAvailable xmlns=\"http://www.holidaywebservice.com/HolidayService_v2/\"> " +
                " <countryCode>" + countryCode + "</countryCode>" + " </GetHolidaysAvailable>" + " </soap12:Body>" +
                "</soap12:Envelope>";

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(xml);
            wr.flush();
            wr.close();
            
            //Part Two get the response
            String responseStatus = con.getResponseMessage();
            System.out.print(responseStatus);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while (((inputLine = in.readLine()) != null)) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(response);
            
            //Part 3 make the response a Document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(response.toString())));
            
            //Part 4 get the data from our document
            NodeList n = document.getElementsByTagName("Description");
            
            System.out.println(n.getLength());
            
            for(int i=0;i<n.getLength();i++){
            
            Element element = (Element)n.item(i);
            System.out.println(element.getTextContent());
            
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }


}
