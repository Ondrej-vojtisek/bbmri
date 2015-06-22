<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<%--<core:set var="locale" value="${actionBean.locale}"/>--%>

<stripes:layout-definition>
    <!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${context}/css/bootstrap.css"/>
        <link rel="stylesheet" type="text/css" href="${context}/css/datepicker.css"/>
        <link rel="stylesheet" type="text/css" href="${context}/css/contentPage.css"/>
        <link rel="stylesheet" type="text/css" href="${context}/css/graph.css"/>
        <link rel="shortcut icon" href="${context}/favicon.ico" type="image/x-icon"/>
        <title>BBMRI_CZ</title>
        <stripes:layout-component name="head"/>
    </head>

    <body>
    <div class="navbar navbar-inverse navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container-fluid">
                <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                    <span class="icon-bar"></span>
                </a>

                <div class="nav-collapse collapse">
                    <stripes:layout-render name="/layouts/navigation/navbar.jsp"/>
                    <ul class="nav">
                        <stripes:layout-render name="/layouts/primaryMenu/primaryMenu.jsp"/>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="row-fluid">

            <stripes:layout-render name="/webpages/component/breadcrumb/breadcrumb.jsp"/>

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
    <%--<script type="text/javascript" src="${context}/libs/prototype.js"></script>--%>

    <script type="text/javascript" src="${context}/libs/jquery.flot.js"></script>
    <script type="text/javascript" src="${context}/libs/jquery.flot.time.js"></script>
    <script type="text/javascript" src="${context}/libs/jquery.flot.symbol.js"></script>

    <stripes:layout-component name="jsLibrary"/>

    <script type="text/javascript">
        $(function () {
            $('.dropdown-toggle').dropdown();
        });

        $(function () {
            $('.fileupload').fileupload();
        });

        $(function () {
            $(".alert").alert();
        });


    </script>
    <stripes:layout-component name="script"/>
    </body>

    </html>

</stripes:layout-definition>

