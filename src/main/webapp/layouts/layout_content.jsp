<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:set var="request" value="${pageContext.request}"/>
<c:set var="context" value="${request.contextPath}"/>

<s:layout-definition>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${context}/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${context}/css/contentPage.css"/>
    <title><c:out value="${title}"/></title>
    <s:layout-component name="hlavicka"/>
</head>

<body>
<s:useActionBean var="bean" beanclass="bbmri.action.BasicActionBean"/>
<s:useActionBean var="userBean" beanclass="bbmri.action.user.UserActionBean"/>
<s:useActionBean var="biobankBean" beanclass="bbmri.action.biobank.BiobankActionBean"/>

<c:set var="administrator" scope="session" value="${bean.loggedUser.isAdministrator}"/>
<c:set var="biobank" scope="session" value="${null}"/>
<c:set var="logged" scope="session" value="${bean.loggedUser.wholeName}"/>


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

                    <f:message key="logged_user"/>:
                    <s:link beanclass="bbmri.action.user.AccountActionBean">
                        <c:out value="${logged}"/>
                    </s:link>
                </p>

                <p class="navbar-text pull-right" style="margin-right: 30px;">
                    <b><f:message key="version"/>:</b> <i>PROJECT_VERSION</i>
                </p>
                <ul class="nav">

                    <li <c:if test="${primarymenu == 'project'}"> class="active" </c:if> ><s:link
                            beanclass="bbmri.action.project.ProjectActionBean"><f:message
                            key="projects"/></s:link></li>

                    <security:allowed bean="biobankBean" event="allBiobanks">
                    <li <c:if test="${primarymenu == 'biobank'}"> class="active" </c:if> ><s:link
                            beanclass="bbmri.action.biobank.BiobankActionBean"><f:message
                            key="biobanks"/></s:link></li>
                    </security:allowed>

                    <security:allowed bean="userBean" event="allUsers">
                        <li <c:if test="${primarymenu == 'user'}"> class="active" </c:if> ><s:link
                                beanclass="bbmri.action.user.UserActionBean"><f:message
                                key="users"/></s:link></li>
                    </security:allowed>

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
                                beanclass="bbmri.action.project.ProjectActionBean"><f:message
                                key="my_projects"/></s:link></li>
                        <li <c:if test="${secondarymenu == 'project_create_project'}"> class="active" </c:if> >
                            <s:link beanclass="bbmri.action.project.ProjectActionBean"
                                    event="createInitial"><f:message key="projects.createProject"/></s:link></li>
                        <c:if test="${not empty biobank}">
                            <li <c:if test="${secondarymenu == 'project_approve'}"> class="active" </c:if> ><s:link
                                    beanclass="bbmri.action.project.ApproveProjectActionBean"><f:message
                                    key="approve_project"/></s:link></li>
                        </c:if>
                        <li <c:if test="${secondarymenu == 'samples_my_requests'}"> class="active" </c:if> ><s:link
                                beanclass="bbmri.action.SampleQuestionActionBean" event="myRequests"><f:message
                                key="my_requests"/></s:link></li>
                        <c:if test="${not empty biobank || administrator}">
                            <li <c:if test="${secondarymenu == 'project_all'}"> class="active" </c:if> ><s:link
                                    beanclass="bbmri.action.project.ProjectActionBean" event="displayAll"><f:message
                                    key="all_projects"/></s:link></li>
                        </c:if>
                    </c:if>

                    <c:if test="${primarymenu == 'biobank'}">
                        <li <c:if test="${secondarymenu == 'biobank_all'}"> class="active" </c:if> ><s:link
                                beanclass="bbmri.action.biobank.BiobankActionBean"><f:message
                                key="biobanks_all"/></s:link></li>
                        <c:if test="${administrator}">
                            <li <c:if test="${secondarymenu == 'biobank_create'}"> class="active" </c:if> ><s:link
                                    beanclass="bbmri.action.biobank.BiobankActionBean"
                                    event="createBiobank"><f:message key="biobank_create"/></s:link></li>
                        </c:if>
                        <c:if test="${not empty biobank}">
                            <li <c:if test="${secondarymenu == 'sample_approve_request'}"> class="active" </c:if> >
                                <s:link beanclass="bbmri.action.SampleQuestionActionBean"
                                        event="approveSampleRequest"><f:message
                                        key="approve_sample_request"/></s:link></li>
                            <li <c:if test="${secondarymenu == 'sample_create'}"> class="active" </c:if> ><s:link
                                    beanclass="bbmri.action.sample.CreateSampleActionBean"><f:message
                                    key="sample_create"/></s:link></li>
                            <%--
                            <li <c:if test="${secondarymenu == 'sample_withdraw'}"> class="active" </c:if> ><s:link
                                    beanclass="bbmri.action.sample.SampleActionBean"><f:message
                                    key="sample.withdraw"/></s:link></li>     --%>
                            <li <c:if test="${secondarymenu == 'requestGroup_all'}"> class="active" </c:if> ><s:link
                                    beanclass="bbmri.action.SampleQuestionActionBean"
                                    event="allRequestGroups"><f:message key="sample.requests"/></s:link></li>
                            <li <c:if test="${secondarymenu == 'sample_all'}"> class="active" </c:if> ><s:link
                                    beanclass="bbmri.action.sample.SampleActionBean" event="allSamples"><f:message
                                    key="sample.all"/></s:link></li>
                        </c:if>
                    </c:if>

                    <c:if test="${primarymenu == 'user'}">
                        <%@include file="secondaryMenu/user_sec_menu.jsp" %>
                    </c:if>

                </ul>
            </div>
        </div>
        <div class="span9">


            <%--<div class="page-header">--%>
                <%--<h1>${title}</h1>--%>
            <%--</div>--%>

            <div class="action_message">
                <s:messages/>
            </div>


            <c:if test="${primarymenu == 'account'}">
                <ul class="nav nav-tabs">
                    <li <c:if test="${ternarymenu == 'personal_data'}"> class="active" </c:if> ><s:link
                            beanclass="bbmri.action.user.AccountActionBean"><f:message
                            key="credentials.change_title"/></s:link></li>
                    <li <c:if test="${ternarymenu == 'password'}"> class="active" </c:if>><s:link
                            beanclass="bbmri.action.user.AccountActionBean"
                            event="changePasswordView"><f:message
                            key="credentials.change_password"/></s:link></li>
                    <li <c:if test="${ternarymenu == 'roles'}"> class="active" </c:if>><s:link
                            beanclass="bbmri.action.user.AccountActionBean" event="rolesView"><f:message
                            key="credentials.myRoles"/></s:link></li>
                </ul>
            </c:if>

            <c:if test="${primarymenu == 'user' and not empty ternarymenu}">
                <ul class="nav nav-tabs">
                    <li <c:if test="${ternarymenu == 'personal_data'}"> class="active" </c:if> ><s:link
                            beanclass="bbmri.action.user.UserActionBean" event="detail">
                        <s:param name="id" value="${userBean.user.id}"/>
                        <f:message
                                key="credentials.change_title"/>
                    </s:link></li>
                    <li <c:if test="${ternarymenu == 'roles'}"> class="active" </c:if>><s:link
                            beanclass="bbmri.action.user.UserActionBean"
                            event="rolesView">
                        <s:param name="id" value="${userBean.user.id}"/>
                        <f:message
                                key="credentials.roles"/></s:link></li>
                </ul>
            </c:if>

                <c:if test="${primarymenu == 'biobank' and not empty ternarymenu}">
                   <ul class="nav nav-tabs">
                       <li <c:if test="${ternarymenu == 'detail'}"> class="active" </c:if> ><s:link
                               beanclass="bbmri.action.biobank.BiobankActionBean" event="detail">
                           <s:param name="id" value="${biobankBean.biobank.id}"/>
                           <f:message
                                   key="biobank.basic.data"/>
                       </s:link></li>
                       <li <c:if test="${ternarymenu == 'administrators'}"> class="active" </c:if>><s:link
                               beanclass="bbmri.action.biobank.BiobankActionBean"
                               event="administrators">
                           <s:param name="id" value="${biobankBean.biobank.id}"/>
                           <f:message
                                   key="biobank.administrators"/></s:link></li>
                   </ul>
               </c:if>

            <s:layout-component name="body"/>


            <script type="text/javascript" src="${context}/libs/jquery-latest.js"></script>
            <script type="text/javascript" src="${context}/libs/bootstrap.min.js"></script>
            <script type="text/javascript" src="${context}/libs/jquery.tablesorter.js"></script>


            <script type="text/javascript">

                $(document).ready(function () {

                    // Get Headers with Class noSort //
                    var theHeaders = {}
                    $(this).find('th.noSort').each(function (i, el) {
                        theHeaders[$(this).index()] = { sorter: false };
                    });

                    // Initialize Table Sorter //
                    $("table").tablesorter({
                        headers: theHeaders,
                        widgets: ["zebra"],
                        // change the default striping class names
                        // updated in v2.1 to use widgetOptions.zebra = ["even", "odd"]
                        // widgetZebra: { css: [ "normal-row", "alt-row" ] } still works
                        widgetOptions: {
                            zebra: [ "normal-row", "alt-row" ]
                        }
                    });

                });

            </script>

        </div>
    </div>
</div>

</body>
</html>

</s:layout-definition>