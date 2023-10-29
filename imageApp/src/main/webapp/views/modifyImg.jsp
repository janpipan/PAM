<%-- 
    Document   : modifyImg
    Created on : Oct 28, 2023, 7:15:10 PM
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
        <form action="modifyImg" method="post" enctpye="multipart/form-data">
            
            <label for="title">Title: </label>
            <input type="text" id="title" name="title" value="<%=rs.getString("title")%>" required><br>
            <label for="description">Description: </label>
            <input type="text" id="description" name="description" value="<%=rs.getString("description")%>" required><br>
            <label for="keywords">Keywords: </label>
            <input type="text" id="keywords" name="keywords" value="<%=rs.getString("keywords")%>" required><br>
            <label for="author">Author: </label>
            <input type="text" id="author" name="author" value="<%=rs.getString("author")%>" required><br>
            <label for="creator">Creator: </label>
            <input type="text" id="creator" name="creator" value="<%=rs.getString("creator")%>" required><br>
            <label for="captureDate">Capture date: </label>
            <input type="date" id="capturingdata" name="capturingdata" required><br>
            <label for="fileName">File name: </label>
            <input type="text" id="fileName" name="fileName" value="<%=rs.getString("filename")%>" required><br>
            <label for="encrypt">Encrypt: </label>
            <input type="checkbox" id="encrypt" name="encrypt" required><br>
            <input type="file" name="file" required><br>
            <button type="submit">Upload</button>
            
        </form>
    </body>
</html>
