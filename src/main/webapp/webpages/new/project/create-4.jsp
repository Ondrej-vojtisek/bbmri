<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
                 <%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

                 <format:message key="cz.bbmri.action.ProjectCreateActionBean.create" var="title"/>

                 <stripes:layout-render name="${component.layout.content}" title="${title}"
                                  primarymenu="project">

    <stripes:layout-component name="body">

        <stripes:form beanclass="cz.bbmri.action.ProjectCreateActionBean">
            <fieldset>
                <legend><format:message key="cz.bbmri.action.ProjectCreateActionBean.fourthStep"/></legend>

                <stripes:label for="cz.bbmri.entity.Project.annotation"/>
                <stripes:textarea name="project.annotation"/>

                <div class="form-actions">
                    <stripes:submit name="fifth" class="btn btn-primary btnMargin"/>
                    <stripes:submit name="backToThird" class="btn btn-inverse"/>
                </div>
            </fieldset>
        </stripes:form>
    </stripes:layout-component>
</stripes:layout-render>