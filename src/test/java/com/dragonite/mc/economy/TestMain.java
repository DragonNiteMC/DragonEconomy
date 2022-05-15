package com.dragonite.mc.economy;

import java.util.Random;

public class TestMain {

    private static final Random random = new Random();
    private static final float MIN = 0.0001f;
    private static final float MAX = 0.9999f;

    private static final float CONTROL = 0.1f;


    public static void main(String[] args) {
        var o = 0.001f;
        int testTimes = 30;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < testTimes; i++) {
            //System.out.println("current float: " + o);
            System.out.printf("current: %.2f gems : 1 wrld%n", (1 / o));
            var next = refreshFloat(o);
            //System.out.println("new float: " + next);
            System.out.printf("new: %.2f gems : 1 wrld%n", (1 / next));
            //System.out.println("diff: " + Math.abs((next - o)));
            System.out.printf("diff: %.2f%n", Math.abs((1 / o) - (1 / next)));
            o = next;

            var gems = (int)(1 / o);

            if (gems > max) {
                max = gems;
            } else if (gems < min) {
                min = gems;
            }
        }

        System.out.printf("max: %d%n", max);
        System.out.printf("min: %d%n", min);
    }

    private static float refreshFloat(float original) {
        var next = randomFloat();
        System.out.printf("random next: %.5f compare to %.5f %n", next * CONTROL, original);
        if (random.nextBoolean()) {
            next = original * (1 + (next * CONTROL));
        } else {
            next = original * (1 - (next * CONTROL));
        }
        return Math.min(MAX, Math.max(MIN, next));
    }

    private static float randomFloat(){
        return random.nextFloat() * (MAX - MIN) + MIN;
    }
}
