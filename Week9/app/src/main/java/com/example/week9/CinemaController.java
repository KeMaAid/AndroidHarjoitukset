package com.example.week9;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class CinemaController {
    private final ArrayList<Cinema> cinemas;
    private static final CinemaController cc = new CinemaController();

    private CinemaController(){
        cinemas = new ArrayList<>();
        addTheaters();
    }

    public static CinemaController getInstance(){
        return cc;
    }

    private void addTheaters(){
        final String sUrl = "https://www.finnkino.fi/xml/TheatreAreas/";
        Document doc = null;

        try {
            DocumentBuilder dbuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = dbuilder.parse(sUrl);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } finally {
            System.out.println("##########Read Theaters###########");
        }

        doc.getDocumentElement().normalize();

        NodeList nList = doc.getDocumentElement().getElementsByTagName("TheatreArea");
        for(int i=0;i<nList.getLength();i++){
            Node node =nList.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                int id = Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent());
                String name = element.getElementsByTagName("Name").item(0).getTextContent();
                this.cinemas.add(new Cinema(id, name));
            }
        }
    }

    public String[] cinemasStringArray(){
        String[] payload = new String[this.cinemas.size()];
        for(int i =0; i<this.cinemas.size(); i++){
            payload[i]=this.cinemas.get(i).getName();
        }
        return payload;
    }

    public String[] getMovies(int index, String searchword, LocalDate date, LocalTime open, LocalTime close){
        final String sUrl = "https://www.finnkino.fi/xml/Schedule/?area="+cinemas.get(index).getId()+"&dt="+date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        Document doc = null;
        ArrayList<String> movies = new ArrayList<>();

        try {
            DocumentBuilder dbuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = dbuilder.parse(sUrl);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } finally {
            System.out.println("##########Read Movies###########");
        }

        doc.getDocumentElement().normalize();

        NodeList nList = doc.getDocumentElement().getElementsByTagName("Shows");
        for(int i=0;i<nList.getLength();i++) {
            Node node = nList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                for (int j = 0; j < element.getElementsByTagName("Show").getLength(); j++) {
                    Node movieNode = element.getElementsByTagName("Show").item(j);
                    if (movieNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element movie = (Element) movieNode;

                        if (open == null && close == null) {
                            movies.add(movie.getElementsByTagName("Title").item(0).getTextContent());
                        } else if (close == null) {
                            LocalTime startTime = stripper(movie.getElementsByTagName("dttmShowStart").item(0).getTextContent());
                            if (open.compareTo(startTime) <= 0) {
                                movies.add(movie.getElementsByTagName("Title").item(0).getTextContent());
                            }
                        } else {
                            LocalTime startTime = stripper(movie.getElementsByTagName("dttmShowStart").item(0).getTextContent());
                            if (open.compareTo(startTime) <= 0 && close.compareTo(startTime) >= 0) {
                                movies.add(movie.getElementsByTagName("Title").item(0).getTextContent());
                            }
                        }
                    }
                }
            }
        }
        if(!searchword.equals("")){
            movies = (ArrayList<String>) movies.stream().filter(title -> title.contains(searchword)).collect(Collectors.toList());
        }

        return movies.toArray(new String[0]);
    }
    //strips hours and mins from formatted input
    private LocalTime stripper(String input){
        int hours = Integer.parseInt(input.substring(input.indexOf('T')+1, input.indexOf(':')));
        int minutes = Integer.parseInt(input.substring(input.indexOf(':')+1, input.indexOf(':', input.indexOf(':')+1)));
        return LocalTime.of(hours, minutes);
    }

}
