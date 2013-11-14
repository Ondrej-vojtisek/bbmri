<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="projectBean" beanclass="bbmri.action.project.ProjectActionBean"/>
<s:useActionBean var="biobankBean" beanclass="bbmri.action.biobank.BiobankActionBean"/>
<s:useActionBean var="userBean" beanclass="bbmri.action.user.UserActionBean"/>
<s:useActionBean var="userFindBean" beanclass="bbmri.action.user.FindUserActionBean"/>

<%------------------------------------------------------------------------%>

<li class="dropdown <c:if test="${primarymenu == 'project'}"> active </c:if>">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
        <f:message key="projects"/>
        <b class="caret"></b></a>
    <ul class="dropdown-menu">

        <%------------------------------------------------------------------------%>

        <li <c:if test="${secondarymenu == 'project_my_projects'}"> class="active" </c:if>>
            <s:link beanclass="${projectBean.name}">
                <f:message key="bbmri.action.project.ProjectActionBean.myProjects"/>
            </s:link>
        </li>

        <%------------------------------------------------------------------------%>

        <li <c:if test="${secondarymenu == 'project_create_project'}"> class="active" </c:if>>
            <s:link beanclass="bbmri.action.project.CreateProjectActionBean" event="initial">
                <f:message key="bbmri.action.project.ProjectActionBean.create"/>
            </s:link>
        </li>

        <%------------------------------------------------------------------------%>

        <security:allowed bean="projectBean" event="allProjects">
            <li <c:if test="${secondarymenu == 'project_all'}"> class="active" </c:if>>
                <s:link beanclass="${projectBean.name}" event="allProjects">
                    <f:message key="bbmri.action.project.ProjectActionBean.allProjects"/>
                </s:link>
            </li>
        </security:allowed>

        <%------------------------------------------------------------------------%>

        <%--<li class="divider"></li>--%>
        <%--<li class="nav-header">Nav header</li>--%>
    </ul>
</li>


<%------------------------------------------------------------------------%>

<security:allowed bean="biobankBean" event="viewBiobanks">
    <li class="dropdown <c:if test="${primarymenu == 'biobank'}"> active </c:if>">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
            <f:message key="biobanks"/>
            <b class="caret"></b></a>
        <ul class="dropdown-menu">

                <%------------------------------------------------------------------------%>

            <security:allowed bean="biobankBean" event="allBiobanks">
                <li
                        <c:if test="${secondarymenu == 'biobank_all'}"> class="active" </c:if> ><s:link
                        beanclass="${biobankBean.name}" event="allBiobanks">
                    <f:message key="bbmri.action.biobank.BiobankActionBean.allBiobanks"/>
                </s:link>
                </li>
            </security:allowed>

                <%------------------------------------------------------------------------%>

                <%--There can't be used createActionBean because it is set as a wizard. Don't know why.--%>
            <security:allowed bean="biobankBean" event="createBiobank">
                <li
                        <c:if test="${secondarymenu == 'biobank_create'}"> class="active" </c:if> ><s:link
                        beanclass="bbmri.action.biobank.CreateActionBean"
                        event="display">
                    <f:message key="bbmri.action.biobank.BiobankActionBean.create"/>
                </s:link>
                </li>
            </security:allowed>

                <%------------------------------------------------------------------------%>

        </ul>
    </li>
</security:allowed>

<%------------------------------------------------------------------------%>

<security:allowed bean="userBean" event="allUsers">
    <li class="dropdown <c:if test="${primarymenu == 'user'}"> active </c:if>">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
            <f:message key="users"/>
            <b class="caret"></b></a>
        <ul class="dropdown-menu">

                <%------------------------------------------------------------------------%>

            <security:allowed bean="userBean" event="allUsers">
                <li <c:if test="${secondarymenu == 'user_all'}"> class="active" </c:if>>
                    <s:link beanclass="${userBean.name}">
                        <f:message key="bbmri.action.user.UserActionBean.all"/>
                    </s:link>
                </li>
            </security:allowed>

                <%------------------------------------------------------------------------%>

            <security:allowed bean="userBean" event="createUser">
                <li <c:if test="${secondarymenu == 'user_create'}"> class="active" </c:if>>
                    <s:link beanclass="${userBean.name}" event="createUser">
                        <f:message key="bbmri.action.user.UserActionBean.create"/>
                    </s:link>
                </li>
            </security:allowed>

                <%------------------------------------------------------------------------%>

            <security:allowed bean="userFindBean" event="find">
                <li <c:if test="${secondarymenu == 'user_find'}"> class="active" </c:if>>
                    <s:link beanclass="${userFindBean.name}" event="find">
                        <f:message key="bbmri.action.user.UserActionBean.findUser"/>
                    </s:link>
                </li>
            </security:allowed>

                <%------------------------------------------------------------------------%>

        </ul>
    </li>
</security:allowed>


