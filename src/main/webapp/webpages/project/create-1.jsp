<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
                 <%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

                 <format:message key="cz.bbmri.action.ProjectCreateActionBean.create" var="title"/>

                 <stripes:layout-render name="${component.layout.content}" title="${title}"
                                  primarymenu="project">

    <stripes:layout-component name="body">
        <stripes:form beanclass="cz.bbmri.action.ProjectCreateActionBean">
            <fieldset>
                <legend><format:message key="cz.bbmri.action.ProjectCreateActionBean.firstStep"/></legend>
                <li><format:message key="cz.bbmri.action.ProjectCreateActionBean.project_upload_information_1"/></li>
                <li><format:message key="cz.bbmri.action.ProjectCreateActionBean.project_upload_information_2"/></li>
                <li><format:message key="cz.bbmri.action.ProjectCreateActionBean.project_upload_information_3"/></li>
                <br>
            </fieldset>
            <br>

            <div class="form-actions">
                <stripes:link href="/data/MTA_BBMRICZ.doc" target="blank" class="btn btn-info btnMargin">
                    <format:message key="download_mta_form"/>
                </stripes:link>
                <stripes:submit name="second" class="btn btn-primary"/>
            </div>
        </stripes:form>
    </stripes:layout-component>
</stripes:layout-render>
