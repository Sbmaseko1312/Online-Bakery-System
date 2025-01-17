/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package za.ac.bakery.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.ac.Exception.UserExist;

import za.ac.bakery.model.Address;
import za.ac.bakery.model.Customer;

import za.ac.bakery.model.Person;
import za.ac.bakery.serviceImpl.CustomerServiceImpl;

/**
 *
 * @author Train
 */
public class CustomerController extends HttpServlet {

    private String title;
    private String id;
    private String name;
    private String surname;
    private String contactno;
    private String email;
    private String password;
    private String action;
    private Customer customer;

    private Person existingCustomer;

    private CustomerServiceImpl customerservice;
    private String path;
    private HttpSession session;
    private String tempEmail;
    private List<Person> customers;

    private String message;
    private String realpath;
    private String street_name;
    private String suburb;
    private String postalcode;
    private Address adress;

    public CustomerController() {
        customerservice = new CustomerServiceImpl("jdbc:mysql://localhost:3306/bakery-systemdb", "root", "root");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        session = request.getSession(true);

        action = request.getParameter("act");
        action = action.toLowerCase();

        switch (action) {

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UserExist {

        session = request.getSession(true);

        action = request.getParameter("act");

        switch (action) {
            case "signup":

                title = request.getParameter("title");
                id = request.getParameter("idPassport");
                name = request.getParameter("name");
                surname = request.getParameter("surname");
                contactno = request.getParameter("contactNo");
                email = request.getParameter("email");
                password = request.getParameter("password");

                street_name = request.getParameter("street_name");
                suburb = request.getParameter("suburb");
                postalcode = request.getParameter("postal_code");

                existingCustomer = customerservice.getCustomer(email);

                System.out.println("Customer Email : " + existingCustomer.getEmail());

                if (existingCustomer != null && existingCustomer.getId_Number().length() > 2 && existingCustomer.getEmail().length() > 2) {

                    message = "User Exist! Sign in.";
                    path = "unsuccesful.jsp";
                    realpath = "sign_in.jsp";

                } else {

                    customer = new Customer(id, name, surname, title, email, contactno, password);
                    adress = new Address(street_name, suburb, postalcode);

                    System.out.println("Customer : " + customer.getEmail());

                    customerservice.createCustomer(customer);
                    customerservice.addAddress(adress, customer);

                    message = "Account Succesfully Created!";
                    String outcome = customerservice.RegisteringEmailOutcome(customer);

                    path = "sucessful.jsp";
                    realpath = "sign_in.jsp";
                }

                session.setAttribute("path", realpath);
                session.setAttribute("message", message);

                request.getRequestDispatcher(path).forward(request, response);
                break;
        }

    }

}
