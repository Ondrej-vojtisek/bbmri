<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
                 <%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

                 <f:message key="cz.bbmri.action.ProjectCreateActionBean.create" var="title"/>

                 <s:layout-render name="${component.layout.content}" title="${title}"
                                  primarymenu="project">

    <s:layout-component name="body">
        <s:form beanclass="cz.bbmri.action.ProjectCreateActionBean">
            <fieldset>
                <legend><f:message key="cz.bbmri.action.ProjectCreateActionBean.firstStep"/></legend>
                <li><f:message key="cz.bbmri.action.ProjectCreateActionBean.project_upload_information_1"/></li>
                <li><f:message key="cz.bbmri.action.ProjectCreateActionBean.project_upload_information_2"/></li>
                <li><f:message key="cz.bbmri.action.ProjectCreateActionBean.project_upload_information_3"/></li>
                <br>
            </fieldset>
            <br>

            <div class="form-actions">
                <s:link href="/data/MTA_BBMRICZ.doc" target="blank" class="btn btn-info btnMargin">
                    <f:message key="download_mta_form"/>
                </s:link>
                <s:submit name="second" class="btn btn-primary"/>
            </div>
        </s:form>
    </s:layout-component>
</s:layout-render>
