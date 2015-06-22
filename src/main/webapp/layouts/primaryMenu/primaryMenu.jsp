<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="biobankActionBean" beanclass="cz.bbmri.action.BiobankActionBean"/>
<stripes:useActionBean var="userActionBean" beanclass="cz.bbmri.action.UserActionBean"/>
<stripes:useActionBean var="projectActionBean" beanclass="cz.bbmri.action.ProjectActionBean"/>
<stripes:useActionBean var="reservationActionBean" beanclass="cz.bbmri.action.ReservationActionBean"/>
<stripes:useActionBean var="materialTypeActionBean" beanclass="cz.bbmri.action.MaterialTypeActionBean"/>
<stripes:useActionBean var="sampleActionBean" beanclass="cz.bbmri.action.SampleActionBean"/>
<%--<stripes:useActionBean var="userFindBean" beanclass="cz.bbmri.action.user.FindUserActionBean"/>--%>
<%--<stripes:useActionBean var="globalSettingsBean" beanclass="cz.bbmri.action.globalSettings.GlobalSettingsActionBean"/>--%>
<%--<stripes:useActionBean var="archiveBean" beanclass="cz.bbmri.action.support.ArchiveActionBean"/>--%>


<stripes:layout-definition>

    <%--<a class="brand" href="#">BBMRI_CZ</a>--%>

    <%-- -------------------------------------------------------------------- --%>

    <li <core:if test="${primarymenu == 'home'}"> class="active" </core:if>">
    <stripes:link beanclass="cz.bbmri.action.DashboardActionBean">
        <format:message key="home"/>
    </stripes:link>
    </li>

    <%-- -------------------------------------------------------------------- --%>
    <security:allowed bean="projectActionBean" event="myProjects">
        <li class="dropdown <core:if test="${primarymenu == 'project'}"> active </core:if>">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                <format:message key="cz.bbmri.entity.Project.projects"/>
                <b class="caret"></b></a>
            <ul class="dropdown-menu">

                    <%-- -------------------------------------------------------------------- --%>

                <security:allowed bean="reservationActionBean" event="myReservations">
                    <li <core:if test="${secondarymenu == 'my_reservations'}"> class="active" </core:if>>
                        <stripes:link beanclass="${reservationActionBean.name}" event="myReservations">
                            <format:message key="cz.bbmri.entity.Reservation.myReservations"/>
                        </stripes:link>
                    </li>
                </security:allowed>

                    <%-- -------------------------------------------------------------------- --%>

                <security:allowed bean="reservationActionBean" event="all">
                    <li <core:if test="${secondarymenu == 'all_reservations'}"> class="active" </core:if>>
                        <stripes:link beanclass="${reservationActionBean.name}" event="all">
                            <format:message key="cz.bbmri.entity.Reservation.allReservations"/>
                        </stripes:link>
                    </li>
                </security:allowed>

                    <%-- -------------------------------------------------------------------- --%>

                <li <core:if test="${secondarymenu == 'project_my_projects'}"> class="active" </core:if>>
                    <stripes:link beanclass="${projectActionBean.name}" event="myProjects">
                        <format:message key="cz.bbmri.entity.Project.myProjects"/>
                    </stripes:link>
                </li>

                    <%--&lt;%&ndash; -------------------------------------------------------------------- &ndash;%&gt;--%>

                    <%--<li <core:if test="${secondarymenu == 'project_create_project'}"> class="active" </core:if>>--%>
                    <%--<stripes:link beanclass="cz.bbmri.action.project.CreateProjectActionBean" event="initial">--%>
                    <%--<format:message key="cz.bbmri.action.project.ProjectActionBean.create"/>--%>
                    <%--</stripes:link>--%>
                    <%--</li>--%>

                    <%--&lt;%&ndash; -------------------------------------------------------------------- &ndash;%&gt;--%>

                <security:allowed bean="projectActionBean" event="all">
                    <li <core:if test="${secondarymenu == 'project_all'}"> class="active" </core:if>>
                        <stripes:link beanclass="${projectActionBean.name}" event="all">
                            <format:message key="cz.bbmri.entity.Project.allProjects"/>
                        </stripes:link>
                    </li>
                </security:allowed>

                    <%--&lt;%&ndash; -------------------------------------------------------------------- &ndash;%&gt;--%>

                    <%--<li class="divider"></li>--%>
                    <%--<li class="nav-header">Nav header</li>--%>
            </ul>
        </li>
    </security:allowed>


    <%-- -------------------------------------------------------------------- --%>

    <security:allowed bean="biobankActionBean" event="all">
        <li class="dropdown <core:if test="${primarymenu == 'biobank'}"> active </core:if>">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                <format:message key="cz.bbmri.entity.Biobank.biobanks"/>
                <b class="caret"></b></a>
            <ul class="dropdown-menu">

                    <%-- -------------------------------------------------------------------- --%>

                <security:allowed bean="biobankActionBean" event="all">
                    <li <core:if test="${secondarymenu == 'biobank_all'}"> class="active" </core:if> ><stripes:link
                            beanclass="${biobankActionBean.name}" event="all">
                        <format:message key="cz.bbmri.entity.Biobank.biobanks"/>
                    </stripes:link>
                    </li>
                </security:allowed>

                    <%-- -------------------------------------------------------------------- --%>

                <security:allowed bean="materialTypeActionBean" event="all">
                    <li <core:if test="${secondarymenu == 'material'}"> class="active" </core:if> ><stripes:link
                            beanclass="${materialTypeActionBean.name}" event="all">
                        <format:message key="cz.bbmri.entity.MaterialType.materialTypes"/>
                    </stripes:link>
                    </li>
                </security:allowed>

                    <%-- -------------------------------------------------------------------- --%>

                    <%--There can't be used createActionBean because it is set as a wizard. Don't know why.--%>
                    <%--<security:allowed bean="biobankActionBean" event="createBiobank">--%>
                    <%--<li--%>
                    <%--<core:if test="${secondarymenu == 'biobank_create'}"> class="active" </core:if> ><stripes:link--%>
                    <%--beanclass="cz.bbmri.action.biobank.CreateActionBean"--%>
                    <%--event="display">--%>
                    <%--<format:message key="cz.bbmri.action.biobank.BiobankActionBean.create"/>--%>
                    <%--</stripes:link>--%>
                    <%--</li>--%>
                    <%--</security:allowed>--%>

                    <%-- -------------------------------------------------------------------- --%>

                    <%--List all "my" biobanks in menu. --%>

                <core:if test="${not empty biobankActionBean.loggedUser.biobankUser}">
                    <li class="divider"></li>
                    <li class="nav-header">
                        <format:message key="cz.bbmri.entity.Biobank.myBiobanks"/>
                    </li>

                    <core:if test="${not empty biobankActionBean.loggedUser.biobankUser}">
                        <core:forEach var="biobankUser" items="${biobankActionBean.loggedUser.biobankUser}">
                            <li>
                                <security:allowed bean="biobankActionBean" event="detail">
                                    <stripes:link beanclass="cz.bbmri.action.BiobankActionBean"
                                                  event="detail">
                                        <stripes:param name="id" value="${biobankUser.biobank.id}"/>
                                        ${biobankUser.biobank.acronym}
                                    </stripes:link>
                                </security:allowed>
                            </li>

                        </core:forEach>
                    </core:if>
                </core:if>

                    <%-- -------------------------------------------------------------------- --%>

            </ul>
        </li>
    </security:allowed>

    <%-- -------------------------------------------------------------------- --%>

    <security:allowed bean="userActionBean" event="all">
        <li class="dropdown <core:if test="${primarymenu == 'user'}"> active </core:if>">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                <format:message key="cz.bbmri.entity.User.users"/>
                <b class="caret"></b></a>
            <ul class="dropdown-menu">

                    <%-- -------------------------------------------------------------------- --%>

                <security:allowed bean="userActionBean" event="all">
                    <li <core:if test="${secondarymenu == 'user_all'}"> class="active" </core:if>>
                        <stripes:link beanclass="${userActionBean.name}">
                            <format:message key="cz.bbmri.entity.User.users"/>
                        </stripes:link>
                    </li>
                </security:allowed>

                    <%-- -------------------------------------------------------------------- --%>

                    <%--<security:allowed bean="userActionBean" event="createUser">--%>
                    <%--<li <core:if test="${secondarymenu == 'user_create'}"> class="active" </core:if>>--%>
                    <%--<stripes:link beanclass="${userActionBean.name}" event="createUser">--%>
                    <%--<format:message key="cz.bbmri.action.user.UserActionBean.create"/>--%>
                    <%--</stripes:link>--%>
                    <%--</li>--%>
                    <%--</security:allowed>--%>

                    <%-- -------------------------------------------------------------------- --%>

                    <%--<security:allowed bean="userFindBean" event="find">--%>
                    <%--<li <core:if test="${secondarymenu == 'user_find'}"> class="active" </core:if>>--%>
                    <%--<stripes:link beanclass="${userFindBean.name}" event="find">--%>
                    <%--<format:message key="cz.bbmri.action.user.UserActionBean.findUser"/>--%>
                    <%--</stripes:link>--%>
                    <%--</li>--%>
                    <%--</security:allowed>--%>

                    <%-- -------------------------------------------------------------------- --%>

            </ul>
        </li>
    </security:allowed>

    <%-- -------------------------------------------------------------------- --%>

    <core:if test="${actionBean.loggedUser.developer}">
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                <format:message key="cz.bbmri.entity.Role.developer"/>
                <b class="caret"></b></a>
            <ul class="dropdown-menu">

                    <%-- -------------------------------------------------------------------- --%>

                <li>
                    <stripes:link beanclass="cz.bbmri.action.ArchiveActionBean" event="all">
                        <format:message key="cz.bbmri.entity.Archive.archives"/>
                    </stripes:link>
                </li>

                    <%-- -------------------------------------------------------------------- --%>

            </ul>
        </li>
    </core:if>

    <%-- -------------------------------------------------------------------- --%>

    <%--<li class="dropdown <core:if test="${primarymenu == 'support'}"> active </core:if>">--%>
    <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown">--%>
    <%--<format:message key="support"/>--%>
    <%--<b class="caret"></b></a>--%>
    <%--<ul class="dropdown-menu">--%>

    <%--&lt;%&ndash; -------------------------------------------------------------------- &ndash;%&gt;--%>

    <%--<li <core:if test="${secondarymenu == 'contacts'}"> class="active" </core:if>>--%>
    <%--<stripes:link beanclass="cz.bbmri.action.support.SupportActionBean">--%>
    <%--<format:message key="cz.bbmri.action.support.SupportActionBean.contacts"/>--%>
    <%--</stripes:link>--%>
    <%--</li>--%>

    <%--&lt;%&ndash; -------------------------------------------------------------------- &ndash;%&gt;--%>
    <%--<security:allowed bean="archiveBean" event="display">--%>
    <%--<li <core:if test="${secondarymenu == 'archive'}"> class="active" </core:if>>--%>
    <%--<stripes:link beanclass="cz.bbmri.action.support.ArchiveActionBean">--%>
    <%--<format:message key="cz.bbmri.action.support.ArchiveActionBean.archive"/>--%>
    <%--</stripes:link>--%>
    <%--</li>--%>
    <%--</security:allowed>--%>

    <%--&lt;%&ndash; -------------------------------------------------------------------- &ndash;%&gt;--%>

    <%--<security:allowed bean="globalSettingsBean" event="all">--%>
    <%--<li <core:if test="${secondarymenu == 'settings'}"> class="active" </core:if>>--%>
    <%--<stripes:link beanclass="${globalSettingsBean.name}">--%>
    <%--<format:message key="cz.bbmri.action.globalSettings.GlobalSettingsActionBean"/>--%>
    <%--</stripes:link>--%>
    <%--</li>--%>
    <%--</security:allowed>--%>

    <%--</ul>--%>
    <%--</li>--%>

</stripes:layout-definition>