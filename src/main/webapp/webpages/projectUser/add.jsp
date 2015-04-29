<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean beanclass="cz.bbmri.action.PermissionActionBean" var="permissionActionBean"/>
<stripes:useActionBean beanclass="cz.bbmri.action.ProjectUserActionBean" var="projectUserActionBean"/>

<stripes:layout-render name="${component.layout.content}"
                       primarymenu="project">

    <stripes:layout-component name="body">

        <%--Set authProjectId of AuthotizationActionBean to enable security tag--%>
        <core:set target="${projectUserActionBean}" property="authProjectId" value="${actionBean.projectId}"/>

        <stripes:form beanclass="cz.bbmri.action.ProjectUserActionBean" class="form-horizontal">
            <format:message key="cz.bbmri.entity.User.findUser"/>

            <stripes:hidden name="projectId" value="${actionBean.projectId}"/>

            <div class="control-group">
                <stripes:label name="cz.bbmri.entity.User.findCriteria" class="control-label">
                    <format:message key="cz.bbmri.entity.User.findCriteria"/>
                </stripes:label>
                <div class="controls">
                    <stripes:text class="form-control" name="find"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label name="cz.bbmri.entity.Permission" class="control-label">
                    <format:message key="cz.bbmri.entity.Permission"/>
                </stripes:label>
                <div class="controls">
                    <stripes:select name="permissionId" value="4" class="form-control">
                        <stripes:options-collection collection="${permissionActionBean.all}" value="id"
                                                    label="name"/>
                    </stripes:select>
                </div>
            </div>

            <div class="form-actions">
                <stripes:submit name="confirmAdd" class="btn btn-primary btnMargin"/>

                <stripes:link name="projectuser" beanclass="cz.bbmri.action.ProjectActionBean"
                              class="btn btn-primary btnMargin">
                    <stripes:param name="id" value="${actionBean.projectId}"/>
                    <format:message key="cancel"/>
                </stripes:link>
            </div>

        </stripes:form>

    </stripes:layout-component>
</stripes:layout-render>