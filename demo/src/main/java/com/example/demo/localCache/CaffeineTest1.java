package com.example.demo.localCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;


/**
 * @author xiaoxu123
 */
public class CaffeineTest1 {

    public static void main(String[] args) throws InterruptedException {

        Caffeine<String, Student> caffeine = Caffeine.newBuilder()
                .maximumWeight(100)
                .weigher((String key, Student value)-> value.getScore());

        Cache<String, Student> cache = caffeine.build();

        cache.put("one", new Student(40, "one"));
        cache.put("two", new Student(60, "two"));
        cache.put("three", new Student(50, "three"));

        Thread.sleep(10);

        System.out.println(cache.estimatedSize());

        System.out.println(cache.getIfPresent("two"));
    }
}

class Student{
    Integer score;

    String name;

    public
    Student(Integer score, String name) {
        this.score = score;
        this.name = name;
    }

    public
    Integer getScore() {
        return score;
    }

    public
    void setScore(Integer score) {
        this.score = score;
    }

    public
    String getName() {
        return name;
    }

    public
    void setName(String name) {
        this.name = name;
    }
}

