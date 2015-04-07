<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="patientBean" beanclass="cz.bbmri.action.patient.FindPatientActionBean"/>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="patients">

    <stripes:layout-component name="body">

        <stripes:form beanclass="cz.bbmri.action.patient.FindPatientActionBean" class="form-horizontal">
            <fieldset>
                <legend><format:message key="cz.bbmri.action.patient.FindPatientActionBean.findPatient"/></legend>

                <stripes:layout-render name="/webpages/patient/component/patientForm.jsp"/>

                <div class="form-actions">
                    <stripes:submit name="findPatient" class="btn btn-primary">
                        <stripes:param name="biobankId" value="${patientBean.biobankId}"/>
                    </stripes:submit>
                </div>

            </fieldset>
        </stripes:form>

        <table class="table table-hover table-striped">

            <stripes:layout-render name="/webpages/component/detail/patient/header.jsp"/>

            <tbody>

            <stripes:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                                           collection="${patientBean.patients}"/>

            <core:forEach items="${patientBean.patients}" var="patient">
                <tr>
                    <stripes:layout-render name="/webpages/component/detail/patient/row.jsp" record="${patient}"/>
                    <td class="action">
                        <div class="tableAction">
                            <stripes:link beanclass="cz.bbmri.action.patient.PatientActionBean" event="detail"
                                    class="btn btn-primary btnMargin">
                                <stripes:param name="biobankId" value="${patientBean.biobankId}"/>
                                <stripes:param name="patientId" value="${patient.id}"/>
                                <format:message key="detail"/>
                            </stripes:link>
                        </div>
                    </td>
                </tr>
            </core:forEach>

            </tbody>
        </table>


    </stripes:layout-component>
</stripes:layout-render>