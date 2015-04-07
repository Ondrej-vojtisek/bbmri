<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<format:message key="cz.bbmri.action.request.RequestActionBean.title" var="title"/>

<stripes:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 ternarymenu="">

    <stripes:layout-component name="body">

        <stripes:form beanclass="cz.bbmri.action.request.CreateRequestsActionBean" class="form-horizontal">

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.Sample.sampleId" class="control-label"/>
                <div class="controls">
                    <stripes:text name="sample.sampleIdentification.sampleId"/>
                </div>
            </div>


            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.Sample.year" class="control-label"/>
                <div class="controls">
                    <stripes:text name="sample.sampleIdentification.year"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.Sample.number" class="control-label"/>
                <div class="controls">
                    <stripes:text name="sample.sampleIdentification.number"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.Sample.retrieved" class="control-label"/>
                <div class="controls">
                    <stripes:select name="sample.retrieved">
                        <stripes:options-enumeration enum="cz.bbmri.entity.enumeration.Retrieved"/>
                    </stripes:select>
                </div>
            </div>


            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.Module.moduleLTS" class="control-label"/>
                <div class="controls">
                    <stripes:checkbox name="moduleLTS"/>
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


            <div class="form-actions">
                <stripes:submit name="find" class="btn btn-primary btnMargin">
                    <stripes:param name="biobankId" value="${actionBean.biobankId}"/>
                    <stripes:param name="sampleQuestionId" value="${actionBean.sampleQuestionId}"/>
                </stripes:submit>
            </div>

        </stripes:form>
        <stripes:form beanclass="cz.bbmri.action.request.CreateRequestsActionBean">

            <table class="table table-hover table-striped">

                <stripes:layout-render name="/webpages/component/detail/sample/header.jsp"/>

                <tbody>

                <stripes:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                                 collection="${actionBean.samples}"/>

                <core:forEach items="${actionBean.samples}" var="sample">
                    <tr>
                        <stripes:layout-render name="/webpages/component/detail/sample/row.jsp" record="${sample}"/>
                        <td>
                            <stripes:checkbox name="selectedSamples" value="${sample.id}"/>
                        </td>
                    </tr>
                </core:forEach>
                </tbody>
            </table>

            <div class="form-actions">
                <stripes:submit name="confirmSelected" class="btn btn-primary">
                    <stripes:param name="sampleQuestionId" value="${actionBean.sampleQuestionId}"/>
                    <stripes:param name="biobankId" value="${actionBean.sampleQuestion.biobank.id}"/>
                </stripes:submit>
            </div>

        </stripes:form>
    </stripes:layout-component>
</stripes:layout-render>
