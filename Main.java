package com.company;

import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Input a number: ");
        BigInteger num = in.nextBigInteger();
        in.close();
        System.out.print("We are starting encryption\n");
        keys key;
        key = generateKey(num);
        key = encryption(num, key);
        key = decryption(key);
        key = proverkaencryption(key);
        key = proverkadecryption(key);
    }

    private static keys encryption(BigInteger num, keys key) {
        BigInteger res = BigInteger.valueOf(num.intValue()).pow(key.getE().intValue()).mod(key.getN());
        System.out.print("Encrypted message:  ");
        System.out.println(res);
        key.setM(res);
        return key;
    }

    private static keys decryption (keys key){
        BigInteger res = (key.getM()).pow(key.getD().intValue()).mod(key.getN());
        System.out.print("Decrypted message:  ");
        System.out.println(res);
        key.setM(res);
        return key;
    }

    private static keys proverkaencryption(keys key) {
        BigInteger res = BigInteger.valueOf(key.getM().intValue()).pow(key.getD().intValue()).mod(key.getN());
        System.out.print("Signature:  ");
        System.out.println(res);
        key.setS(res);
        return key;
    }

    private static keys proverkadecryption(keys key) {
        BigInteger res = BigInteger.valueOf(key.getS().intValue()).pow(key.getE().intValue()).mod(key.getN());
        BigInteger temp = BigInteger.valueOf(key.getS().intValue()).pow(key.getE().intValue());
        if (res.equals(key.getM())) {
            System.out.print("correct signature:  ");
        }
        System.out.println(res);
        key.setM(res);
        return key;
    }

    private static keys generateKey(BigInteger num){
        BigInteger e, d, k;
        int p,q, n;
        do {
            p = rand(1050);
            do {
                q = rand(1050);
            } while (p == q);
        }while (p * q < num.intValue());
        k = BigInteger.valueOf((long) (p - 1) * (q-1));
        n = (p * q);
        do {
            e = BigInteger.valueOf(rand(n));
        }while (evklid(e.intValue(), k.intValue()) != 1);
        do {
            d = BigInteger.valueOf( (int) (Math.random() * (n) + 1));
        }while((e.intValue()*d.intValue())%k.intValue() != 1 || (e.equals(d)));
        keys key = new keys();
        key.setE(e);
        key.setN(BigInteger.valueOf(n));
        key.setD(d);
        return key;
    }

    private static int evklid(int a, int b){
        int c;
        do{
            c = a % b;
            a = b;
            b = c;
        }while(c != 0);
        return a;
    }

    private static int rand(int n){
        int e, k;
        boolean isPrime;
        do {
            isPrime = true;
            e = (int) ((Math.random() * ( n - 1)) + 2);
            for (int i=2; i <= e/2; i++) {
                k = e % i;
                if (k == 0) {
                    isPrime = false;
                }
            }
        }while (!isPrime);
        return e;
    }
}

class keys {
    BigInteger e, d;
    BigInteger m, n, s;

    public BigInteger getE() {
        return e;
    }
    public BigInteger getM() {
        return m;
    }
    public BigInteger getN() {
        return n;
    }
    public BigInteger getD() {
        return d;
    }
    public BigInteger getS() {
        return d;
    }
    public void setS(BigInteger s) {
        this.s = s;
    }
    public void setD(BigInteger d) {
        this.d = d;
    }
    public void setE(BigInteger e) {
        this.e = e;
    }
    public void setM(BigInteger m) {
        this.m = m;
    }
    public void setN(BigInteger n) {
        this.n = n;
    }
}
//(Math.random() * ((max - min) + 1)) + min
//(Math.random() * ((n - 2) + 1)) + 2