<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<core:set var="patient" value="${actionBean.patient}"/>

<stripes:layout-render name="${component.layout.content}">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.patient}" active="detail"/>

        <table class="table table-bordered table-striped">
            <tr>
                <th width="30%"><format:message key="id"/></th>
                <td width="70%">${patient.id}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Patient.institutionalId"/></th>
                <td>${patient.institutionalId}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Patient.sex"/></th>
                <td><format:message key="cz.bbmri.entity.Sex.${patient.sex.name}"/></td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Patient.age"/></th>
                <td>${patient.age}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Patient.consent"/></th>
                <td>${patient.consent}</td>
            </tr>
        </table>

    </stripes:layout-component>
</stripes:layout-render>