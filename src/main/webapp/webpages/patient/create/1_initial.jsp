<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="patients">

    <s:layout-component name="body">

        <s:form beanclass="cz.bbmri.action.patient.CreatePatientActionBean" class="form-horizontal">
            <fieldset>
                <legend><f:message key="cz.bbmri.action.patient.CreatePatientActionBean.addPatient"/></legend>

                <s:layout-render name="/webpages/patient/component/patientForm.jsp"/>

                <div class="form-actions">
                    <s:submit name="createPatient" class="btn btn-primary">
                        <s:param name="biobankId" value="${actionBean.biobankId}"/>
                    </s:submit>
                </div>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>