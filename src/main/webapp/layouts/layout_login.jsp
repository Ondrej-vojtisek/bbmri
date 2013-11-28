<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:set var="request" value="${pageContext.request}"/>
<c:set var="context" value="${request.contextPath}"/>


<s:layout-definition>
    <!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${context}/css/bootstrap.css"/>
        <link rel="stylesheet" type="text/css" href="${context}/css/login.css"/>
        <title>BBMRI CZ</title>
        <s:layout-component name="hlavicka"/>
    </head>
    <body>
    <div style="text-align: right; margin-right: 20px;">
        <b><f:message key="version"/>:</b> <i>PROJECT_VERSION</i>
    </div>

    <div class="container-fluid">

        <s:messages/>

        <s:errors/>

        <div class="login_screen">

            <div class="form-signin">
                <s:layout-component name="body"/>
            </div>

        </div>
    </div>
    <a href="http://www.recamo.cz/cz/bbmri/" alt="www.recamo.cz">
        <div class="images">
            <img src="../images/recamo_logo.png" alt="recamo" style="margin-right: 30px;">
            <img src="../images/bbmri_logo.gif" alt="bbmri" style="margin-left: 30px;">
        </div>
    </a>

    <script type="text/javascript" src="${context}/libs/jquery-latest.js"></script>
    <script type="text/javascript" src="${context}/libs/bootstrap.min.js"></script>

    <script type="text/javascript">
        $(function () {
            $(".alert").alert();
        });
    </script>


    </body>
    </html>

</s:layout-definition>