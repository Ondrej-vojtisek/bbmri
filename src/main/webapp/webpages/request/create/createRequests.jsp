<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.request.RequestActionBean.title" var="title"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 ternarymenu="">

    <s:layout-component name="body">

        <s:form beanclass="cz.bbmri.action.request.CreateRequestsActionBean" class="form-horizontal">

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Sample.sampleId" class="control-label"/>
                <div class="controls">
                    <s:text name="sample.sampleIdentification.sampleId"/>
                </div>
            </div>


            <div class="control-group">
                <s:label for="cz.bbmri.entities.Sample.year" class="control-label"/>
                <div class="controls">
                    <s:text name="sample.sampleIdentification.year"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Sample.number" class="control-label"/>
                <div class="controls">
                    <s:text name="sample.sampleIdentification.number"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Sample.retrieved" class="control-label"/>
                <div class="controls">
                    <s:select name="sample.retrieved">
                        <s:options-enumeration enum="cz.bbmri.entities.enumeration.Retrieved"/>
                    </s:select>
                </div>
            </div>


            <div class="control-group">
                <s:label for="cz.bbmri.entities.Module.moduleLTS" class="control-label"/>
                <div class="controls">
                    <s:checkbox name="moduleLTS"/>
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


            <div class="form-actions">
                <s:submit name="find" class="btn btn-primary btnMargin">
                    <s:param name="biobankId" value="${actionBean.biobankId}"/>
                    <s:param name="sampleQuestionId" value="${actionBean.sampleQuestionId}"/>
                </s:submit>
            </div>

        </s:form>
        <s:form beanclass="cz.bbmri.action.request.CreateRequestsActionBean">

            <table class="table table-hover table-striped">

                <s:layout-render name="/webpages/component/detail/sample/header.jsp"/>

                <tbody>

                <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                                 collection="${actionBean.samples}"/>

                <c:forEach items="${actionBean.samples}" var="sample">
                    <tr>
                        <s:layout-render name="/webpages/component/detail/sample/row.jsp" record="${sample}"/>
                        <td>
                            <s:checkbox name="selectedSamples" value="${sample.id}"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="form-actions">
                <s:submit name="confirmSelected" class="btn btn-primary">
                    <s:param name="sampleQuestionId" value="${actionBean.sampleQuestionId}"/>
                    <s:param name="biobankId" value="${actionBean.sampleQuestion.biobank.id}"/>
                </s:submit>
            </div>

        </s:form>
    </s:layout-component>
</s:layout-render>
