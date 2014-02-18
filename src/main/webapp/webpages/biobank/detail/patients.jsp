<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.biobank.BiobankActionBean.detail" var="title"/>
<s:useActionBean var="biobankBean" beanclass="cz.bbmri.action.biobank.BiobankActionBean"/>
<s:useActionBean var="patientBean" beanclass="cz.bbmri.action.patient.PatientActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 ternarymenu="patients">

    <s:layout-component name="body">

        <table class="table table-hover table-striped">

            <s:layout-render name="/webpages/component/detail/patient/header.jsp"/>

            <tbody>

            <c:if test="${empty biobankBean.patients}">
                <tr>
                    <td colspan="5"><f:message key="empty"/></td>
                </tr>
            </c:if>
            <c:forEach items="${biobankBean.patients}" var="patient">
                <tr>

                    <s:layout-render name="/webpages/component/detail/patient/row.jsp" patient="${patient}"/>

                    <c:set target="${patientBean}" property="biobankId" value="${biobankBean.biobankId}"/>
                    <td class="action">
                        <security:allowed bean="patientBean" event="detail">
                            <div class="tableAction">
                                <s:link beanclass="cz.bbmri.action.patient.PatientActionBean" event="detail"
                                        class="btn btn-primary btnMargin">
                                    <s:param name="biobankId" value="${biobankBean.biobankId}"/>
                                    <s:param name="patientId" value="${patient.id}"/>
                                    <f:message key="detail"/>
                                </s:link>
                            </div>
                        </security:allowed>
                    </td>


                </tr>
            </c:forEach>
            </tbody>
        </table>

    </s:layout-component>
</s:layout-render>
