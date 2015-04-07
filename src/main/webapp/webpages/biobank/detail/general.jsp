<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="detail">

    <stripes:layout-component name="body">

        <core:set var="readonly" value="${true}"/>

        <security:allowed event="update">
            <core:set var="readonly" value="${false}"/>
        </security:allowed>

        <stripes:form beanclass="${actionBean.name}" class="form-horizontal">


            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.Biobank.abbreviation" class="control-label"/>
                <div class="controls">
                    <stripes:text name="biobank.abbreviation" readonly="${readonly}"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.Biobank.name" class="control-label"/>
                <div class="controls">
                    <stripes:text name="biobank.name" readonly="${readonly}"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.Biobank.street" class="control-label"/>
                <div class="controls">
                    <stripes:text name="biobank.street" readonly="${readonly}"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.Biobank.city" class="control-label"/>
                <div class="controls">
                    <stripes:text name="biobank.city" readonly="${readonly}"/>
                </div>
            </div>

            <security:allowed event="update">
                <div class="form-actions">
                    <stripes:submit name="update" class="btn btn-primary">

                        <stripes:param name="biobankId" value="${actionBean.biobankId}"/>
                    </stripes:submit>
                </div>
            </security:allowed>
        </stripes:form>


    </stripes:layout-component>

</stripes:layout-render>




