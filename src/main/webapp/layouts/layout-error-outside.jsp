<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:set var="request" value="${pageContext.request}"/>
<c:set var="context" value="${request.contextPath}"/>

<%--<c:set var="locale" value="${actionBean.locale}"/>--%>

<s:layout-definition>
    <!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${context}/css/bootstrap.css"/>
        <link rel="stylesheet" type="text/css" href="${context}/css/datepicker.css"/>
        <link rel="stylesheet" type="text/css" href="${context}/css/contentPage.css"/>
        <title>BBMRI CZ</title>
        <s:layout-component name="hlavicka"/>
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

            <s:messages/>

            <s:errors/>

            <h1>${title}</h1>


            <s:layout-component name="body"/>


        </div>
    </div>

    <script type="text/javascript" src="${context}/libs/jquery-latest.js"></script>
    <script type="text/javascript" src="${context}/libs/bootstrap.min.js"></script>
    <script type="text/javascript" src="${context}/libs/bootstrap-fileupload.js"></script>
    <script type="text/javascript" src="${context}/libs/bootstrap-datepicker.js"></script>

    <s:layout-component name="jsLibrary"/>

    <script type="text/javascript">
        $(function () {
            $('.dropdown-toggle').dropdown();
        });

    </script>
    </body>

    </html>

</s:layout-definition>

