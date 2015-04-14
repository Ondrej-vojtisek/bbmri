<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean beanclass="cz.bbmri.action.PermissionActionBean" var="permissionActionBean"/>
<stripes:useActionBean beanclass="cz.bbmri.action.BiobankUserActionBean" var="biobankUserActionBean"/>

<stripes:layout-render name="${component.layout.content}"
                       primarymenu="biobank">

    <stripes:layout-component name="body">

        <%--Set authProjectId of AuthotizationActionBean to enable security tag--%>
        <core:set target="${biobankUserActionBean}" property="authBiobankId" value="${actionBean.biobankId}"/>

        <stripes:form beanclass="cz.bbmri.action.BiobankUserActionBean" class="form-horizontal">
            <format:message key="cz.bbmri.entity.User.findUser"/>

            <stripes:hidden name="biobankId" value="${actionBean.biobankId}"/>

            <div class="control-group">
                <stripes:label name="cz.bbmri.entity.User.findCriteria" class="control-label"/>
                <div class="controls">
                    <stripes:text class="form-control" name="find"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label name="cz.bbmri.entity.Permission" class="control-label"/>
                <div class="controls">
                    <stripes:select name="permissionId" value="4" class="form-control">
                        <stripes:options-collection collection="${permissionActionBean.all}" value="id"
                                                    label="name"/>
                    </stripes:select>
                </div>
            </div>

            <div class="form-actions">
                <stripes:submit name="confirmAdd" class="btn btn-primary btnMargin"/>

                <stripes:link name="biobankuser" beanclass="cz.bbmri.action.BiobankActionBean"
                              class="btn btn-primary btnMargin">
                    <stripes:param name="id" value="${actionBean.biobankId}"/>
                    <format:message key="cancel"/>
                </stripes:link>
            </div>

        </stripes:form>

    </stripes:layout-component>
</stripes:layout-render>