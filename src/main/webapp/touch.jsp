<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en" xml:lang="en">
<head>
    <title>Touch!</title>
    <meta charset="utf-8">
</head>
<body>
    <h1>Touch!</h1>
    <table border="1">
    <tr>
        <th>Header Name</th><th>Header Value(s)</th>
    </tr>


    <%
       java.util.Enumeration headerNames = request.getHeaderNames();
       while(headerNames.hasMoreElements())
       {
          String paramName = (String) headerNames.nextElement();
          out.print("<tr><td>" + paramName + "</td><td>" + request.getHeader(paramName) + "</td></tr>\n");
       }
    %>
    </table>
    <br />
    <table border="1">
    <tr>
        <th>Attribute Name</th><th>Attribute Value(s)</th>
    </tr>
    <%
       java.util.Enumeration attributeNames = request.getAttributeNames();
       while(attributeNames.hasMoreElements())
       {
          String paramName = (String) attributeNames.nextElement();
          out.print("<tr><td>" + paramName + "</td><td>" + request.getAttribute(paramName).toString() + "</td></tr>");
       }
    %>
    </table>


</body>
</html>
