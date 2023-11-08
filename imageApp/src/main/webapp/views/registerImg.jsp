<%-- 
    Document   : registerImg
    Created on : Oct 6, 2023, 1:00:56 PM
    Author     : alumne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Upload image</h1>
        <div class="container d-flex align-items-center justify-content-center" style="height: 80vh;">
            <form action="registerImg" method="post" enctype="multipart/form-data" id="registerImg">
                <div class="form-group">
                    <label for="title">Title: </label>
                    <input type="text" id="title" name="title" class="form-control">
                </div>
                <div class="form-group">
                    <label for="description">Description: </label>
                    <input type="text" id="description" name="description" class="form-control">
                </div>
                <div class="form-group">
                    <label for="keywords">Keywords: </label>
                    <input type="text" id="keywords" name="keywords" class="form-control">
                </div>
                <div class="form-group">
                    <label for="author">Author: </label>
                    <input type="text" id="author" name="author" class="form-control">
                </div>
                <div class="form-group">
                    <label for="creator">Creator: </label>
                    <input type="text" id="creator" name="creator" class="form-control">
                </div>
                <div class="form-group">
                    <label for="capturingdate">Capture date: </label>
                    <input type="date" id="capturingdate" name="capturingdate" class="form-control">
                </div>
                <div class="form-group">
                    <label for="encrypt">Encrypt: </label>
                    <input type="checkbox" id="encrypt" name="encrypt" >
                </div>
                <div class="form-group">

                </div>
                <div class="form-group"id="encryptInput" style="display:none;">
                    <label for="encryptPassword">Encryption password</label>
                    <input type="password" id="encryptPassword" name="encryptpassword" class="form-control">
                </div>

                <input type="file" name="file" ><br>
                <button type="submit" class="btn btn-primary">Upload</button>
            </form>
        </div>
        
        <script>
            /*
            const checkbox = document.getElementById('encrypt');
            const form = document.getElementById('registerImg');
            
            function addTextInput() {
                const input = document.createElement('input');
                input.type = 'text';
                input.name = 'dynamicInput';
                input.placeholder = 'Password';
                form.appendChild(input);
            }
            
            checkbox.addEventListener('change', function() {
               if(checkbox.checked) {
                   addTextInput();
               } else {
                   const dynamicInput = form.querySelector('input[name="dynamicInput"]');
                   if (dynamicInput){
                       form.removeChild(dynamicInput);
                   }
               }
            });*/
    
            const encryptCheckbox = document.getElementById('encrypt');
            const encryptInput = document.getElementById('encryptInput');
            
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
