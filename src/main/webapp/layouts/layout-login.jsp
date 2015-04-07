<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<core:set var="request" value="${pageContext.request}"/>
<core:set var="context" value="${request.contextPath}"/>


<stripes:layout-definition>
    <!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${context}/css/bootstrap.css"/>
        <link rel="stylesheet" type="text/css" href="${context}/css/login.css"/>
        <title>BBMRI CZ</title>
        <stripes:layout-component name="hlavicka"/>
    </head>
    <body>
    <div class="navbar navbar-fixed-top">
        <div class="container-fluid">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
            </a>

            <div class="nav-collapse collapse">
                <stripes:layout-render name="/layouts/navigation/navbar.jsp"/>
            </div>
        </div>
    </div>

    <div class="container-fluid" style="margin-top: 50px;">
        <stripes:messages/>

        <stripes:errors/>

        <div class="login_screen">

            <div class="form-signin">
                <stripes:layout-component name="body"/>
            </div>

        </div>
    </div>
    <a href="http://www.recamo.cz/cz/bbmri/" alt="www.recamo.cz">
        <div class="images">
            <img src="../images/recamo_logo.png" alt="recamo" style="margin-right: 30px;">
            <img src="../images/BBMRIERIC_final.png" alt="bbmri" style="margin-left: 30px;">
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

</stripes:layout-definition>