<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="project"
                 ternarymenu="">

    <stripes:layout-component name="body">

        <fieldset>
            <legend><format:message key="cz.bbmri.action.request.CreateSampleQuestion.createSampleRequest"/></legend>

            <stripes:form beanclass="cz.bbmri.action.request.CreateSampleQuestion" class="form-horizontal">

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.SampleQuestion.biobank.create" class="control-label"/>

                    <div class="controls">
                        <stripes:select name="biobankId">
                            <stripes:options-collection collection="${actionBean.allBiobanks}"
                                                  label="name" value="id"/>
                        </stripes:select>
                    </div>
                </div>

                <stripes:hidden name="projectId" value="${actionBean.projectId}"/>

                <stripes:label for="cz.bbmri.entities.SampleQuestion.specification.create"/>
                <stripes:textarea name="specification"/>

                <div class="form-actions">
                    <stripes:link beanclass="cz.bbmri.action.project.ProjectActionBean"
                            event="detail" class="btn btn-inverse btnMargin">
                        <stripes:param name="projectId" value="${actionBean.projectId}"/>
                        <format:message key="cancel"/>
                    </stripes:link>

                    <security:allowed event="confirmSampleRequest">
                        <stripes:submit name="confirmSampleRequest" class="btn btn-primary"/>
                    </security:allowed>
                </div>
            </stripes:form>

        </fieldset>


    </stripes:layout-component>
</stripes:layout-render>
