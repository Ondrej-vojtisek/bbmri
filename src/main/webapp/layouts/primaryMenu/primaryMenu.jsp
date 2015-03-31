<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="biobankActionBean" beanclass="cz.bbmri.action.BiobankActionBean"/>
<s:useActionBean var="userActionBean" beanclass="cz.bbmri.action.UserActionBean"/>
<s:useActionBean var="projectActionBean" beanclass="cz.bbmri.action.ProjectActionBean"/>
<s:useActionBean var="reservationActionBean" beanclass="cz.bbmri.action.ReservationActionBean"/>
<%--<s:useActionBean var="userFindBean" beanclass="cz.bbmri.action.user.FindUserActionBean"/>--%>
<%--<s:useActionBean var="globalSettingsBean" beanclass="cz.bbmri.action.globalSettings.GlobalSettingsActionBean"/>--%>
<%--<s:useActionBean var="archiveBean" beanclass="cz.bbmri.action.support.ArchiveActionBean"/>--%>


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
            <f:message key="cz.bbmri.entity.Project.projects"/>
            <b class="caret"></b></a>
        <ul class="dropdown-menu">

                <%-- -------------------------------------------------------------------- --%>

            <security:allowed bean="reservationActionBean" event="myReservations">
                <li <c:if test="${secondarymenu == 'my_reservations'}"> class="active" </c:if>>
                    <s:link beanclass="${reservationActionBean.name}" event="myReservations">
                        <f:message key="cz.bbmri.entity.Reservation.myReservations"/>
                    </s:link>
                </li>
            </security:allowed>

                <%-- -------------------------------------------------------------------- --%>

            <security:allowed bean="reservationActionBean" event="all">
                <li <c:if test="${secondarymenu == 'all_reservations'}"> class="active" </c:if>>
                    <s:link beanclass="${reservationActionBean.name}" event="all">
                        <f:message key="cz.bbmri.entity.Reservation.allReservations"/>
                    </s:link>
                </li>
            </security:allowed>

                <%-- -------------------------------------------------------------------- --%>

            <security:allowed bean="projectActionBean" event="myProjects">
                <li <c:if test="${secondarymenu == 'project_my_projects'}"> class="active" </c:if>>
                    <s:link beanclass="${projectActionBean.name}" event="myProjects">
                        <f:message key="cz.bbmri.entity.Project.myProjects"/>
                    </s:link>
                </li>
            </security:allowed>

                <%--&lt;%&ndash; -------------------------------------------------------------------- &ndash;%&gt;--%>

                <%--<li <c:if test="${secondarymenu == 'project_create_project'}"> class="active" </c:if>>--%>
                <%--<s:link beanclass="cz.bbmri.action.project.CreateProjectActionBean" event="initial">--%>
                <%--<f:message key="cz.bbmri.action.project.ProjectActionBean.create"/>--%>
                <%--</s:link>--%>
                <%--</li>--%>

                <%--&lt;%&ndash; -------------------------------------------------------------------- &ndash;%&gt;--%>

            <security:allowed bean="projectActionBean" event="all">
                <li <c:if test="${secondarymenu == 'project_all'}"> class="active" </c:if>>
                    <s:link beanclass="${projectActionBean.name}" event="all">
                        <f:message key="cz.bbmri.entity.Project.allProjects"/>
                    </s:link>
                </li>
            </security:allowed>

                <%--&lt;%&ndash; -------------------------------------------------------------------- &ndash;%&gt;--%>

                <%--<li class="divider"></li>--%>
                <%--<li class="nav-header">Nav header</li>--%>
        </ul>
    </li>


    <%-- -------------------------------------------------------------------- --%>

    <security:allowed bean="biobankActionBean" event="all">
        <li class="dropdown <c:if test="${primarymenu == 'biobank'}"> active </c:if>">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                <f:message key="cz.bbmri.entity.Biobank.biobanks"/>
                <b class="caret"></b></a>
            <ul class="dropdown-menu">

                    <%-- -------------------------------------------------------------------- --%>

                <security:allowed bean="biobankActionBean" event="all">
                    <li <c:if test="${secondarymenu == 'biobank_all'}"> class="active" </c:if> ><s:link
                            beanclass="${biobankActionBean.name}" event="all">
                        <f:message key="cz.bbmri.entity.Biobank.biobanks"/>
                    </s:link>
                    </li>
                </security:allowed>

                    <%-- -------------------------------------------------------------------- --%>

                    <%--There can't be used createActionBean because it is set as a wizard. Don't know why.--%>
                    <%--<security:allowed bean="biobankActionBean" event="createBiobank">--%>
                    <%--<li--%>
                    <%--<c:if test="${secondarymenu == 'biobank_create'}"> class="active" </c:if> ><s:link--%>
                    <%--beanclass="cz.bbmri.action.biobank.CreateActionBean"--%>
                    <%--event="display">--%>
                    <%--<f:message key="cz.bbmri.action.biobank.BiobankActionBean.create"/>--%>
                    <%--</s:link>--%>
                    <%--</li>--%>
                    <%--</security:allowed>--%>

                    <%-- -------------------------------------------------------------------- --%>

                    <%--List all "my" biobanks in menu. --%>

                <c:if test="${not empty biobankActionBean.loggedUser.biobankUser}">
                    <li class="divider"></li>
                    <li class="nav-header">
                        <f:message key="cz.bbmri.entity.Biobank.myBiobanks"/>
                    </li>

                    <c:if test="${not empty biobankActionBean.loggedUser.biobankUser}">
                        <c:forEach var="biobankUser" items="${biobankActionBean.loggedUser.biobankUser}">
                            <li>
                                <security:allowed bean="biobankActionBean" event="detail">
                                    <s:link beanclass="cz.bbmri.action.BiobankActionBean"
                                            event="detail">
                                        <s:param name="id" value="${biobankUser.biobank.id}"/>
                                        ${biobankUser.biobank.acronym}
                                    </s:link>
                                </security:allowed>
                            </li>

                        </c:forEach>
                    </c:if>
                </c:if>

                    <%-- -------------------------------------------------------------------- --%>

            </ul>
        </li>
    </security:allowed>

    <%-- -------------------------------------------------------------------- --%>

    <security:allowed bean="userActionBean" event="all">
        <li class="dropdown <c:if test="${primarymenu == 'user'}"> active </c:if>">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                <f:message key="cz.bbmri.entity.User.users"/>
                <b class="caret"></b></a>
            <ul class="dropdown-menu">

                    <%-- -------------------------------------------------------------------- --%>

                <security:allowed bean="userActionBean" event="all">
                    <li <c:if test="${secondarymenu == 'user_all'}"> class="active" </c:if>>
                        <s:link beanclass="${userActionBean.name}">
                            <f:message key="cz.bbmri.entity.User.users"/>
                        </s:link>
                    </li>
                </security:allowed>

                    <%-- -------------------------------------------------------------------- --%>

                    <%--<security:allowed bean="userActionBean" event="createUser">--%>
                    <%--<li <c:if test="${secondarymenu == 'user_create'}"> class="active" </c:if>>--%>
                    <%--<s:link beanclass="${userActionBean.name}" event="createUser">--%>
                    <%--<f:message key="cz.bbmri.action.user.UserActionBean.create"/>--%>
                    <%--</s:link>--%>
                    <%--</li>--%>
                    <%--</security:allowed>--%>

                    <%-- -------------------------------------------------------------------- --%>

                    <%--<security:allowed bean="userFindBean" event="find">--%>
                    <%--<li <c:if test="${secondarymenu == 'user_find'}"> class="active" </c:if>>--%>
                    <%--<s:link beanclass="${userFindBean.name}" event="find">--%>
                    <%--<f:message key="cz.bbmri.action.user.UserActionBean.findUser"/>--%>
                    <%--</s:link>--%>
                    <%--</li>--%>
                    <%--</security:allowed>--%>

                    <%-- -------------------------------------------------------------------- --%>

            </ul>
        </li>
    </security:allowed>

    <%-- -------------------------------------------------------------------- --%>


    <%--<li class="dropdown <c:if test="${primarymenu == 'support'}"> active </c:if>">--%>
    <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown">--%>
    <%--<f:message key="support"/>--%>
    <%--<b class="caret"></b></a>--%>
    <%--<ul class="dropdown-menu">--%>

    <%--&lt;%&ndash; -------------------------------------------------------------------- &ndash;%&gt;--%>

    <%--<li <c:if test="${secondarymenu == 'contacts'}"> class="active" </c:if>>--%>
    <%--<s:link beanclass="cz.bbmri.action.support.SupportActionBean">--%>
    <%--<f:message key="cz.bbmri.action.support.SupportActionBean.contacts"/>--%>
    <%--</s:link>--%>
    <%--</li>--%>

    <%--&lt;%&ndash; -------------------------------------------------------------------- &ndash;%&gt;--%>
    <%--<security:allowed bean="archiveBean" event="display">--%>
    <%--<li <c:if test="${secondarymenu == 'archive'}"> class="active" </c:if>>--%>
    <%--<s:link beanclass="cz.bbmri.action.support.ArchiveActionBean">--%>
    <%--<f:message key="cz.bbmri.action.support.ArchiveActionBean.archive"/>--%>
    <%--</s:link>--%>
    <%--</li>--%>
    <%--</security:allowed>--%>

    <%--&lt;%&ndash; -------------------------------------------------------------------- &ndash;%&gt;--%>

    <%--<security:allowed bean="globalSettingsBean" event="all">--%>
    <%--<li <c:if test="${secondarymenu == 'settings'}"> class="active" </c:if>>--%>
    <%--<s:link beanclass="${globalSettingsBean.name}">--%>
    <%--<f:message key="cz.bbmri.action.globalSettings.GlobalSettingsActionBean"/>--%>
    <%--</s:link>--%>
    <%--</li>--%>
    <%--</security:allowed>--%>

    <%--</ul>--%>
    <%--</li>--%>

</s:layout-definition>