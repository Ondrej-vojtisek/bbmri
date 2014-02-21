<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <div class="control-group">
        <s:label for="cz.bbmri.entities.Patient.institutionId" class="control-label"/>
        <div class="controls">
            <s:text name="patient.institutionId"/>
        </div>
    </div>

    <div class="control-group">
        <s:label for="cz.bbmri.entities.Patient.consent" class="control-label"/>
        <div class="controls">
            <s:checkbox name="patient.consent"/>
        </div>
    </div>

    <div class="control-group">
        <s:label for="cz.bbmri.entities.Patient.sex" class="control-label"/>
        <div class="controls">
            <s:select name="patient.sex">
                <s:options-enumeration enum="cz.bbmri.entities.enumeration.Sex"/>
            </s:select>
        </div>
    </div>

    <div class="control-group">
        <s:label for="cz.bbmri.entities.Patient.birthYear" class="control-label"/>
        <div class="controls">
            <s:text name="patient.birthYear"/>
        </div>
    </div>

    <div class="control-group">
        <s:label for="cz.bbmri.entities.Patient.birthMonth" class="control-label"/>
        <div class="controls">
            <s:text name="patient.birthMonth"/>
        </div>
    </div>

</s:layout-definition>