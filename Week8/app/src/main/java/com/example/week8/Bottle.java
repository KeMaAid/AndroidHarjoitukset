package com.example.week8;

public class Bottle {
    private String name;
    private String manufacturer;
    private double total_energy;
    private double size;
    private double price;

    public Bottle(String name, String manuf, float totE, double size, double price){
        this.name =name;
        this.manufacturer = manuf;
        this.total_energy = totE;
        this.size = size;
        this.price = price;
    }

    public String getName(){
        return name;
    }

    public String getManufacturer(){
        return manufacturer;
    }

    public double getEnergy(){
        return total_energy;
    }
    public double getSize(){
        return size;
    }

    public double getPrice(){
        return price;
    }
    @Override
    public String toString(){
        String payload = "";
        payload += "Pullon nimi: "+this.getName()+"\n";
        payload += "Pullon valmistaja: "+this.getManufacturer()+"\n";
        payload += "Pullon kalorit: "+this.getEnergy()+"\n";
        payload += "Pullon koko: "+this.getSize()+"\n";
        payload += "Pullon hinta: "+this.getPrice()+"\n";
        return payload;
    }
}