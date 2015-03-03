<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="project"
                 ternarymenu="">

    <s:layout-component name="body">

        <fieldset>
            <legend><f:message key="cz.bbmri.action.request.CreateSampleQuestion.createSampleRequest"/></legend>

            <s:form beanclass="cz.bbmri.action.request.CreateSampleQuestion" class="form-horizontal">

                <div class="control-group">
                    <s:label for="cz.bbmri.entities.SampleQuestion.biobank.create" class="control-label"/>

                    <div class="controls">
                        <s:select name="biobankId">
                            <s:options-collection collection="${actionBean.allBiobanks}"
                                                  label="name" value="id"/>
                        </s:select>
                    </div>
                </div>

                <s:hidden name="projectId" value="${actionBean.projectId}"/>

                <s:label for="cz.bbmri.entities.SampleQuestion.specification.create"/>
                <s:textarea name="specification"/>

                <div class="form-actions">
                    <s:link beanclass="cz.bbmri.action.project.ProjectActionBean"
                            event="detail" class="btn btn-inverse btnMargin">
                        <s:param name="projectId" value="${actionBean.projectId}"/>
                        <f:message key="cancel"/>
                    </s:link>

                    <security:allowed event="confirmSampleRequest">
                        <s:submit name="confirmSampleRequest" class="btn btn-primary"/>
                    </security:allowed>
                </div>
            </s:form>

        </fieldset>


    </s:layout-component>
</s:layout-render>
