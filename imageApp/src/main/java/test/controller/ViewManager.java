/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author alumne
 */
public class ViewManager {
    public static void nextView(HttpServletRequest req, HttpServletResponse res, String jsp){
        try {
            RequestDispatcher dispatcher = req.getRequestDispatcher(jsp);
            if (dispatcher != null) {
                dispatcher.forward(req,res);
            }
        } catch (Exception e){
            
        }
    }
}
