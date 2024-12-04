
package com.mycompany.useraccount;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class passwordHasher {

  // Static method for hashing passwords
    public static String hasher( String pass) throws NoSuchAlgorithmException{
   
   MessageDigest mD = MessageDigest.getInstance("SHA-256");
    
   mD.update(pass.getBytes());
   byte[] result= mD.digest();
   
    StringBuilder sb = new StringBuilder();
    // Convert byte array to hexadecimal string
    for(byte b: result){
    
    sb.append(String.format("%02x", b));
    }
    
    return sb.toString();
    }
} 
