<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="bbmri.action.biobank.BiobankActionBean.detail" var="title"/>
<s:useActionBean var="biobankBean" beanclass="bbmri.action.biobank.BiobankActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 ternarymenu="detail">

    <s:layout-component name="body">

        <c:set var="readonly" value="${true}"/>

        <security:allowed bean="biobankBean" event="edit">
            <c:set var="readonly" value="${false}"/>
        </security:allowed>

        <s:form beanclass="${biobankBean.name}" class="form-horizontal">

            <div class="control-group">
                <s:label for="name" class="control-label"/>
                <div class="controls">
                    <s:text id="name" name="biobank.name" readonly="${readonly}"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="address" class="control-label"/>
                <div class="controls">
                    <s:text id="address" name="biobank.address" readonly="${readonly}"/>
                </div>
            </div>

            <security:allowed bean="biobankBean" event="edit">
                <div class="form-actions">
                    <s:submit name="update" class="btn btn-primary">

                        <s:param name="id" value="${biobankBean.id}"/>
                    </s:submit>
                </div>
            </security:allowed>
        </s:form>


    </s:layout-component>

</s:layout-render>




