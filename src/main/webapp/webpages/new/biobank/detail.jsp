<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean beanclass="cz.bbmri.action.CountryActionBean" var="countryActionBean"/>

<stripes:layout-render name="${component.layout.content}"
                       primarymenu="biobank">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.biobank}" active="detail"/>

        <stripes:form beanclass="cz.bbmri.action.BiobankActionBean" class="form-horizontal">

            <stripes:hidden name="id"/>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entity.Biobank.acronym" class="control-label"/>
                <div class="controls">
                    <stripes:text name="biobank.acronym" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entity.Biobank.name" class="control-label"/>
                <div class="controls">
                    <stripes:text name="biobank.name" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entity.Biobank.nameEnglish" class="control-label"/>
                <div class="controls">
                    <stripes:text name="biobank.nameEnglish" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entity.Biobank.institutionalId" class="control-label"/>
                <div class="controls">
                    <stripes:text name="biobank.institutionalId" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entity.Biobank.description" class="control-label"/>
                <div class="controls">
                    <stripes:text name="biobank.description"/>
                </div>
            </div>

            <core:if test="${not empty actionBean.biobank.contact }">
                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Contact.email" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="contact.email"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Contact.phone" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="contact.phone"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Contact.address" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="contact.address"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Contact.city" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="contact.city"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Contact.zip" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="contact.zip"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Contact.url" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="contact.url"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Contact.country" class="control-label"/>
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

            <div class="form-actions">
                <security:allowed event="save">
                    <stripes:submit name="save" class="btn btn-primary btnMargin"/>
                </security:allowed>

            </div>

        </stripes:form>

    </stripes:layout-component>
</stripes:layout-render>