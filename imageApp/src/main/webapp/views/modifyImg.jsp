<%-- 
    Document   : modifyImg
    Created on : Oct 28, 2023, 7:15:10 PM
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
        <%
            List<Image> imgList =  (List<Image>) request.getSession().getAttribute("imgList");
            for(Image img: imgList){
        %>
        <form action="modifyImg" method="post" enctpye="multipart/form-data">
            <input type="hidden" name="id" value="<%=img.getId()%>"/>
            <label for="title">Title: </label>
            <input type="text" id="title" name="title" value="<%=img.getTitle()%>" required><br>
            <label for="description">Description: </label>
            <input type="text" id="description" name="description" value="<%=img.getDescription()%>" required><br>
            <label for="keywords">Keywords: </label>
            <input type="text" id="keywords" name="keywords" value="<%=img.getKeywords()%>" required><br>
            <label for="author">Author: </label>
            <input type="text" id="author" name="author" value="<%=img.getAuthor()%>" required><br>
            <label for="creator">Creator: </label>
            <input type="text" id="creator" name="creator" value="<%=img.getCreator()%>" required><br>
            <label for="capturingdate">Capture date: </label>
            <input type="date" id="capturingdate" name="capturingdate" required><br>
            <!--
            <label for="fileName">File name: </label>
            <input type="text" id="fileName" name="fileName" value="<%=img.getFilename()%>" required><br>
            -->
            <label for="encrypt">Encrypt: </label>
            <input type="checkbox" id="encrypt" name="encrypt" ><br>
            <input type="file" name="file" ><br>
            <button type="submit">Upload</button>
            
        </form>
            
        <%
            }
        %>    
    </body>
</html>
