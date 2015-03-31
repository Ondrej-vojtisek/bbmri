<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
                 <%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

                 <f:message key="cz.bbmri.action.ProjectCreateActionBean.create" var="title"/>

                 <s:layout-render name="${component.layout.content}" title="${title}"
                                  primarymenu="project">

    <s:layout-component name="body">

        <s:form beanclass="cz.bbmri.action.ProjectCreateActionBean">
            <fieldset>
                <legend><f:message key="cz.bbmri.action.ProjectCreateActionBean.fourthStep"/></legend>

                <s:label for="cz.bbmri.entity.Project.annotation"/>
                <s:textarea name="project.annotation"/>

                <div class="form-actions">
                    <s:submit name="fifth" class="btn btn-primary btnMargin"/>
                    <s:submit name="backToThird" class="btn btn-inverse"/>
                </div>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>