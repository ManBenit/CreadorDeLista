package listmaker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/*
To this proyect, all created nodes will be placed into root one
*/

public final class XMLMaker{
    private Document doc;
    private Element root;
    private String pathToSave;
    
    private XMLMaker(){
        try{
            DocumentBuilderFactory docFactory= DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder= docFactory.newDocumentBuilder();
            doc= docBuilder.newDocument();
            
            //Create by default the root element
            root= doc.createElement("rootnode");
            doc.appendChild(root);
        }catch(ParserConfigurationException ex){
            new Message().showMessage(Message.ERROR, ex.toString());
        }
    }
    
    public static XMLMaker getInstance(){
        return new XMLMaker();
    }
    
    public String[] read(File file)throws ParserConfigurationException, SAXException, IOException{
        String[] infoXML= new String[5]; //We well know there will be only 5 nodes of information
        //InputStream contentXML= new FileInputStream(file); //Funciona con el IS también
        DocumentBuilderFactory docFactory= DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder= docFactory.newDocumentBuilder();
        doc= docBuilder.parse(file); //contentXML
        doc.getDocumentElement().normalize(); //Coloca cada nodo en el árbol interno
        //infoXML[0]= doc.getElementsByTagName("maestre").item(0).getTextContent();
        infoXML[0]= doc.getElementsByTagName("contramaestre").item(0).getTextContent(); //key AES
        infoXML[1]= doc.getElementsByTagName("hybrid").item(0).getTextContent(); //iv AES
        infoXML[2]= doc.getElementsByTagName("unsigned").item(0).getTextContent(); //Digital signature
        infoXML[3]= doc.getElementsByTagName("private").item(0).getTextContent(); //Public key RSA
        infoXML[4]= doc.getElementsByTagName("information").item(0).getAttributes().getNamedItem("value").getTextContent(); //File extension
        return infoXML;
    }
    
    public void write(){
        try{
            //Se escribe el contenido del XML en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(pathToSave+"TheXML.xml"));
            transformer.transform(source, result);
        }catch(TransformerException ex){
            new Message().showMessage(Message.ERROR, ex.toString());
        }
        
    }
    
    public Element createAutoClosedNode(String name){
        Element el = doc.createElement(name);
        root.appendChild(el);
        return el;
    }
    
    public Element createNode(String name, String content){
        Element el = doc.createElement(name);
        el.setTextContent(content);
        root.appendChild(el);
        return el;
    }
    
    public void addAttribute(Element container, String attrName, String value){
        Attr attr = doc.createAttribute(attrName);
        attr.setValue(value);
        container.setAttributeNode(attr);
    }
    
    //Necessary to specify where files will be located, call before encrypt or decrypt
    public void setDestinationPath(String path){
        StringBuilder sb= new StringBuilder(path);
        if(System.getProperty("os.name").equals("Windows"))
            sb.append("\\");
        else
            sb.append("/");
        pathToSave= sb.toString();
    }
}