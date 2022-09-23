package com.MuazAbdeen;

public class Main {

    public static void main(String[] args) {

        Cow myCow = new Cow();
        Dog myDog = new Dog();
        Animal myAnimal = myCow;

        makeAnimalSpeak(myCow);
        makeAnimalSpeak(myDog);
        makeAnimalSpeak(myAnimal);

//        myCow.speak();
//        myAnimal.speak();
//
//        myCow.getMilk();
//        // myAnimal.getMilk();  // Animal has no getMilk()
//
//        myAnimal.eat();
//        myCow.eat();
//        myDog.eat();
    }

    private static void makeAnimalSpeak(Animal animal) {
        animal.speak();
    }


}

class Animal {
    public void speak() {
        System.out.println("ha..?");
    }
    public void eat() {
        System.out.println("Yammy");
    }
}

class Cow extends Animal {
    public void speak() {
        System.out.println("moo");
    }

    public void eat() {
        System.out.println("Yum Yum");
    }

    public void getMilk() {
        System.out.println("Milk ....");
    }
}

class Dog extends Animal {
    public void speak() {
        System.out.println("Huf Huf");
    }

    public void getOwner() {
        System.out.println("Owner is ....");
    }
}