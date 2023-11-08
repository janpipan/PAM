<%-- 
    Document   : searchImg
    Created on : Oct 28, 2023, 7:14:25 PM
    Author     : alumne
--%>
<%@page import="test.entity.Image"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
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
                        <a class="nav-link" href="searchImg">Search Image</a>
                    </li>
                </ul>
            </div>
        </nav>
        <div class="container mt-4">
            <div class="text-center">
                <h1>Search Image</h1>
            </div>
        </div>
        <div class="container mt-4 d-flex align-items-center justify-content-center">
            <form method="post" action="searchImg" class="form-inline">
                <div class="form-group mr-2">
                    <select name="attribute" class="form-control">
                        <option value="title">Title</option>
                        <option value="author">Author</option>
                        <option value="creator">Creator</option>
                        <option value="capturingdate">Capturing date</option>
                        <option value="storagedate">Storage date</option>
                    </select>
                </div>
                <div class="form-group mr-2">
                    <input type="text" id="searchQuery" name="searchQuery" class="form-control" placeholder="Enter your search query">
                </div>
                <button type="submit" class="btn btn-primary">Search</button>
            </form>
        </div>
        
        <%
            List<Image> imgList =  (List<Image>) request.getSession().getAttribute("searchList");
        %>
        <div class="container mt-4">
            <table class="table table-bordered table-striped">
                <thead class="thead-light">
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
                </thead>
                <%
                    if (imgList != null) {
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
                            <button class="btn btn-primary">
                                <a href="modifyImg?id=<%=img.getId()%>" style="text-decoration: none; color: white;">Edit</a>
                            </button>
                        </td>
                        <td>
                            <button class="btn btn-primary">
                                <a href="deleteImg?id=<%=img.getId()%>" style="text-decoration: none; color: white;">Delete</a>
                            </button>
                    </td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
        <%
            }
        %>
    </body>
</html>
