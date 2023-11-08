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
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#">Menu</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="registerImg">Register Image</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="listImg">List Images</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="modifyImg">Modify Images</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="deleteImg">Delete Image</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="searchImg">Search Image</a>
                    </li>
                </ul>
            </div>
        </nav>
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
