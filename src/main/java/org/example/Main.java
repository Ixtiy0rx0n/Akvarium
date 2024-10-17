package org.example;


public class Main {
    public static void main(String[] args) {
        Akvarium aquarium = new Akvarium();
        try {
            aquarium.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



}