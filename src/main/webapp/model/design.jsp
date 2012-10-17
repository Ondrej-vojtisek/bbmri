 
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<s:layout-definition>
<html>
<head>
  <title><c:out value="${title}" /></title>
  <style type="text/css">
      input.error { background-color: yellow; }
      body { font-family: Helvetica, sans-serif; background-color: azure;}
      h1 { text-align: center; text-decoration: underline; text-shadow: aquamarine; text-transform: capitalize; }
      /*  http://www.alistapart.com/articles/taminglists/ */
     #navigace { width: 120px; border-right: 1px solid #000; padding: 0 0 1em 0; margin-bottom: 1em;
               background-color: #90bade;  color: #333;  }
     #navigace ul { list-style: none;  margin: 0;  padding: 0;  border: none; }
     #navigace li { border-bottom: 1px solid #90bade;  margin: 0; }
     #navigace li a { display: block; padding: 5px 5px 5px 0.5em; border-left: 10px solid #1958b7;
         border-right: 10px solid #508fc4; background-color: #2175bc;  color: #fff; text-decoration: none; width: 100%; }
      html>body #navigace li a { width: auto; }
      #navigace li a:hover { border-left: 10px solid #1c64d1;  border-right: 10px solid #5ba3e0;
          background-color: #2586d7;  color: #fff; }
      /*  http://realworldstyle.com/2col.html */
      #navigace { width: 120px; float: left; margin-left: -1px; }
      #content { padding: 10px;  margin-left: 130px; }
  </style>
    <s:layout-component name="hlavicka"/>
</head>
<body>
   <h1><c:out value="${title}" /></h1>
   <div id="navigace">
     <ul>
       <li><s:link href="/index.jsp">Login</s:link></li>
       <li><s:link href="/projects.jsp">Projects</s:link></li>
       <li><s:link href="/researchers.jsp">Researchers</s:link></li>

     </ul>
   </div>
   <div id="content">
       
       <s:layout-component name="body"/>
       
    </div>
</body>
</html>
 
</s:layout-definition>