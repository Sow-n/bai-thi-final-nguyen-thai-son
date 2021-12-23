/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import jdbc.DatabaseConnectionPoolManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Customer;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CustomerControllerServlet", urlPatterns = {"/CustomerControllerServlet"})
public class CustomerControllerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            List<Customer> customers = new ArrayList<>();
            String searchName = request.getParameter("search");
            //
            int check = 1;
            if (searchName == null ) {
                check = 0;
            }
            
            if ((searchName == null || "".equals(searchName)) && check == 1) {
                searchName = "";
                request.setAttribute("errorMessage", "Không được để trống!");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            if(check == 0)
                searchName = "";
            
            myConn = DatabaseConnectionPoolManager.getConnection();

            myStmt = myConn.createStatement();

            String sql = "SELECT * FROM customer WHERE name like '%" + searchName + "%'";

            myRs = myStmt.executeQuery(sql);

            while (myRs.next()) {
                int id = myRs.getInt("id");
                String name = myRs.getString("name");
                int age = myRs.getInt("age");
                String address = myRs.getString("address");
                customers.add(new Customer(id, name, age, address));
            }
            // cho list customer vao session
            request.setAttribute("customers", customers);
            request.getRequestDispatcher("./index.jsp").forward(request, response);
            
            // exception
        } catch (Exception e) {
            System.out.println("error");
        } finally {

            // close all
            if (myRs != null) try {
                myRs.close();
            } catch (SQLException e) {
                // ignore
            } else {
                out.println("error");
            }

            if (myStmt != null) try {
                myStmt.close();
            } catch (SQLException e) {
                // ignore
            } else {
                out.println("error");
            }

            if (myConn != null) try {
                myConn.close();
            } catch (SQLException e) {
                // ignore
            } else {
                out.println("error");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
