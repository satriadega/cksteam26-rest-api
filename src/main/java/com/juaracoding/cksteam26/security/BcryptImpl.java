package com.juaracoding.cksteam26.security;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 21/07/25 19.51
@Last Modified 21/07/25 19.51
Version 1.0
*/

import java.util.function.Function;

public class BcryptImpl {
    private static final BcryptCustom bcrypt = new BcryptCustom(11);

    public static String hash(String password) {
        return bcrypt.hash(password);
    }

    public static boolean verifyAndUpdateHash(String password, String hash, Function<String, Boolean> updateFunc) {
        return bcrypt.verifyAndUpdateHash(password, hash, updateFunc);
    }

    public static boolean verifyHash(String password, String hash) {
        return bcrypt.verifyHash(password, hash);
    }
}
