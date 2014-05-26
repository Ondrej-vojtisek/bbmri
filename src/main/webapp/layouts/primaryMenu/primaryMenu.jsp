<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="projectBean" beanclass="cz.bbmri.action.project.ProjectActionBean"/>
<s:useActionBean var="biobankBean" beanclass="cz.bbmri.action.biobank.BiobankActionBean"/>
<s:useActionBean var="userBean" beanclass="cz.bbmri.action.user.UserActionBean"/>
<s:useActionBean var="userFindBean" beanclass="cz.bbmri.action.user.FindUserActionBean"/>
<s:useActionBean var="globalSettingsBean" beanclass="cz.bbmri.action.globalSettings.GlobalSettingsActionBean"/>
<s:useActionBean var="archiveBean" beanclass="cz.bbmri.action.support.ArchiveActionBean"/>


<s:layout-definition>

<%-- -------------------------------------------------------------------- --%>

<li <c:if test="${primarymenu == 'home'}"> class="active" </c:if>">
<s:link beanclass="cz.bbmri.action.DashboardActionBean">
    <f:message key="home"/>
</s:link>
</li>

<%-- -------------------------------------------------------------------- --%>

<li class="dropdown <c:if test="${primarymenu == 'project'}"> active </c:if>">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
        <f:message key="cz.bbmri.entities.Project.projects"/>
        <b class="caret"></b></a>
    <ul class="dropdown-menu">

            <%-- -------------------------------------------------------------------- --%>

        <li <c:if test="${secondarymenu == 'reservations'}"> class="active" </c:if>>
            <s:link beanclass="cz.bbmri.action.reservation.ReservationActionBean">
                <f:message key="cz.bbmri.entities.SampleReservation.sampleReservations"/>
            </s:link>
        </li>


            <%-- -------------------------------------------------------------------- --%>

        <li <c:if test="${secondarymenu == 'project_my_projects'}"> class="active" </c:if>>
            <s:link beanclass="${projectBean.name}">
                <f:message key="cz.bbmri.entities.Project.myProjects"/>
            </s:link>
        </li>

            <%-- -------------------------------------------------------------------- --%>

        <li <c:if test="${secondarymenu == 'project_create_project'}"> class="active" </c:if>>
            <s:link beanclass="cz.bbmri.action.project.CreateProjectActionBean" event="initial">
                <f:message key="cz.bbmri.action.project.ProjectActionBean.create"/>
            </s:link>
        </li>

            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="projectBean" event="display">
            <li <c:if test="${secondarymenu == 'project_all'}"> class="active" </c:if>>
                <s:link beanclass="${projectBean.name}" event="display">
                    <f:message key="cz.bbmri.action.project.ProjectActionBean.allProjects"/>
                </s:link>
            </li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>

            <%--<li class="divider"></li>--%>
            <%--<li class="nav-header">Nav header</li>--%>
    </ul>
</li>


<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="biobankBean" event="allBiobanks">
    <li class="dropdown <c:if test="${primarymenu == 'biobank'}"> active </c:if>">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
            <f:message key="cz.bbmri.entities.Biobank.biobanks"/>
            <b class="caret"></b></a>
        <ul class="dropdown-menu">

                <%-- -------------------------------------------------------------------- --%>

            <security:allowed bean="biobankBean" event="allBiobanks">
                <li <c:if test="${secondarymenu == 'biobank_all'}"> class="active" </c:if> ><s:link
                        beanclass="${biobankBean.name}" event="allBiobanks">
                    <f:message key="cz.bbmri.entities.Biobank.allBiobanks"/>
                </s:link>
                </li>
            </security:allowed>

                <%-- -------------------------------------------------------------------- --%>

                <%--There can't be used createActionBean because it is set as a wizard. Don't know why.--%>
            <security:allowed bean="biobankBean" event="createBiobank">
                <li
                        <c:if test="${secondarymenu == 'biobank_create'}"> class="active" </c:if> ><s:link
                        beanclass="cz.bbmri.action.biobank.CreateActionBean"
                        event="display">
                    <f:message key="cz.bbmri.action.biobank.BiobankActionBean.create"/>
                </s:link>
                </li>
            </security:allowed>

                <%-- -------------------------------------------------------------------- --%>

                <%--List all "my" biobanks in menu. If I can edit that the link leads to editable form --%>

            <c:if test="${not empty biobankBean.loggedUser.biobankAdministrators}">
                <li class="divider"></li>
                <li class="nav-header">
                    <f:message key="cz.bbmri.entities.Biobank.myBiobanks"/>
                </li>

                <c:forEach var="ba" items="${biobankBean.loggedUser.biobankAdministrators}">
                    <li>
                        <security:allowed bean="biobankBean" event="detail">
                            <s:link beanclass="cz.bbmri.action.biobank.BiobankActionBean"
                                    event="detail">
                                <s:param name="biobankId" value="${ba.biobank.id}"/>
                                ${ba.biobank.abbreviation}
                            </s:link>
                        </security:allowed>
                    </li>

                </c:forEach>
            </c:if>

                <%-- -------------------------------------------------------------------- --%>

        </ul>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="userBean" event="display">
    <li class="dropdown <c:if test="${primarymenu == 'user'}"> active </c:if>">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
            <f:message key="cz.bbmri.entities.User.users"/>
            <b class="caret"></b></a>
        <ul class="dropdown-menu">

                <%-- -------------------------------------------------------------------- --%>

            <security:allowed bean="userBean" event="display">
                <li <c:if test="${secondarymenu == 'user_all'}"> class="active" </c:if>>
                    <s:link beanclass="${userBean.name}">
                        <f:message key="cz.bbmri.action.entities.User.allUsers"/>
                    </s:link>
                </li>
            </security:allowed>

                <%-- -------------------------------------------------------------------- --%>

            <security:allowed bean="userBean" event="createUser">
                <li <c:if test="${secondarymenu == 'user_create'}"> class="active" </c:if>>
                    <s:link beanclass="${userBean.name}" event="createUser">
                        <f:message key="cz.bbmri.action.user.UserActionBean.create"/>
                    </s:link>
                </li>
            </security:allowed>

                <%-- -------------------------------------------------------------------- --%>

            <security:allowed bean="userFindBean" event="find">
                <li <c:if test="${secondarymenu == 'user_find'}"> class="active" </c:if>>
                    <s:link beanclass="${userFindBean.name}" event="find">
                        <f:message key="cz.bbmri.action.user.UserActionBean.findUser"/>
                    </s:link>
                </li>
            </security:allowed>

                <%-- -------------------------------------------------------------------- --%>

        </ul>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>


<li class="dropdown <c:if test="${primarymenu == 'support'}"> active </c:if>">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
        <f:message key="support"/>
        <b class="caret"></b></a>
    <ul class="dropdown-menu">

            <%-- -------------------------------------------------------------------- --%>

        <li <c:if test="${secondarymenu == 'contacts'}"> class="active" </c:if>>
            <s:link beanclass="cz.bbmri.action.support.SupportActionBean">
                <f:message key="cz.bbmri.action.support.SupportActionBean.contacts"/>
            </s:link>
        </li>

            <%-- -------------------------------------------------------------------- --%>
        <security:allowed bean="archiveBean" event="display">
            <li <c:if test="${secondarymenu == 'archive'}"> class="active" </c:if>>
                <s:link beanclass="cz.bbmri.action.support.ArchiveActionBean">
                    <f:message key="cz.bbmri.action.support.ArchiveActionBean.archive"/>
                </s:link>
            </li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="globalSettingsBean" event="all">
            <li <c:if test="${secondarymenu == 'settings'}"> class="active" </c:if>>
                <s:link beanclass="${globalSettingsBean.name}">
                    <f:message key="cz.bbmri.action.globalSettings.GlobalSettingsActionBean"/>
                </s:link>
            </li>
        </security:allowed>

    </ul>
</li>

</s:layout-definition>