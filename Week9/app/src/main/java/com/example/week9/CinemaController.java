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
import java.util.HashMap;
import java.util.Map;
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

    public String getCinemaName(int ID){
        for(Cinema c : cinemas){
            if(c.getId()==ID){
                return c.getName();
            }
        }
        return null;
    }
    public int getCinemaID(int placement){
        return this.cinemas.get(placement).getId();
    }

    public Movie[] getMovies(int ID, String searchword, LocalDate date, LocalTime open, LocalTime close){
        final String sUrl = "https://www.finnkino.fi/xml/Schedule/?area="+ID+"&dt="+date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        Document doc = null;
        ArrayList<Movie> movies = new ArrayList<>();

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
                        movies.add(new Movie(movie.getElementsByTagName("Title").item(0).getTextContent(),
                                Integer.parseInt(movie.getElementsByTagName("ID").item(0).getTextContent()),
                                Integer.parseInt(movie.getElementsByTagName("TheatreID").item(0).getTextContent()),
                                stripper(movie.getElementsByTagName("dttmShowStart").item(0).getTextContent())));
                    }
                }
            }
        }

        //filtering
        if(!searchword.equals("")){
            movies = (ArrayList<Movie>) movies.stream().filter(movie -> movie.getTitle().contains(searchword)).collect(Collectors.toList());
        }

        if (close != null && open != null) {
            movies = (ArrayList<Movie>) movies.stream().filter(movie -> open.compareTo(movie.getStartTime())<=0).filter(movie -> close.compareTo(movie.getStartTime())>=0).collect(Collectors.toList());
        } else if (close != null){
            movies = (ArrayList<Movie>) movies.stream().filter(movie -> open.compareTo(movie.getStartTime())<=0).collect(Collectors.toList());
        }

        return movies.toArray(new Movie[0]);
    }

    public Map<String, ArrayList<Movie>> findMovies(String searchword, LocalDate date, LocalTime open, LocalTime close){
        int[] placements = {1039,1038,1045,1031,1032,1033,1013,1015,1016,1017,1018,1019,1034,1035,1022, 1041};
        Map<String, ArrayList<Movie>> movies = new HashMap<String, ArrayList<Movie>>();

        for(int placement: placements){
            Movie[] movieArray = getMovies(placement, searchword, date, open, close);
            for(Movie movie: movieArray){
                movies.putIfAbsent(movie.getTitle(), new ArrayList<>());
                movie.setLocationID(placement);
                movies.get(movie.getTitle()).add(movie);
            }
        }

        return movies;
    }
    //strips hours and mins from formatted input
    private LocalTime stripper(String input){
        int hours = Integer.parseInt(input.substring(input.indexOf('T')+1, input.indexOf(':')));
        int minutes = Integer.parseInt(input.substring(input.indexOf(':')+1, input.indexOf(':', input.indexOf(':')+1)));
        return LocalTime.of(hours, minutes);
    }

}
