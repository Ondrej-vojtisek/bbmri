<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean beanclass="cz.bbmri.action.CountryActionBean" var="countryActionBean"/>

<s:layout-render name="${component.layout.content}"
                 primarymenu="biobank">

    <s:layout-component name="body">

        <s:layout-render name="${component.menu.biobank}" active="detail"/>

        <s:form beanclass="cz.bbmri.action.BiobankActionBean" class="form-horizontal">

            <s:hidden name="id"/>

            <div class="control-group">
                <s:label for="cz.bbmri.entity.Biobank.acronym" class="control-label"/>
                <div class="controls">
                    <s:text name="biobank.acronym" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entity.Biobank.name" class="control-label"/>
                <div class="controls">
                    <s:text name="biobank.name" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entity.Biobank.nameEnglish" class="control-label"/>
                <div class="controls">
                    <s:text name="biobank.nameEnglish" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entity.Biobank.institutionalId" class="control-label"/>
                <div class="controls">
                    <s:text name="biobank.institutionalId" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entity.Biobank.description" class="control-label"/>
                <div class="controls">
                    <s:text name="biobank.description"/>
                </div>
            </div>

            <c:if test="${not empty actionBean.biobank.contact }">
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

            <div class="form-actions">
                <security:allowed event="save">
                    <s:submit name="save" class="btn btn-primary btnMargin"/>
                </security:allowed>

            </div>

        </s:form>

    </s:layout-component>
</s:layout-render>