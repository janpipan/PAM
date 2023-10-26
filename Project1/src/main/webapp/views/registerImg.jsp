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
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Upload image</h1>
        <form action="registerImg" method="post" enctpye="multipart/form-data">
            <label for="title">Title: </label>
            <input type="text" id="title" name="title" required><br>
            <label for="description">Description: </label>
            <input type="text" id="description" name="description" required><br>
            <label for="keywords">Keywords: </label>
            <input type="text" id="keywords" name="keywords" required><br>
            <label for="author">Author: </label>
            <input type="text" id="author" name="author" required><br>
            <label for="creator">Creator: </label>
            <input type="text" id="creator" name="creator" required><br>
            <label for="captureDate">Capture date: </label>
            <input type="date" id="captureDate" name="captureDate" required><br>
            <label for="fileName">File name: </label>
            <input type="text" id="fileName" name="fileName" required><br>
            <label for="encrypt">Encrypt: </label>
            <input type="checkbox" id="encrypt" name="encrypt" required><br>
            <input type="file" name="file" required><br>
            <button type="submit">Upload</button>
        </form>
    </body>
</html>
