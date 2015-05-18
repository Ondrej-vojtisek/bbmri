<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean beanclass="cz.bbmri.action.CountryActionBean" var="countryActionBean"/>
<core:set var="biobank" value="${actionBean.biobank}"/>

<stripes:layout-render name="${component.layout.content}"
                       primarymenu="biobank">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.biobank}" active="detail"/>

        <%--<stripes:form beanclass="cz.bbmri.action.BiobankActionBean" class="form-horizontal">--%>

        <%--<stripes:hidden name="id"/>--%>

        <table class="table table-bordered table-striped">
            <tr>
                <th width="30%"><format:message key="id"/></th>
                <td width="70%">${biobank.id}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Biobank.acronym"/></th>
                <td>${biobank.acronym}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Biobank.acronymEn"/></th>
                <td>${biobank.acronymEn}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Biobank.name"/></th>
                <td>${biobank.name}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Biobank.nameEnglish"/></th>
                <td>${biobank.nameEnglish}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Biobank.institutionalId"/></th>
                <td>${biobank.institutionalId}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Biobank.juridicalPerson"/></th>
                <td>${biobank.juridicalPerson}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Biobank.juridicalPersonEn"/></th>
                <td>${biobank.juridicalPersonEn}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Biobank.description"/></th>
                <td>${biobank.description}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Biobank.patientCount"/></th>
                <td>${biobank.patientCount}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Biobank.sampleCount"/></th>
                <td>${actionBean.sampleCount}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Biobank.totalAliquotesCount"/></th>
                <td>${actionBean.aliquotesTotalCount}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Biobank.availableAliquotesCount"/></th>
                <td>${actionBean.aliquotesAvailableCount}</td>
            </tr>


            <core:if test="${not empty biobank.contact}">
                <tr>
                    <th><format:message key="cz.bbmri.entity.Contact.street"/></th>
                    <td>${biobank.contact.street}</td>
                </tr>
                <tr>
                    <th><format:message key="cz.bbmri.entity.Contact.zip"/></th>
                    <td>${biobank.contact.zip}</td>
                </tr>
                <tr>
                    <th><format:message key="cz.bbmri.entity.Contact.city"/></th>
                    <td>${biobank.contact.city}</td>
                </tr>
                <tr>
                    <th><format:message key="cz.bbmri.entity.Contact.country"/></th>
                    <td><core:if test="${not empty biobank.contact.country}">
                        ${biobank.contact.country.name}
                    </core:if></td>
                </tr>

                <tr>
                    <th><format:message key="cz.bbmri.entity.Contact.contactPerson"/></th>
                    <td>${biobank.contact.firstName}&nbsp;${biobank.contact.lastName}</td>
                </tr>

                <tr>
                    <th><format:message key="cz.bbmri.entity.Contact.url"/></th>
                    <td><a href="${biobank.contact.url}">${biobank.contact.url}</a></td>
                </tr>

                <tr>
                    <th><format:message key="cz.bbmri.entity.Contact.email"/></th>
                    <td>${biobank.contact.email}</td>
                </tr>

                <tr>
                    <th><format:message key="cz.bbmri.entity.Contact.phone"/></th>
                    <td>${biobank.contact.phone}</td>
                </tr>

                <tr>
                    <th><format:message key="cz.bbmri.entity.Contact.gps"/></th>
                    <td><format:message key="cz.bbmri.entity.Contact.latitude"/>:${biobank.contact.latitude}
                        &nbsp;
                        <format:message key="cz.bbmri.entity.Contact.longitude"/>:${biobank.contact.longitude}
                    </td>
                </tr>

            </core:if>

            </tbody>
        </table>


        <%--<div class="form-actions">--%>
        <%--<security:allowed event="save">--%>
        <%--<stripes:submit name="save" class="btn btn-primary btnMargin"/>--%>
        <%--</security:allowed>--%>

        <%--</div>--%>

        <%--</stripes:form>--%>

    </stripes:layout-component>
</stripes:layout-render>