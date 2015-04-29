<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<stripes:layout-render name="${component.layout.content}"
                       primarymenu="user">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.user}" active="shibboleth"/>

        <stripes:form beanclass="cz.bbmri.action.UserActionBean" class="form-horizontal">

            <core:if test="${not empty actionBean.user.shibboleth}">
                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Shibboleth.eppn" class="control-label">
                        <format:message key="cz.bbmri.entity.Shibboleth.eppn"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="user.shibboleth.eppn" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Shibboleth.affiliation" class="control-label">
                        <format:message key="cz.bbmri.entity.Shibboleth.affiliation"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="user.shibboleth.affiliation" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Shibboleth.targeted" class="control-label">
                        <format:message key="cz.bbmri.entity.Shibboleth.targeted"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="user.shibboleth.targeted" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Shibboleth.persistent" class="control-label">
                        <format:message key="cz.bbmri.entity.Shibboleth.persistent"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="user.shibboleth.persistent" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Shibboleth.name" class="control-label">
                        <format:message key="cz.bbmri.entity.Shibboleth.name"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="user.shibboleth.name" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Shibboleth.surname" class="control-label">
                        <format:message key="cz.bbmri.entity.Shibboleth.surname"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="user.shibboleth.surname" readonly="true"/>
                    </div>
                </div>


                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Shibboleth.email" class="control-label">
                        <format:message key="cz.bbmri.entity.Shibboleth.email"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="user.shibboleth.email" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Shibboleth.organization" class="control-label">
                        <format:message key="cz.bbmri.entity.Shibboleth.organization"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="user.shibboleth.organization" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Shibboleth.displayName" class="control-label">
                        <format:message key="cz.bbmri.entity.Shibboleth.displayName"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="user.shibboleth.displayName" readonly="true"/>
                    </div>
                </div>

            </core:if>

        </stripes:form>

    </stripes:layout-component>
</stripes:layout-render>