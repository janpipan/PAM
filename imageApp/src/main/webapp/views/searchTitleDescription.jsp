<%-- 
    Document   : searchTitleDescription
    Created on : Dec 1, 2023, 8:27:18 PM
    Author     : alumne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
    </head>
    <body>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <title>Image app</title>
    </head>
    <body>
        
        <div class="container mt-4">
            <div class="text-center">
                <h1>Search Image</h1>
            </div>
        </div>
        <div class="container mt-4 d-flex flex-row align-items-center justify-content-center">
            <form method="GET" action="/imageApp/resources/jakartaee9/search" class="d-flex flex-column" id="searchForm" onsubmit="getFunction()">
                <div class="form-group mr-2 d-flex flex-row">
                    <input type="text" id="searchQuery" name="title" class="form-control" placeholder="Enter title of image:">
                    <input type="text" id="searchQuery" name="description" class="form-control" placeholder="Enter description of image:">
                </div>
                
                
                <button type="submit" class="btn btn-primary">Search</button>
            </form>
        </div>
</html>
