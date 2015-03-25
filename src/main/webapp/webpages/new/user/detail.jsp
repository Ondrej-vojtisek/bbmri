<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean beanclass="cz.bbmri.action.CountryActionBean" var="countryActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="user"
                 ternarymenu="personal_data">

    <s:layout-component name="body">

        <s:form beanclass="cz.bbmri.action.UserActionBean" class="form-horizontal">

            <s:hidden name="id"/>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.User.wholeName" class="control-label"/>
                <div class="controls">
                    <s:text name="user.wholeName" readonly="true"/>
                </div>
            </div>

            <c:if test="${not empty actionBean.user.contact }">
                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Contact.email" class="control-label"/>
                    <div class="controls">
                        <s:text name="contact.email"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Contact.phone" class="control-label"/>
                    <div class="controls">
                        <s:text name="contact.phone"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Contact.address" class="control-label"/>
                    <div class="controls">
                        <s:text name="contact.address"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Contact.city" class="control-label"/>
                    <div class="controls">
                        <s:text name="contact.city"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Contact.zip" class="control-label"/>
                    <div class="controls">
                        <s:text name="contact.zip"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Contact.country" class="control-label"/>
                    <div class="controls">

                        <c:if test="${empty actionBean.contact.country}">

                            <s:select name="countryId">
                                <s:option label="Not filled" value=""/>
                                <s:options-collection collection="${countryActionBean.all}" value="id"
                                                      label="name"/>
                            </s:select>

                        </c:if>

                        <c:if test="${not empty actionBean.contact.country}">
                            <s:text name="contact.country.name" readonly="true"/>
                        </c:if>

                    </div>
                </div>

            </c:if>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.User.created" class="control-label"/>
                <div class="controls">
                    <s:text name="user.created" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.User.lastLogin" class="control-label"/>
                <div class="controls">
                    <s:text name="user.lastLogin" readonly="true"/>
                </div>
            </div>

            <div class="form-actions">
                <security:allowed event="save">
                    <s:submit name="save" class="btn btn-primary btnMargin"/>
                </security:allowed>

            </div>

        </s:form>

    </s:layout-component>
</s:layout-render>