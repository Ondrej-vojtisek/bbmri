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
    <script type="text/javascript" src="libs/jquery-latest.js"></script>
    <script type="text/javascript" src="libs/jquery.tablesorter.js"></script>
    <script type="text/javascript">

        $(document).ready(function() {

            // Get Headers with Class noSort //
            var theHeaders = {}
            $(this).find('th.noSort').each(function(i,el){
                theHeaders[$(this).index()] = { sorter: false };
            });

            // Initialize Table Sorter //
            $("table").tablesorter({
                 headers: theHeaders,
                 widgets: ["zebra"],
                    // change the default striping class names
                    // updated in v2.1 to use widgetOptions.zebra = ["even", "odd"]
                    // widgetZebra: { css: [ "normal-row", "alt-row" ] } still works
                    widgetOptions : {
                      zebra : [ "normal-row", "alt-row" ]
                    }
            });
        });


    </script>
    <body>
    <s:useActionBean var="bean" beanclass="bbmri.action.BasicActionBean"/>
    <div class="navbar navbar-inverse navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container-fluid">
                <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                    <span class="icon-bar"></span>
                </a>

                <div class="nav-collapse collapse">
                    <p class="navbar-text pull-right">
                        <s:link event="logout" beanclass="bbmri.action.LoginActionBean">
                            <f:message key="logout"/></s:link>
                    </p>

                    <p class="navbar-text pull-right" style="margin-right: 30px;">
                        <f:message key="logged_user"/>: <s:link beanclass="bbmri.action.AccountActionBean"><c:out
                            value="${logged}"/></s:link>
                    </p>

                    <p class="navbar-text pull-right" style="margin-right: 30px;">
                        <b><f:message key="version"/>:</b> <i>PROJECT_VERSION</i>
                    </p>
                    <ul class="nav">


                        <li <c:if test="${primarymenu == 'project'}"> class="active" </c:if> ><s:link
                                beanclass="bbmri.action.Project.ProjectActionBean"><f:message
                                key="projects"/></s:link></li>
                        <li <c:if test="${primarymenu == 'biobank'}"> class="active" </c:if> ><s:link
                                beanclass="bbmri.action.Biobank.BiobankActionBean"><f:message
                                key="biobanks"/></s:link></li>
                        <c:if test="${administrator}">
                            <li <c:if test="${primarymenu == 'users'}"> class="active" </c:if> ><s:link
                                    beanclass="bbmri.action.UserActionBean"><f:message
                                    key="users"/></s:link></li>
                        </c:if>
                        <c:if test="${administrator}">
                            <li <c:if test="${primarymenu == 'changeadministrator'}"> class="active" </c:if>><s:link
                                    beanclass="bbmri.action.ChangeAdministrator"><f:message
                                    key="change_administrator"/></s:link></li>
                        </c:if>

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

                        <c:if test="${primarymenu == 'project'}">
                            <li <c:if test="${secondarymenu == 'project_my_projects'}"> class="active" </c:if> ><s:link
                                    beanclass="bbmri.action.Project.ProjectActionBean"><f:message
                                    key="my_projects"/></s:link></li>
                            <li <c:if test="${secondarymenu == 'project_create_project'}"> class="active" </c:if> >
                                <s:link beanclass="bbmri.action.Project.ProjectActionBean"
                                        event="createInitial"><f:message key="projects.createProject"/></s:link></li>
                            <c:if test="${not empty biobank}">
                                <li <c:if test="${secondarymenu == 'project_approve'}"> class="active" </c:if> ><s:link
                                        beanclass="bbmri.action.Project.ApproveProjectActionBean"><f:message
                                        key="approve_project"/></s:link></li>
                            </c:if>
                            <li <c:if test="${secondarymenu == 'samples_my_requests'}"> class="active" </c:if> ><s:link
                                    beanclass="bbmri.action.SampleQuestionActionBean" event="myRequests"><f:message
                                    key="my_requests"/></s:link></li>
                            <c:if test="${not empty biobank || administrator}">
                                <li <c:if test="${secondarymenu == 'project_all'}"> class="active" </c:if> ><s:link
                                        beanclass="bbmri.action.Project.ProjectActionBean" event="displayAll"><f:message
                                        key="all_projects"/></s:link></li>
                            </c:if>
                        </c:if>

                        <c:if test="${primarymenu == 'biobank'}">
                            <li <c:if test="${secondarymenu == 'biobank_all'}"> class="active" </c:if> ><s:link
                                    beanclass="bbmri.action.Biobank.BiobankActionBean"><f:message
                                    key="biobanks_all"/></s:link></li>
                            <c:if test="${administrator}">
                                <li <c:if test="${secondarymenu == 'biobank_create'}"> class="active" </c:if> ><s:link
                                        beanclass="bbmri.action.Biobank.BiobankActionBean"
                                        event="createBiobank"><f:message key="biobank_create"/></s:link></li>
                            </c:if>
                            <c:if test="${not empty biobank}">
                                <li <c:if test="${secondarymenu == 'sample_approve_request'}"> class="active" </c:if> >
                                    <s:link beanclass="bbmri.action.SampleQuestionActionBean"
                                            event="approveSampleRequest"><f:message
                                            key="approve_sample_request"/></s:link></li>
                                <li <c:if test="${secondarymenu == 'sample_create'}"> class="active" </c:if> ><s:link
                                        beanclass="bbmri.action.Sample.CreateSampleActionBean"><f:message
                                        key="sample_create"/></s:link></li>
                                <%--
                                <li <c:if test="${secondarymenu == 'sample_withdraw'}"> class="active" </c:if> ><s:link
                                        beanclass="bbmri.action.Sample.SampleActionBean"><f:message
                                        key="sample.withdraw"/></s:link></li>     --%>
                                <li <c:if test="${secondarymenu == 'requestGroup_all'}"> class="active" </c:if> ><s:link
                                        beanclass="bbmri.action.SampleQuestionActionBean"
                                        event="allRequestGroups"><f:message key="sample.requests"/></s:link></li>
                                <li <c:if test="${secondarymenu == 'sample_all'}"> class="active" </c:if> ><s:link
                                        beanclass="bbmri.action.Sample.SampleActionBean" event="allSamples"><f:message
                                        key="sample.all"/></s:link></li>
                            </c:if>
                        </c:if>

                        <c:if test="${primarymenu == 'users'}">
                            <c:if test="${administrator}">
                                <li <c:if test="${secondarymenu == 'user_all'}"> class="active" </c:if> ><s:link
                                        beanclass="bbmri.action.UserActionBean"><f:message key="all"/></s:link></li>
                                <li <c:if test="${secondarymenu == 'user_create'}"> class="active" </c:if>><s:link
                                        beanclass="bbmri.action.UserActionBean" event="createUser"><f:message
                                        key="user.create"/></s:link></li>
                            </c:if>
                        </c:if>

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