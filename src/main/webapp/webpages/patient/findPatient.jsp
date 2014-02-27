<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="patientBean" beanclass="cz.bbmri.action.patient.FindPatientActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="patients">

    <s:layout-component name="body">

        <s:form beanclass="cz.bbmri.action.patient.FindPatientActionBean" class="form-horizontal">
            <fieldset>
                <legend><f:message key="cz.bbmri.action.patient.CreatePatientActionBean.findPatient"/></legend>

                <s:layout-render name="/webpages/patient/component/patientForm.jsp"/>

                <div class="form-actions">
                    <s:submit name="findPatient" class="btn btn-primary">
                        <s:param name="biobankId" value="${patientBean.biobankId}"/>
                    </s:submit>
                </div>

            </fieldset>
        </s:form>

        <table class="table table-hover table-striped">

            <s:layout-render name="/webpages/component/detail/patient/header.jsp"/>

            <tbody>

            <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                                           collection="${patientBean.patients}"/>

            <c:forEach items="${patientBean.patients}" var="patient">
                <tr>
                    <s:layout-render name="/webpages/component/detail/patient/row.jsp" patient="${patient}"/>
                    <td class="action">
                        <div class="tableAction">
                            <s:link beanclass="cz.bbmri.action.patient.PatientActionBean" event="detail"
                                    class="btn btn-primary btnMargin">
                                <s:param name="biobankId" value="${patientBean.biobankId}"/>
                                <s:param name="patientId" value="${patient.id}"/>
                                <f:message key="detail"/>
                            </s:link>
                        </div>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>


    </s:layout-component>
</s:layout-render>