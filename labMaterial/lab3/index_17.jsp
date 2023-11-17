<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>File Upload</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <form method="POST" action="resources/jakartaee9/register" >
            <table> 
                <tr>
                    <th colspan="2">Fill Database fields</th>
                </tr>
                <tr>                    
                    <td>Title</td>                
                    <td><input type="text" name="title" id="title" /></td>           
                </tr>
                <tr>
                    <td>Description</td>
                    <td><textarea name="description" id="description" >Enter text... </textarea> </td>
                </tr>
                <tr>
                    <td>Keywords</td>
                    <td><input type="text" name="keywords" id="keywords" /></td></tr>
                <tr>
                    <td>Author</td>
                    <td><input type="text" name="author" id="author" /> </td></tr>                 
                <tr>
                    <td>Creator</td>
                    <td><input type="text" name="creator" id="creator" /> </td></tr>                 
                <tr>
                    <td>Capture date</td>
                    <td><input type="date" name="capture" id="capture" /> </td></tr>                                    
                <tr>                    
                <tr><td colspan="2"><input type="submit" value="Register" name="register" id="register" />                    
                </tr>
            </table>
        </form>
    </body>
</html>
