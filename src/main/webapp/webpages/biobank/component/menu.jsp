<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="biobankActionBean" beanclass="cz.bbmri.action.BiobankActionBean"/>

<stripes:layout-definition>
    <ul class="nav nav-tabs">
            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="biobankActionBean" event="detail">
            <li <core:if test="${active == 'detail'}"> class="active" </core:if> >
                <stripes:link beanclass="cz.bbmri.action.BiobankActionBean" event="detail">
                    <stripes:param name="id" value="${actionBean.id}"/>
                    <format:message key="cz.bbmri.entity.Biobank.basicInformation"/>
                </stripes:link>
            </li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="biobankActionBean" event="biobankuser">
            <li <core:if test="${active == 'biobankuser'}"> class="active" </core:if>>
                <stripes:link beanclass="cz.bbmri.action.BiobankActionBean"
                              event="biobankuser">
                    <stripes:param name="id" value="${actionBean.id}"/>
                    <format:message key="cz.bbmri.entity.BiobankUser.biobankUsers"/>
                </stripes:link>
            </li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="biobankActionBean" event="attachments">
            <li <core:if test="${active == 'attachments'}"> class="active" </core:if>>
                <stripes:link beanclass="cz.bbmri.action.BiobankActionBean"
                              event="attachments">
                    <stripes:param name="id" value="${actionBean.id}"/>
                    <format:message key="cz.bbmri.entity.Biobank.attachment"/>
                </stripes:link>
            </li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="biobankActionBean" event="withdraws">
            <li <core:if test="${active == 'withdraws'}"> class="active" </core:if>>
                <stripes:link beanclass="cz.bbmri.action.BiobankActionBean"
                              event="withdraws">
                    <stripes:param name="id" value="${actionBean.id}"/>
                    <format:message key="cz.bbmri.entity.Withdraw.withdraws"/>
                </stripes:link>
            </li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="biobankActionBean" event="patients">
            <li <core:if test="${active == 'patients'}"> class="active" </core:if>>
                <stripes:link beanclass="cz.bbmri.action.BiobankActionBean"
                              event="patients">
                    <stripes:param name="id" value="${actionBean.id}"/>
                    <format:message key="cz.bbmri.entity.Patient.patients"/>
                </stripes:link>
            </li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="biobankActionBean" event="samples">
            <li <core:if test="${active == 'samples'}"> class="active" </core:if>>
                <stripes:link beanclass="cz.bbmri.action.BiobankActionBean"
                              event="samples">
                    <stripes:param name="id" value="${actionBean.id}"/>
                    <format:message key="cz.bbmri.entity.Sample.samples"/>
                </stripes:link>
            </li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="biobankActionBean" event="questions">
            <li <core:if test="${active == 'questions'}"> class="active" </core:if>>
                <stripes:link beanclass="cz.bbmri.action.BiobankActionBean"
                              event="questions">
                    <stripes:param name="id" value="${actionBean.id}"/>
                    <format:message key="cz.bbmri.entity.Question.questions"/>
                </stripes:link>
            </li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>
    </ul>
</stripes:layout-definition>