<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="${component.layout.content}"
                       primarymenu="biobank"
                       secondarymenu="material">

    <stripes:layout-component name="body">

        <core:set var="biobanks" value="${actionBean.biobanks}"/>

        <table class="table table-hover table-striped">
            <thead>
            <th><format:message key="cz.bbmri.entity.MaterialType.name"/></th>
            <th><format:message key="cz.bbmri.entity.MaterialType.nameEN"/></th>
            <th><format:message key="cz.bbmri.entity.MaterialType.description"/></th>

            <core:forEach var="biobank" items="${biobanks}">
                <th>${biobank.acronym}</th>

            </core:forEach>
            </thead>

            <tbody>
            <core:forEach var="material" items="${actionBean.all}">
                <tr>
                    <td>${material.name}</td>
                    <td>${material.nameEnglish}</td>
                    <td>${material.description}</td>
                    <core:forEach var="biobank" items="${biobanks}">
                        <core:set target="${actionBean}" property="biobankId" value="${biobank.id}"/>
                        <core:set target="${actionBean}" property="materialTypeId" value="${material.id}"/>
                        <td>
                                ${actionBean.biobankMaterialType}
                        </td>
                    </core:forEach>
                </tr>
            </core:forEach>
            </tbody>
        </table>

    </stripes:layout-component>


</stripes:layout-render>
