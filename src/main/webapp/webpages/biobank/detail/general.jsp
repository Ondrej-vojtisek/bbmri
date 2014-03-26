<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<%--<f:message key="cz.bbmri.action.biobank.BiobankActionBean.detail" var="title"/>--%>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="detail">

    <s:layout-component name="body">

        <c:set var="readonly" value="${true}"/>

        <security:allowed event="update">
            <c:set var="readonly" value="${false}"/>
        </security:allowed>

        <s:form beanclass="${actionBean.name}" class="form-horizontal">


            <div class="control-group">
                <s:label for="cz.bbmri.entities.Biobank.abbreviation" class="control-label"/>
                <div class="controls">
                    <s:text name="biobank.abbreviation" readonly="${readonly}"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Biobank.name" class="control-label"/>
                <div class="controls">
                    <s:text name="biobank.name" readonly="${readonly}"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Biobank.street" class="control-label"/>
                <div class="controls">
                    <s:text name="biobank.street" readonly="${readonly}"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Biobank.city" class="control-label"/>
                <div class="controls">
                    <s:text name="biobank.city" readonly="${readonly}"/>
                </div>
            </div>

            <security:allowed event="update">
                <div class="form-actions">
                    <s:submit name="update" class="btn btn-primary">

                        <s:param name="biobankId" value="${actionBean.biobankId}"/>
                    </s:submit>
                </div>
            </security:allowed>
        </s:form>


    </s:layout-component>

</s:layout-render>




