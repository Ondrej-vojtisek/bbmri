<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="patient"
                 ternarymenu="detail">

    <stripes:layout-component name="body">

        <%--TODO--%>
        <%--<div class="form-actions">--%>
            <%--<stripes:link beanclass="cz.bbmri.action.biobank.BiobankPatientsActionBean" event="display"--%>
                    <%--class="btn btn-primary btnMargin">--%>
                <%--<stripes:param name="biobankId" value="${patientBean.biobankId}"/>--%>
                <%--<format:message key="cz.bbmri.action.patient.PatientActionBean.backToBiobank"/>--%>
            <%--</stripes:link>--%>
        <%--</div>--%>

        <stripes:form beanclass="${actionBean.name}" class="form-horizontal">

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.Patient.institutionId" class="control-label"/>
                <div class="controls">
                    <stripes:text name="patient.institutionId" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.Patient.sex" class="control-label"/>
                <div class="controls">
                    <stripes:text name="patient.sex" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.Patient.age" class="control-label"/>
                <div class="controls">
                    <stripes:text name="patient.age" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.Patient.consent" class="control-label"/>
                <div class="controls">
                    <stripes:text name="patient.consent" readonly="true"/>
                </div>
            </div>

        </stripes:form>

    </stripes:layout-component>
</stripes:layout-render>