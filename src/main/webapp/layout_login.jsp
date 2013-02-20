<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<s:layout-definition>
    <html>
    <head>
        <title><c:out value="${titles}"/></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css"/>
        <s:layout-component name="hlavicka"/>
        <div style="text-align: right; margin-right: 20px;"><b><f:message key="version"/>:</b> <i>PROJECT_VERSION</i>
        </div>
    </head>
    <body>
    <h1><c:out value="${titles}"/></h1>

    <div class="login_screen">
        <div class="form-signin">
            <s:layout-component name="body"/>
        </div>
    </div>
    <a href="http://www.recamo.cz/cz/bbmri/" alt="www.recamo.cz">
        <div class="images">
            <img src="images/recamo_logo.png" alt="recamo" style="margin-right: 30px;">
            <img src="images/bbmri_logo.gif" alt="bbmri" style="margin-left: 30px;">
        </div>
    </a>
    </body>
    </html>

</s:layout-definition>