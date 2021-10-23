package com.example.demo1;

import com.github.javafaker.Faker;
import entity.Device;
import entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class main {
    public static void main(String[] args) {
//if the code doesn't work fist time go to Persistence.xml and un comment line 17 & 18.
        // adding 10 demo
        for (int i = 0; i < 5; i++) {
            demo();
        }


    }

    public static void demo() {
        // set up the connection throw
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

//       Random template
        Faker faker = new Faker();
        Random random = new Random();
        Person person = new Person(random.nextLong(),
                faker.name().firstName(), random.nextInt(100));
        Device device = new Device(random.nextLong(), faker.name().firstName()
                , random.nextBoolean(), person);


//        Starting the transaction to the DB
        try {
            transaction.begin();


            entityManager.persist(device);
            transaction.commit();
        } finally {
            if (transaction.isActive()) {

                transaction.rollback();
            }

            entityManager.clear();
            entityManagerFactory.close();
        }

    }
}