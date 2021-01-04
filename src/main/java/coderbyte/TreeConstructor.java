package coderbyte;

import java.util.Scanner;

public class TreeConstructor {

    public static String TreeConstructor(String... strArr) {
        // code goes here
        return strArr[0];
    }

    public static void main (String[] args) {
        // keep this function call here
        Scanner s = new Scanner(System.in);
        System.out.print(TreeConstructor(s.nextLine()));
    }
}
