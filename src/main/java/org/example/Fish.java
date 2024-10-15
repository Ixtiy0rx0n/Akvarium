package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Fish implements Runnable {
    private static final Random random = new Random();
    private static final AtomicInteger fishCount = new AtomicInteger(0);
    private static int totalFishCount = 0; // Baliqlar sonini kuzatish uchun static o'zgaruvchi
    private static int deadFishCount = 0; // o'lgan baliqlar soni
    private final Gender gender;
    private final int id;
    private int lifeSpan;
    private Boolean isNewBorn;


    public Fish() {
        this.id = fishCount.incrementAndGet();
        this.gender = random.nextBoolean() ? Gender.MALE : Gender.FEMALE;
        this.lifeSpan = random.nextInt(60) + 20; // 10 sek ~ 50 sek
        this.isNewBorn = false;
        totalFishCount++;
    }

    public Gender getGender() {
        return gender;
    }

    public Boolean getNewBorn() {
        return isNewBorn;
    }

    public void setNewBorn(Boolean newBorn) {
        isNewBorn = newBorn;
    }

    public int getId() {
        return id;
    }

    public int getLifeSpan() {
        return lifeSpan;
    }

    @Override
    public void run() {
        try {
            if (!isNewBorn) {
                System.out.println(id + " Baliq (" + gender + ") akvariumga qo'yildi. Umri - " + lifeSpan + " sekund");
            }
            while (lifeSpan > 0) {
                Thread.sleep(1000); // 1 sek
                lifeSpan--;
                if (lifeSpan == 0) {
                    System.out.println(id + " baliq (" + gender + ") baliq o'ldi.");
                    synchronized (Fish.class) {
                        deadFishCount++;
                        if (deadFishCount == totalFishCount) {
                            System.out.println("Barcha baliqlar o'ldi akvariumda baliq qolmadi dastur o'chdi.");
                            System.exit(0);
                        }
                    }
                    break;
                }
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void birth(Fish male, Fish female) {
        if (male.lifeSpan > 0 && female.lifeSpan > 0) {
            System.out.println(male.id + " baliq (" + male.gender + ") baliq bilan " + female.id + " (" + female.gender + ") baliq uchrashdi...");
            Fish newFish = new Fish();
            newFish.setNewBorn(true);
            System.out.println(newFish.id + " yangi baliq (" + newFish.gender + ") tug'ildi, Umri - " + newFish.lifeSpan + " sekund");
            new Thread(newFish).start();
        }
    }
}
