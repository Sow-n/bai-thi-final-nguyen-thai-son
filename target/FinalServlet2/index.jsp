<%-- 
    Document   : index.jsp
    Created on : Dec 23, 2021, 6:06:09 PM
    Author     : ADMIN
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <%
        // lay bien search
        String searchName = request.getParameter("search");
        if (searchName == null) {
            searchName = "";
            }
    %>
    <body>
        <h1>Search customer information !</h1>
        <h4 style="color: red;">${errorMessage}</h4>
        <form action="CustomerControllerServlet" method="GET">
        Name: <input type="search" name="search" value="<%=searchName %>"/> <button>Search</button>
        </form>
        <br/>
        <table border="1">
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Age</th>
                <th>Address</th>
            </tr>
            <c:forEach var="customer" items="${customers}">
                <tr>
                    <td>${customer.id}</td>
                    <td>${customer.name}</td>
                    <td>
                        ${customer.age}
                    </td>
                    <td>
                        ${customer.address}
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
