<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<s:layout-definition>
    <html>
    <head>
        <title><c:out value="${title}"/></title>
        <style type="text/css">
            table th{
                text-align: left;
            }


            input.error {
                background-color: yellow;
            }

            body {
                font-family: Helvetica, sans-serif;
                background-color: azure;
            }

            fieldset{
                background-color: white;
            }

            h1 {
                text-align: center;
                text-shadow: aquamarine;
            }

                /*  http://www.alistapart.com/articles/taminglists/ */
            #navigace {
                width: 120px;
                border-right: 1px solid #000;
                padding: 0 0 1em 0;
                margin-bottom: 1em;
                background-color: #90bade;
                color: #333;
            }

            #navigace ul {
                list-style: none;
                margin: 0;
                padding: 0;
                border: none;
            }

            #navigace li {
                border-bottom: 1px solid #90bade;
                margin: 0;
            }

            #navigace li a {
                display: block;
                padding: 5px 5px 5px 0.5em;
                border-left: 10px solid #1958b7;
                border-right: 10px solid #508fc4;
                background-color: #2175bc;
                color: #fff;
                text-decoration: none;
                width: 100%;
            }

            html>body #navigace li a {
                width: auto;
            }

            #navigace li a:hover {
                border-left: 10px solid #1c64d1;
                border-right: 10px solid #5ba3e0;
                background-color: #2586d7;
                color: #fff;
            }

                /*  http://realworldstyle.com/2col.html */
            #navigace {
                width: 120px;
                float: left;
                margin-left: -1px;
            }

            #content {
                padding: 10px;
                margin-left: 130px;
            }

            textarea{
                width: 800px;
                height: 200px;

            }
        </style>
        <s:layout-component name="hlavicka"/>
    </head>
    <body>
    <h1><c:out value="${title}"/></h1>

    <div id="navigace">
            <ul> <ul>

            <s:form beanclass="bbmri.action.BasicActionBean">
            <li><f:message key="user"/></li>
            <li><s:link href="/myAccount.jsp"><f:message key="credentials"/></s:link></li>

            <li><s:link href="/createProject.jsp"><f:message key="projects.createProject"/></s:link></li>
            <li><s:link href="/allProjects.jsp"><f:message key="project.all_projects"/></s:link></li>
            <li><s:link href="/allBiobanks.jsp"><f:message key="biobanks.title"/></s:link></li>


              <c:if test="${loggedUser.ethicalCommitteeOfBiobank != null}">
                <li><f:message key="biobank.ethical_committee"/></li>
                <li><s:link href="/approveProject.jsp"><f:message key="approve_project"/></s:link></li>
              </c:if>

              <c:if test="${loggedUser.biobank != null}">
                 <li><f:message key="biobank.operator"/></li>
                 <li><s:link href="/addSample.jsp"><f:message key="samples.add"/></s:link></li>
                 <li><s:link href="/approveSampleRequest.jsp"><f:message key="sample_requests"/></s:link></li>
                 <li><s:link href="/releaseSample.jsp"><f:message key="sample.release"/></s:link></li>
              </c:if>

              <c:if test="${loggedUser.administrator}">
                 <li><f:message key="system.administrator"/></li>
                 <li><s:link href="/createBiobank.jsp"><f:message key="biobank.create"/></s:link></li>
                 <li><s:link href="/createUser.jsp"><f:message key="user.create"/></s:link></li>
                 <li><s:link href="/changeAdministrator.jsp"><f:message key="change_administrator"/></s:link></li>
              </c:if>
        </ul>
        </s:form>
        <div>
            <p><f:message key="logged_user"/>:<c:out value="${logged}"/></p>
            <s:link event="logout" beanclass="bbmri.action.LoginActionBean" name="logout">
                <f:message key="logout"/></s:link>
        </div>

    </div>


    <div id="content">

        <s:layout-component name="body"/>

    </div>
    </body>
    </html>

</s:layout-definition>