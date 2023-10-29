<%-- 
    Document   : deleteImg
    Created on : Oct 28, 2023, 7:15:21 PM
    Author     : alumne
--%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            ResultSet rs = (ResultSet) request.getSession().getAttribute("resultSet");
            rs.next();
        %>
        
        <p><%=rs.getString("title")%></p>
        <p><%=rs.getString("description")%></p>
        <p><%=rs.getString("keywords")%></p>
        <p><%=rs.getString("author")%></p>
        <p><%=rs.getString("creator")%></p>
        <p><%=rs.getString("capturingdate")%></p>
        <p><%=rs.getString("storagedate")%></p>
        <p><%=rs.getString("filename")%></p>
        <p><%=rs.getString("encrypted")%></p>
    </body>
</html>
