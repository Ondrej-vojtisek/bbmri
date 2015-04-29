<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean beanclass="cz.bbmri.action.CountryActionBean" var="countryActionBean"/>

<stripes:layout-render name="${component.layout.content}"
                       primarymenu="user">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.user}" active="personal_data"/>

        <stripes:form beanclass="cz.bbmri.action.UserActionBean" class="form-horizontal">

            <stripes:hidden name="id"/>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.User.wholeName" class="control-label">
                    <format:message key="cz.bbmri.entity.User.wholeName"/>
                </stripes:label>
                <div class="controls">
                    <stripes:text name="user.wholeName" readonly="true"/>
                </div>
            </div>

            <core:if test="${not empty actionBean.user.contact }">
                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Contact.email" class="control-label">
                        <format:message key="cz.bbmri.entity.Contact.email"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="contact.email"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Contact.phone" class="control-label">
                        <format:message key="cz.bbmri.entity.Contact.phone"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="contact.phone"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Contact.address" class="control-label">
                        <format:message key="cz.bbmri.entity.Contact.address"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="contact.address"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Contact.city" class="control-label">
                        <format:message key="cz.bbmri.entity.Contact.city"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="contact.city"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Contact.zip" class="control-label">
                        <format:message key="cz.bbmri.entity.Contact.zip"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="contact.zip"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Contact.country" class="control-label">
                        <format:message key="cz.bbmri.entity.Contact.country"/>
                    </stripes:label>
                    <div class="controls">

                        <core:if test="${empty actionBean.contact.country}">

                            <stripes:select name="countryId">
                                <stripes:option label="Not filled" value=""/>
                                <stripes:options-collection collection="${countryActionBean.all}" value="id"
                                                            label="name"/>
                            </stripes:select>

                        </core:if>

                        <core:if test="${not empty actionBean.contact.country}">
                            <stripes:text name="contact.country.name" readonly="true"/>
                        </core:if>

                    </div>
                </div>

            </core:if>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.User.created" class="control-label">
                    <format:message key="cz.bbmri.entity.User.created"/>
                </stripes:label>
                <div class="controls">
                    <stripes:text name="user.created" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.User.lastLogin" class="control-label">
                    <format:message key="cz.bbmri.entity.User.lastLogin"/>
                </stripes:label>
                <div class="controls">
                    <stripes:text name="user.lastLogin" readonly="true"/>
                </div>
            </div>

            <div class="form-actions">
                <security:allowed event="save">
                    <stripes:submit name="save" class="btn btn-primary btnMargin"/>
                </security:allowed>

            </div>

        </stripes:form>

    </stripes:layout-component>
</stripes:layout-render>