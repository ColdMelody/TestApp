package com.demos.testapp.utils;

import java.math.BigInteger;

/**
 * Created by Bill on 2017/1/11.
 * Email androidBaoCP@163.com
 */

public class Fibonacci {
    private BigInteger num1, num2;

    public Fibonacci() {
    }

    public String getFibonacci(int n) {
        BigInteger fibonacci = getFib(n);
        String s = fibonacci.toString();
        if (s.length() >= 10) {
            return s.substring(0, 1) + "." + s.substring(1, 10) + "e" + (s.length() - 1);
        } else {
            return s;
        }
    }


    private BigInteger getBigFib(int n) {
        if (n < 2) {
            return null;
        }
        int i = n * n - (n - 1) * (n - 1);
        return getF(i + 1).multiply(num2).add(getF(i).multiply(num1));

    }

    private BigInteger getFib(int n) {
        BigInteger pre = new BigInteger("-1");
        BigInteger result = new BigInteger("1");
        int i;
        BigInteger sum;
        for (i = 0; i <= n; i++) {
            sum = result.add(pre);
            pre = result;
            num1 = result;
            result = sum;
            num2 = sum;
        }
        return result;
    }

    private BigInteger getF(int n) {
        BigInteger pre = new BigInteger("-1");
        BigInteger result = new BigInteger("1");
        int i;
        BigInteger sum;
        for (i = 0; i <= n; i++) {
            sum = result.add(pre);
            pre = result;
            result = sum;
        }
        return result;
    }
}
