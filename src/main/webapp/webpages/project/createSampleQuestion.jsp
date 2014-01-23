<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.project.ProjectActionBean.createSampleQuestion" var="title"/>
<s:useActionBean var="projectBean" beanclass="cz.bbmri.action.project.ProjectActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 ternarymenu="">

    <s:layout-component name="body">

        <fieldset>
            <legend><f:message key="cz.bbmri.action.project.ProjectActionBean.createSampleQuestion.legend"/></legend>

            <s:form beanclass="cz.bbmri.action.project.ProjectActionBean" class="form-horizontal">

                <div class="control-group">
                    <s:label for="cz.bbmri.entities.SampleQuestion.biobank.create" class="control-label"/>
                    <div class="controls">
                        <s:select name="biobankId">
                            <s:options-collection collection="${projectBean.allBiobanks}"
                                                  label="name" value="id"/>
                        </s:select>
                    </div>
                </div>

                <s:label for="cz.bbmri.entities.SampleQuestion.specification.create"/>
                <s:textarea name="sampleQuestion.specification"/>

                <div class="form-actions">
                    <security:allowed bean="projectBean" event="detail">
                        <s:link beanclass="${projectBean.name}" event="detail" class="btn btn-inverse btnMargin">
                            <s:param name="id" value="${projectBean.id}"/>
                            <f:message key="cz.bbmri.action.project.ProjectActionBean.cancelSampleQuestion"/>
                        </s:link>
                    </security:allowed>

                    <security:allowed bean="projectBean" event="detail">
                        <s:submit name="confirmSampleQuestion" class="btn btn-primary">
                            <s:param name="id" value="${projectBean.id}"/>
                        </s:submit>
                    </security:allowed>
                </div>

            </s:form>

        </fieldset>


    </s:layout-component>
</s:layout-render>
