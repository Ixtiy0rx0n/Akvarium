package org.example;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        Akvarium aquarium = new Akvarium();
        System.out.println("Baliqlar soni qaysi songacha tanlab olinsin? e.g: 50");
        final int maxN = scanner.nextInt();

        int N = random.nextInt((maxN)+1); // Erkak baliqlarning tasodifiy soni
        int M = random.nextInt((maxN)+1); // Urg'ochi baliqlarning tasodifiy soni

        for (int i = 0; i < N; i++) {
            aquarium.addFish(new Fish()); // Erkak baliqlarni yaratish
        }

        for (int i = 0; i < M; i++) {
            aquarium.addFish(new Fish()); // Urg'ochi baliqlar yaratish
        }

        // Baliqlarni juftlashtirishni tekshirish
        new Thread(() -> aquarium.checkForMating()).start();
    }

}