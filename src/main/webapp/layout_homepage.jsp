<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>
    <!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
        <link rel="stylesheet" href="http://twitter.github.com/bootstrap/assets/css/bootstrap-responsive.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/contentPage.css"/>
        <title><c:out value="${title}"/></title>
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

            <div class="span9">
                <div class="hero-unit">
                    <div class="action_message">
                        <s:messages/>
                    </div>

                    <s:layout-component name="body"/>
                </div>
            </div>
            <div class="span3">
                <div style="text-align: center;">
                    <img src="images/bbmri_logo.gif" alt="bbmri" style="width: 200px; padding-bottom: 20px;">
                    <a href="http://www.recamo.cz/cz/bbmri/" alt="www.recamo.cz">
                        <img src="images/recamo_logo.png" alt="recamo" style="width: 250px; padding-bottom: 20px;">
                    </a>
                </div>
                <div class="well sidebar-nav">
                    <ul class="nav nav-list">
                        <li class="nav-header"></li>
                        <s:layout-component name="links"/>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    </body>
    </html>

</s:layout-definition>