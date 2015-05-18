<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean beanclass="cz.bbmri.action.CountryActionBean" var="countryActionBean"/>

<core:set var="user" value="${actionBean.user}"/>

<stripes:layout-render name="${component.layout.content}"
                       primarymenu="user">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.user}" active="personal_data"/>

        <stripes:form beanclass="cz.bbmri.action.UserActionBean" class="form-horizontal">

            <stripes:hidden name="id"/>

            <table class="table table-bordered table-striped">
                <tr>
                    <th width="30%"><format:message key="id"/></th>
                    <td width="70%">${user.id}</td>
                </tr>
                <tr>
                    <th><format:message key="cz.bbmri.entity.User.wholeName"/></th>
                    <td>${user.wholeName}</td>
                </tr>
                <core:if test="${not empty actionBean.user.contact }">

                    <core:set var="contact" value="${user.contact}"/>

                    <tr>
                        <th><format:message key="cz.bbmri.entity.Contact.email"/></th>
                        <td>${contact.email}</td>
                    </tr>
                    <tr>
                        <th><format:message key="cz.bbmri.entity.Contact.phone"/></th>
                        <td>${contact.phone}</td>
                    </tr>
                    <tr>
                        <th><format:message key="cz.bbmri.entity.Contact.street"/></th>
                        <td>${contact.street}</td>
                    </tr>
                    <tr>
                        <th><format:message key="cz.bbmri.entity.Contact.city"/></th>
                        <td>${contact.city}</td>
                    </tr>
                    <tr>
                        <th><format:message key="cz.bbmri.entity.Contact.zip"/></th>
                        <td>${contact.zip}</td>
                    </tr>
                    <tr>
                        <th><format:message key="cz.bbmri.entity.Contact.zip"/></th>
                        <td>${contact.zip}</td>
                    </tr>
                    <tr>
                        <th><format:message key="cz.bbmri.entity.Contact.country"/></th>
                        <td>
                            <core:if test="${not empty contact.country}">
                                ${contact.country.name}
                            </core:if>
                        </td>
                    </tr>
                </core:if>

                <tr>
                    <th><format:message key="cz.bbmri.entity.User.created"/></th>
                    <td><format:formatDate value="${user.created}" type="both"/></td>
                </tr>
                <tr>
                    <th><format:message key="cz.bbmri.entity.User.lastLogin"/></th>
                    <td><format:formatDate value="${user.lastLogin}" type="both"/></td>
                </tr>

            </table>

            <div class="form-actions">
                <%--<security:allowed event="save">--%>
                    <%--<stripes:submit name="save" class="btn btn-primary btnMargin"/>--%>
                <%--</security:allowed>--%>

                <core:if test="${actionBean.user.notAuthorized}">
                    <stripes:submit name="authorize" class="btn btn-warning"/>
                </core:if>

            </div>

        </stripes:form>

    </stripes:layout-component>
</stripes:layout-render>