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
