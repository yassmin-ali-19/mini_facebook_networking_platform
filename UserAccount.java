/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.useraccount;

/**
 *
 * @author Ahmed Kamel
 */
public class UserAccount {

    public static void main(String[] args) {
        User user = new User(
            "12345", 
            "user@example.com", 
            "username", 
            "securePassword123", 
            "2000-01-01", 
            "offline"
        );

        System.out.println("Email: " + user.getEmail());
        System.out.println("Hashed Password: " + user.getPassword());
    }}