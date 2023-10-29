<%-- 
    Document   : listImg
    Created on : Oct 28, 2023, 7:14:12 PM
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
        
        <h1>ListImg</h1>
        <div class='container'>
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
                    <th>Edit</th>
                    <th>Delete</th>
                    
                </tr>
                <%
                    ResultSet rs = (ResultSet) request.getSession().getAttribute("resultSet");
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
                        <td>
                            <button>
                                <a href="modifyImg?id=<%=rs.getString("id")%>">Edit</a>
                            </button>
                        </td>
                        <td>
                            <button>
                                <a href="deleteImg?id=<%=rs.getString("id")%>">Delete</a>
                            </button>
                        </td>
                    </tr>
                    

                <%
                    }
                %>
            </table>
        </div>
            
        
    </body>
</html>
