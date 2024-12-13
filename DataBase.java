/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facebookcheck;

import java.util.List;

/**
 *
 * @author Yassmin
 */
public interface DataBase<T> {
    public void save(List<T> obj);
    public List<T> load();
    
}
