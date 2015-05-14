<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<core:set var="sample" value="${actionBean.sample}"/>

<stripes:layout-render name="${component.layout.content}">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.sample}" active="detail"/>

        <table class="table table-bordered table-striped">
            <tbody>
            <tr>
                <th width="30%"><format:message key="id"/></th>
                <td width="70%">${sample.id}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Sample.institutionalId"/></th>
                <td>${sample.institutionalId}</td>
            </tr>

            <tr>
                <th><format:message key="cz.bbmri.entity.Sample.takingDateTime"/></th>
                <td><format:formatDate value="${sample.takingDateTime}" type="both"/></td>
            </tr>

            <tr>
                <th><format:message key="cz.bbmri.entity.Sample.freezeDateTime"/></th>
                <td><format:formatDate value="${sample.freezeDateTime}" type="both"/></td>
            </tr>

            <tr>
                <th><format:message key="cz.bbmri.entity.MaterialType.materialType"/></th>
                <td>
                    <core:if test="${not empty sample.materialType}">
                        ${sample.materialType.name}
                    </core:if>
                </td>
            </tr>

            <tr>
                <th><format:message key="cz.bbmri.entity.Quantity.quantity"/></th>
                <td><core:if test="${not empty sample.quantity}">
                    ${sample.quantity}
                </core:if>
                </td>
            </tr>

            <tr>
                <th><format:message key="cz.bbmri.entity.Diagnosis.diagnosis"/></th>
                <td>${sample.diagnosisPrint}</td>
            </tr>

            <tr>
                <th><format:message key="cz.bbmri.entity.Biopticalreport.biopticalReport"/></th>
                <td><core:if test="${not empty sample.biopticalReport}">
                    ${sample.biopticalReport.year}/${sample.biopticalReport.number}
                </core:if>
                </td>
            </tr>

            <tr>
                <th><format:message key="cz.bbmri.entity.Retrieved.retrieved"/></th>
                <td>${sample.retrieved}</td>
            </tr>

            <tr>
                <th><format:message key="cz.bbmri.entity.Tnm.tnm"/></th>
                <td>${sample.tnm}</td>
            </tr>

            <tr>
                <th><format:message key="cz.bbmri.entity.Ptnm.ptnm"/></th>
                <td>${sample.ptnm}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Morphology.morphology"/></th>
                <td>${sample.morphology}</td>
            </tr>

            <core:if test="${not empty sample.storageMethodology}">
                <tr>
                    <th><format:message key="cz.bbmri.entity.StorageMethodology.methology"/></th>
                    <td>${sample.storageMethodology.methology}</td>
                </tr>

                <tr>
                    <th><format:message key="cz.bbmri.entity.StorageMethodology.temperatureCelsius"/></th>
                    <td>${sample.storageMethodology.temperatureCelsius}</td>
                </tr>

                <tr>
                    <th><format:message key="cz.bbmri.entity.StorageMethodology.sts"/></th>
                    <td>${sample.storageMethodology.sts}</td>
                </tr>

                <tr>
                    <th><format:message key="cz.bbmri.entity.StorageMethodology.expiration"/></th>
                    <td>${sample.storageMethodology.expiration}</td>
                </tr>

                <tr>
                    <th><format:message key="cz.bbmri.entity.StorageMethodology.medium"/></th>
                    <td>${sample.storageMethodology.medium}</td>
                </tr>
            </core:if>


            </tbody>
        </table>

    </stripes:layout-component>
</stripes:layout-render>