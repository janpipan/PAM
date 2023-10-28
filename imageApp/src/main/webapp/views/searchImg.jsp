<%-- 
    Document   : searchImg
    Created on : Oct 28, 2023, 7:14:25 PM
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
        <h1>Search Image</h1>
        
        <form method="post" action="searchImg">
            <select name="attribute">
                <option value="title">Title</option>
                <option value="author">Author</option>
                <option value="creator">Creator</option>
                <option value="capturingdate">Capturing date</option>
                <option value="storagedate">Storage date</option>
            </select>
            <input type="text" id="searchQuery" name="searchQuery" ><br>
            <button type="submit">Search</button>
        </form>
        
        <%
            ResultSet rs = (ResultSet) request.getSession().getAttribute("resultSet");
            if (rs != null) {
        %>
        <table>
             <tr>
                <th>Title</th>
                <th>Description</th>
                <th>Keywords</th>
                <th>Author</th>
                <th>Creator</th>
                <th>CapturingDate</th>
                <th>StorageDate</th>
                <th>Filename</th>
                <th>Encrypted</th>

            </tr>
            <%
                while (rs.next()) {
            %>
            <tr>
                <td><%=rs.getString("title")%></td>
                <td><%=rs.getString("description")%></td>
                <td><%=rs.getString("keywords")%></td>
                <td><%=rs.getString("author")%></td>
                <td><%=rs.getString("creator")%></td>
                <td><%=rs.getString("capturingdate")%></td>
                <td><%=rs.getString("storagedate")%></td>
                <td><%=rs.getString("filename")%></td>
                <td><%=rs.getString("encrypted")%></td>

            </tr>
            <%
                }
            %>
        </table>
        <%
            }
        %>
    </body>
</html>
