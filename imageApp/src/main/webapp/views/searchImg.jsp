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
        <title>Image app</title>
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
        <div class="container mt-4 d-flex flex-row align-items-center justify-content-center">
            <form method="post" action="searchImg" class="d-flex flex-column">
                <div class="form-group mr-2 d-flex flex-row">
                    <select name="parameter" class="form-control" onchange="updateInputType(this.value, 1)">
                        <option value="title">Title</option>
                        <option value="keywords">Keyword</option>
                        <option value="author">Author</option>
                        <option value="creator">Creator</option>
                        <option value="capturingdate">Capturing date</option>
                        <option value="storagedate">Storage date</option>
                    </select>
                    <input type="text" id="searchQuery" name="searchQuery" class="form-control" placeholder="Enter your search query">
                    <input type="date" id="searchDate" name="searchDate" class="form-control" style="display:none;">
                </div>
                
                <div class="form-group">
                    <label for="secondParameter">Second search parameter:</label>
                    <input type="checkbox" id="showSecondSearchContainer" name="secondParameter">
                </div>
                <div class="form-group mr-2 flex-row" style="display:none;" id="secondSearcParameterContainer">
                    <select name="parameter-2" class="form-control" onchange="updateInputType(this.value, 2)">
                        <option value="title">Title</option>
                        <option value="keywords">Keyword</option>
                        <option value="author">Author</option>
                        <option value="creator">Creator</option>
                        <option value="capturingdate">Capturing date</option>
                        <option value="storagedate">Storage date</option>
                    </select>
                    <input type="text" id="searchQuery-2" name="searchQuery-2" class="form-control" placeholder="Enter your search query">
                    <input type="date" id="searchDate-2" name="searchDate-2" class="form-control" style="display:none;">
                </div>
                <button type="submit" class="btn btn-primary">Search</button>
            </form>
        </div>
        
        <%
            List<Image> imgList =  (List<Image>) request.getSession().getAttribute("searchList");
        %>
        <div class="container mt-4 d-flex align-items-center justify-content-center">
            <table class="table table-bordered table-striped">
                <thead class="thead-light">
                    <tr>
                       <th>Image</th>
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
                       <th>Decrypt</th>
                   </tr>
                </thead>
                <%
                    if (imgList != null) {
                        for(Image img: imgList){

                %>
                    <tr>
                        <%
                            if (img.getEncrypted()){
                        %>
                            <td>
                                <div id="encryptedText-<%=img.getId()%>">Image is encrypted</div>
                                <img id="img-<%=img.getId()%>">
                            </td>
                        <%
                            }else {
                        %>
                        <td>
                            <img src="displayImg?id=<%=img.getId()%>" style="max-height: 100px;" >
                        </td>
                        <%
                            }
                        %>
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
                        <%
                            if (img.getEncrypted()){
                        %>
                        <td>
                            <input type="password" id="passwordInput-<%=img.getId()%>" placeholder="Enter password">
                            <button onclick="loadImage(<%=img.getId()%>)" class="btn btn-primary">Submit password</button>
                        </td>
                        <%
                            } else {
                        %>
                        <td></td>
                    </tr>
                    

                <%
                            }
                        }
                    }
                %>
            </table>
        </div>
        
        <script>
            
            document.getElementById('showSecondSearchContainer').addEventListener("change", function() {
               let secondSearchContainer = document.getElementById('secondSearcParameterContainer');
                if (this.checked) {
                    secondSearchContainer.classList.add("d-flex");
                } else {
                    secondSearchContainer.classList.remove("d-flex");
                }
            });
            
            
            function updateInputType(selectedValue, parameter){
                let inputElement, dateInput;
                if (parameter === 2) {
                    inputElement = document.getElementById("searchQuery"+"-2");
                    dateInput = document.getElementById("searchDate"+"-2");
                } else {
                    inputElement = document.getElementById("searchQuery");
                    dateInput = document.getElementById("searchDate");
                }
                
                
                
                if (selectedValue === 'capturingdate' || selectedValue === 'storagedate') {
                    inputElement.style.display = 'none';
                    dateInput.style.display = 'inline';
                } else {
                    inputElement.style.display = 'inline';
                    dateInput.style.display = 'none';
                }
                
            }
            
            
            function loadImage(id) {
                
                const password = document.getElementById("passwordInput-"+id).value;
                const imgText = document.getElementById('encryptedText-'+id);
                
                
                const xhr = new XMLHttpRequest();
                xhr.onreadystatechange = function () {
                    if (xhr.readyState == 4) {
                        if (xhr.status == 200) {
                            const img = document.getElementById('img-'+id);
                            imgText.innerHTML = "";
                            console.log(xhr.response);
                            const urlCreator = window.URL || window.webkitURL;
                            const imageUrl = urlCreator.createObjectURL(xhr.response);
                            img.src = imageUrl;
                            img.height = 100;
                            
                        } else {
                            imgText.innerHTML = "Incorrect password";
                            console.error("Incorrect password");
                        }
                    }
                };
                const reqString = 'displayImg?id=' + id + '&password=' + password;
                xhr.open("GET", reqString);
                xhr.responseType = "blob";
                xhr.send();
                
            }
        </script>
    </body>
</html>
