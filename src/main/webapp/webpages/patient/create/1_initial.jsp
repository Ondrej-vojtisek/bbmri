<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="patients">

    <stripes:layout-component name="body">

        <stripes:form beanclass="cz.bbmri.action.patient.CreatePatientActionBean" class="form-horizontal">
            <fieldset>
                <legend><format:message key="cz.bbmri.action.patient.CreatePatientActionBean.addPatient"/></legend>

                <stripes:layout-render name="/webpages/patient/component/patientForm.jsp"/>

                <div class="form-actions">
                    <stripes:submit name="createPatient" class="btn btn-primary">
                        <stripes:param name="biobankId" value="${actionBean.biobankId}"/>
                    </stripes:submit>
                </div>
            </fieldset>
        </stripes:form>
    </stripes:layout-component>
</stripes:layout-render>