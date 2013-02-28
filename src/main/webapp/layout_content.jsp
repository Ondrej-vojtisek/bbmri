<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<s:layout-definition>
    <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
        <link rel="stylesheet" href="http://twitter.github.com/bootstrap/assets/css/bootstrap-responsive.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/contentPage.css"/>
        <title><c:out value="${title}"/></title>
        <s:layout-component name="hlavicka"/>
    </head>
    <script type="text/javascript" src="/libs/jquery-latest.js"></script>
    <script type="text/javascript" src="/libs/jquery.tablesorter.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
                    $("#sortableTable").tablesorter();
                }
        );
    </script>

    <body>

    <div class="navbar navbar-inverse navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container-fluid">
                <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                    <span class="icon-bar"></span>
                </a>

                <div class="nav-collapse collapse">
                    <p class="navbar-text pull-right">
                        <s:link event="logout" beanclass="bbmri.action.LoginActionBean" name="logout">
                            <f:message key="logout"/></s:link>
                    </p>

                    <p class="navbar-text pull-right" style="margin-right: 30px;">
                        <f:message key="logged_user"/>: <s:link href="/my_account.jsp"><c:out
                            value="${logged}"/></s:link>
                    </p>

                    <p class="navbar-text pull-right" style="margin-right: 30px;">
                        <b><f:message key="version"/>:</b> <i>PROJECT_VERSION</i>
                    </p>
                    <ul class="nav">
                        <s:layout-component name="primary_menu"/>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span3">
                <div class="well sidebar-nav">
                    <ul class="nav nav-list">
                        <li class="nav-header"><f:message key="operations"/></li>
                        <s:layout-component name="secondary_menu"/>
                    </ul>
                </div>
            </div>
            <div class="span9">
                <div class="hero-unit">
                    <div class="action_message">
                        <s:messages/>
                    </div>
                    <s:layout-component name="body"/>
                </div>
            </div>
        </div>
    </div>

    </body>
    </html>

</s:layout-definition>