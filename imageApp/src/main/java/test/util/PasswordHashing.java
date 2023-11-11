/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.util;

import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;



/**
 *
 * @author alumne
 */
public class PasswordHashing {
    
    
    public static byte[] hashPassword(String password, byte[] salt) throws Exception{
       KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
       SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
       byte[] hash = f.generateSecret(spec).getEncoded();
       return hash;
    }
    
}
