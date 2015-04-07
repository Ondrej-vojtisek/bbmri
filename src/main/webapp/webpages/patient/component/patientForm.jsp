<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entities.Patient.institutionId" class="control-label"/>
        <div class="controls">
            <stripes:text name="patient.institutionId"/>
        </div>
    </div>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entities.Patient.consent" class="control-label"/>
        <div class="controls">
            <stripes:checkbox name="patient.consent"/>
        </div>
    </div>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entities.Patient.sex" class="control-label"/>
        <div class="controls">
            <stripes:select name="patient.sex">
                <stripes:options-enumeration enum="cz.bbmri.entity.enumeration.Sex"/>
            </stripes:select>
        </div>
    </div>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entities.Patient.birthYear" class="control-label"/>
        <div class="controls">
            <stripes:text name="patient.birthYear"/>
        </div>
    </div>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entities.Patient.birthMonth" class="control-label"/>
        <div class="controls">
            <stripes:text name="patient.birthMonth"/>
        </div>
    </div>

</stripes:layout-definition>