package com.example.week8;
import java.util.ArrayList;

public class BottleDispenser {
    // The array for the Bottle-objects
    private ArrayList<ArrayList> bottle_arrays;
    private float money;
    private Bottle lastBuy;

    //singleton
    private static BottleDispenser bd = new BottleDispenser();

    private BottleDispenser() {
        int bottles = 5;
        lastBuy=null;
        this.money = 0;

        // Initialize the array
        this.bottle_arrays = new ArrayList<ArrayList>();
        addBottleList("isoFanta",  bottles);
        addBottleList("pikkuFanta",  bottles);
        addBottleList("isoCola",  bottles);
        addBottleList("pikkuCola",  bottles);
        addBottleList("isoPepsi",  bottles);
        addBottleList("pikkuPepsi",  bottles);
    }

    //singleton
    public static BottleDispenser getInstance(){
        return bd;
    }
    //helper for creating ArrayList
    private void addBottleList(String input, int bottles) {
        ArrayList<Bottle> tempList = new ArrayList<>();
        Bottle temp;
        for(int i =0; i<bottles;i++) {
            switch (input) {
                case "isoFanta":
                    temp = new Bottle("Fanta", "Coca-Cola Co", 645, 1.5, 2.8);
                    break;
                case "pikkuFanta":
                    temp = new Bottle("Fanta", "Coca-Cola Co", 215, 0.5, 1.8);
                    break;
                case "isoCola":
                    temp = new Bottle("Coca-Cola", "Coca-Cola Co", 630, 1.5, 3);
                    break;
                case "pikkuCola":
                    temp = new Bottle("Coca-Cola", "Coca-Cola Co", 210, 0.5, 2);
                    break;
                case "isoPepsi":
                    temp = new Bottle("Pepsi Max", "Pepsi Co", 0, 1.5, 2.5);
                    break;
                case "pikkuPepsi":
                default:
                    temp = new Bottle("Pepsi Max", "Pepsi Co", 0, 0.5, 2.5);
                    break;
            }
            tempList.add(temp);
        }
        this.bottle_arrays.add(tempList);
    }

    public String addMoney(int money) {
        this.money += money;
        return "Klink! "+money+" money was added into the machine!";
    }

    public String buyBottle(int type) {
        System.out.println("Bottle id is "+type);
        System.out.println("Selected bottles in the machine " + this.bottle_arrays.get(type).size());


        if(this.bottle_arrays.get(type).size()>0) {
            Bottle temp =(Bottle) this.bottle_arrays.get(type).get(0);
            if (money >= temp.getPrice()) {
                money -= temp.getPrice();
                this.bottle_arrays.get(type).remove(0);
                lastBuy = temp;
                return "KACHUNK! " + temp.getName() + " appeared from the machine!\n";
            } else {
                return "Not enough money for that bottle\n";
            }
        } else {
            return "This machine doesn't contain that";
        }
    }

    public String returnMoney() {
        float amountReturned = money;
        money = 0;
        return "Klink klink. You got "+amountReturned+". All money gone!\n";
    }

    public String printReceipt(){
        if(lastBuy == null){
            return "Mitään pulloa ei ole vielä ostettu\n";
        } else {
            return lastBuy.toString();
        }
    }
}