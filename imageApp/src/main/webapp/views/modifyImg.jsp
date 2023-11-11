<%-- 
    Document   : modifyImg
    Created on : Oct 28, 2023, 7:15:10 PM
    Author     : alumne
--%>
<%@page import="test.entity.Image"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.Format"%>
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
        <%
            List<Image> imgList =  (List<Image>) request.getSession().getAttribute("imgList");
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            
            for(Image img: imgList){
            System.out.println(img.getEncrypted());
        %>
        <div class="container d-flex align-items-center justify-content-center" style="height: 80vh;">
            
            <form action="modifyImg" method="post" enctpye="multipart/form-data">
                
                <input type="hidden" name="id" value="<%=img.getId()%>"/>
                 <div class="form-group">
                    <label for="title">Title: </label>
                    <input type="text" id="title" name="title" class="form-control" value="<%=img.getTitle()%>" required>
                </div>
                <div class="form-group">
                    <label for="description">Description: </label>
                    <input type="text" id="description" name="description" class="form-control" value="<%=img.getDescription()%>" required>
                </div>
                <div class="form-group">
                    <label for="keywords">Keywords: </label>
                    <input type="text" id="keywords" name="keywords" class="form-control" value="<%=img.getKeywords()%>" required>
                </div>
                <div class="form-group">
                    <label for="author">Author: </label>
                    <input type="text" id="author" name="author" class="form-control" value="<%=img.getAuthor()%>" required>
                </div>
                <div class="form-group">
                    <label for="creator">Creator: </label>
                    <input type="text" id="creator" name="creator" class="form-control" value="<%=img.getCreator()%>" required>
                </div>
                <div class="form-group">
                    <label for="capturingdate">Capture date: </label>
                    <input type="date" id="capturingdate" name="capturingdate" class="form-control" value="<%=formatter.format(img.getCapturingdate())%>" required>
                </div>
                <div class="form-group">
                    <label for="encrypt">Encrypt: </label>
                    <%
                        if(img.getEncrypted()) {
                    %>
                    <input type="checkbox" id="encrypt" name="encrypt" checked>
                    <%
                        }else {
                    %>
                    <input type="checkbox" id="encrypt" name="encrypt" >
                    <%
                        }
                    %>
                </div>
                <div class="form-group">

                </div>
                <div class="form-group"id="encryptInput" style="display:none;">
                    <label for="encryptPassword">Old encryption password:</label>
                    <input type="password" id="encryptPasswordOld" name="encryptPasswordOld" class="form-control">
                    <label for="encryptPassword">New encryption password:</label>
                    <input type="password" id="encryptPasswordNew" name="encryptPasswordNew" class="form-control">
                </div>

                <input type="file" name="file" ><br>
                <button type="submit" class="btn btn-primary">Upload</button>

            </form>
        </div>
            
        <%
            }
        %>    
        
        
        <script>
            
    
            const encryptCheckbox = document.getElementById('encrypt');
            const encryptInput = document.getElementById('encryptInput');
            
            if (encryptCheckbox.checked) {
                encryptInput.style.display = 'block';
            }
            
            encryptCheckbox.addEventListener('change', function() {
                if (encryptCheckbox.checked) {
                    encryptInput.style.display = 'block';
                } else {
                    encryptInput.style.display = 'none';
                }
            });
            
        </script>
    </body>
    
    
   
</html>
