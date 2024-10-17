package org.example;

import java.util.*;


public class Akvarium {
    private static Random random = new Random();
    private List<Fish> fishes = Collections.synchronizedList(new ArrayList<>());
    public static int width = 5;
    public static int height = 5;
    public static int fishLimit = (width + 1) * (height + 1);

    public void start() throws InterruptedException {
        int numberOfFish = random.nextInt(11) + 5;
        System.out.println("Akvariumdagi baliqlar soni: " + numberOfFish);
        for (int i = 0; i < numberOfFish; i++) {
            Fish fish = createFish();
            fish.setAquarium(this);
            fish.start();
            fishes.add(fish);
            System.out.println(fish.getName() + " Baliq (" + fish.getGender() + ") akvariumga qo'yildi. Umri - " + fish.getLife() + " sekund");
        }
        getInfo();
    }
    public synchronized void checkForMating(Fish fish){
        if (fishes.size() > fishLimit) {
            return;
        }
        ListIterator<Fish> iterator = fishes.stream().toList().listIterator();
        while (iterator.hasNext()) {
            Fish f = iterator.next();
            if (f != fish && !f.getGender().equals(fish.getGender()) && f.equals(fish) && random.nextInt(100)<60) {
                if ((f.getX() == fish.getX() && f.getY() == fish.getY())) {
                    try {
                        String s = String.format("%s baliq bilan %s baliq uchrashdi ",
                                f.getName(), fish.getName());
                        Fish newFish = createFish();
                        newFish.setNewBorn(true);
                        newFish.setAquarium(this);
                        newFish.start();
                        fishes.add(newFish);
                        System.out.println(s);
                        System.out.println("Yangi baliq tug'ildi: " + newFish);
                        System.out.println("--------------------------------------");
                        getInfo();
                        Thread.sleep(1000);
                        break;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        if (fishes.isEmpty()) {
            System.out.println("Barcha baliqlar o'ldi. Dastur o'chdi");
            System.exit(0);
        }
    }

    public void remove(Fish fish) throws InterruptedException {
        System.out.println(fish.getName() + " baliq o'ldi!");
        fishes.remove(fish);
        Thread.sleep(1000);
        getInfo();
    }

    public void getInfo() {
        int maleCount = 0;
        int femaleCount = 0;
        for (Fish fish : fishes) {
            if (fish.getGender().equals(Gender.MALE)) {
                maleCount++;
            } else {
                femaleCount++;
            }
        }
        System.out.println("--------------------------------------");
        System.out.println("Akvariumdagi baliqlar soni: " + fishes.size());
        System.out.println("Erkak baliqlar soni: " + maleCount);
        System.out.println("Urg'ochi baliqlar soni: " + femaleCount);
        System.out.println("--------------------------------------");
    }

    public static Fish createFish() {
        int life = random.nextInt(21) + 10;
        int x = randomNumber(Akvarium.width);
        int y = randomNumber(Akvarium.height);
        Fish fish = new Fish(random.nextBoolean() ? Gender.MALE : Gender.FEMALE, life, x, y, false, life);
        return fish;
    }

    public static int randomNumber(int n) {
        return random.nextInt(n) + 1;
    }
}
