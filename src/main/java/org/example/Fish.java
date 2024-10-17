package org.example;


import java.util.Random;

public class Fish extends Thread {
    public Random random = new Random();
    private Akvarium akvarium;
    private Gender gender;
    private int life;
    private int leftLife;// in second
    private int x;
    private int y;
    private Boolean isNewBorn = false;

    public Fish(Gender gender, int life, int x, int y, Boolean isNewBorn, int leftLife) {
        this.gender = gender;
        this.life = life;
        this.leftLife = leftLife;
        this.x = x;
        this.y = y;
        this.isNewBorn = isNewBorn;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i <= life; i++) {
                Thread.sleep(1000);
                move();
                if (getLife() > 3) {
                    akvarium.checkForMating(this);
                }
                leftLife--;
                if (leftLife == 0) {
                    System.out.println("--------------------------------------");
                    akvarium.remove(this);
                }
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Ismi(Thread nomi)=" + getName() +
                " jinsi=" + gender +
                ", umri=" + life +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public void move() {
        int direction = randomNumber(4);
        switch (direction) {
            case 1:
                if (y < Akvarium.height) {
                    y += 1;
                }
                break;
            case 2:
                if (x < Akvarium.width) {
                    x += 1;
                }
                break;
            case 3:
                if (y > 0) {
                    y -= 1;
                }
                break;
            case 4:
                if (x > 0) {
                    x -= 1;
                }
                break;
            default:
                break;
        }
    }

    public void setAquarium(Akvarium akvarium) {
        this.akvarium = akvarium;
    }

    public boolean equals(Fish fish) {
        if (this == fish) return true;
        if (fish == null || getClass() != fish.getClass()) return false;
        return x == fish.x && y == fish.y;

    }

    public Gender getGender() {
        return gender;
    }

    public int randomNumber(int n) {
        return random.nextInt(n) + 1;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLife() {
        return life;
    }

    public Boolean getNewBorn() {
        return isNewBorn;
    }

    public void setNewBorn(Boolean newBorn) {
        isNewBorn = newBorn;
    }
}
