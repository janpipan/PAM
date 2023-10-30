<%-- 
    Document   : listImg
    Created on : Oct 28, 2023, 7:14:12 PM
    Author     : alumne
--%>
<%@page import="test.entity.Image"%>
<%@page import="java.util.List"%>
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
                    List<Image> imgList =  (List<Image>) request.getSession().getAttribute("imgList");
                    for(Image img: imgList){

                %>
                    <tr>
                        <td><%=img.getTitle()%></td>
                        <td><%=img.getDescription()%></td>
                        <td><%=img.getKeywords()%></td>
                        <td><%=img.getAuthor()%></td>
                        <td><%=img.getCreator()%></td>
                        <td><%=img.getCapturingdate()%></td>
                        <td><%=img.getStoragedate()%></td>
                        <td><%=img.getFilename()%></td>
                        <td><%=img.getEncrypted()%></td>
                        <td>
                            <button>
                                <a href="modifyImg?id=<%=img.getId()%>">Edit</a>
                            </button>
                        </td>
                        <td>
                            <button>
                                <a href="deleteImg?id=<%=img.getId()%>">Delete</a>
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
