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
        <link rel="stylesheet" type="text/css" href="${context}/css/graph.css"/>
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
                    <s:layout-render name="/layouts/navigation/navbar.jsp"/>
                    <ul class="nav">
                        <s:layout-render name="/layouts/primaryMenu/primaryMenu.jsp"/>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="row-fluid">

            <s:layout-render name="${actionBean.componentManager.breadcrumbsComponent}"/>

            <s:messages/>

            <s:errors globalErrorsOnly="true"/>

            <h1>${title}</h1>

            <c:if test="${not empty ternarymenu}">
                <ul class="nav nav-tabs">

                    <c:if test="${primarymenu == 'biobank'}">

                        <s:layout-render name="${actionBean.componentManager.secondaryRibbon}"
                                         record="${actionBean.biobank}"/>

                        <s:layout-render name="/layouts/ternaryMenu/biobank_ter_menu.jsp"/>

                    </c:if>

                    <c:if test="${primarymenu == 'user'}">

                        <s:layout-render name="/layouts/ternaryMenu/user_ter_menu.jsp"/>

                    </c:if>

                    <c:if test="${primarymenu == 'project'}">

                        <s:layout-render name="${actionBean.componentManager.secondaryRibbon}"
                                         record="${actionBean.project}"/>

                        <s:layout-render name="/layouts/ternaryMenu/project_ter_menu.jsp"/>

                    </c:if>

                    <c:if test="${primarymenu == 'patient'}">

                        <s:layout-render name="${actionBean.componentManager.secondaryRibbon}"
                                         record="${actionBean.patient}"/>

                        <s:layout-render name="/layouts/ternaryMenu/patient_ter_menu.jsp"/>

                    </c:if>

                    <c:if test="${primarymenu == 'sample'}">

                        <s:layout-render name="${actionBean.componentManager.secondaryRibbon}"
                                         record="${actionBean.biobank}"/>

                        <s:layout-render name="/layouts/ternaryMenu/sample_ter_menu.jsp"/>

                    </c:if>

                </ul>
            </c:if>

            <s:layout-component name="body"/>


        </div>
    </div>

    <script type="text/javascript" src="${context}/libs/jquery-latest.js"></script>
    <script type="text/javascript" src="${context}/libs/bootstrap.min.js"></script>
    <script type="text/javascript" src="${context}/libs/bootstrap-fileupload.js"></script>
    <script type="text/javascript" src="${context}/libs/bootstrap-datepicker.js"></script>

    <script type="text/javascript" src="${context}/libs/jquery.flot.js"></script>
    <script type="text/javascript" src="${context}/libs/jquery.flot.time.js"></script>
    <script type="text/javascript" src="${context}/libs/jquery.flot.symbol.js"></script>

        <%--<script type="text/javascript" src="${context}/libs/storyjs-embed.js"></script>--%>

    <s:layout-component name="jsLibrary"/>

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
    <s:layout-component name="script"/>
    </body>

    </html>

</s:layout-definition>

