<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<core:set var="request" value="${pageContext.request}"/>
<core:set var="context" value="${request.contextPath}"/>

<%--<core:set var="locale" value="${actionBean.locale}"/>--%>

<stripes:layout-definition>
    <!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${context}/css/bootstrap.css"/>
        <link rel="stylesheet" type="text/css" href="${context}/css/datepicker.css"/>
        <link rel="stylesheet" type="text/css" href="${context}/css/contentPage.css"/>
        <title>BBMRI CZ</title>
        <stripes:layout-component name="hlavicka"/>
    </head>

    <body>
    <div class="navbar navbar-inverse navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container-fluid">
                <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                    <span class="icon-bar"></span>
                </a>

                <div class="nav-collapse collapse">
                    <ul class="nav">
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="row-fluid">

            <stripes:messages/>

            <stripes:errors/>

            <h1>${title}</h1>


            <stripes:layout-component name="body"/>


        </div>
    </div>

    <script type="text/javascript" src="${context}/libs/jquery-latest.js"></script>
    <script type="text/javascript" src="${context}/libs/bootstrap.min.js"></script>
    <script type="text/javascript" src="${context}/libs/bootstrap-fileupload.js"></script>
    <script type="text/javascript" src="${context}/libs/bootstrap-datepicker.js"></script>

    <stripes:layout-component name="jsLibrary"/>

    <script type="text/javascript">
        $(function () {
            $('.dropdown-toggle').dropdown();
        });

    </script>
    </body>

    </html>

</stripes:layout-definition>

