package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Akvarium {
    private static final Random random = new Random();
    private final List<Fish> fishes = Collections.synchronizedList(new ArrayList<>());


    public void addFish(Fish fish) {
        fishes.add(fish);
        new Thread(fish).start();
    }

    public void checkForMating() {
        while (true) {
            List<Fish> males = new ArrayList<>();
            List<Fish> females = new ArrayList<>();
            synchronized (fishes) {
                for (Fish fish : fishes) {
                    if (Gender.MALE.equals(fish.getGender())) {
                        males.add(fish);
                    } else {
                        females.add(fish);
                    }
                }
            }

            if (!males.isEmpty() && !females.isEmpty()) {
                Fish male = males.get(random.nextInt(males.size()));
                Fish female = females.get(random.nextInt(females.size()));
                if (male.getLifeSpan() > 0 && female.getLifeSpan() > 0) {
                    Fish.birth(male, female); // Yangi baliq tug'ildi
                }
            }
            try {
                Thread.sleep(5000); // 5 sek
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
