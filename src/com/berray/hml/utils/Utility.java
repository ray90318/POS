package com.berray.hml.utils;

import java.util.Scanner;

public class Utility {
    static String key;
    static int key1;
    static Scanner scanner = new Scanner(System.in);
    public static String readString(){
        key = scanner.next();
        return key;
    }
    public static int readInt(){
        key1 = scanner.nextInt();
        return key1;
    }
}
