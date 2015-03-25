<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="user"
                 ternarymenu="shibboleth">

    <s:layout-component name="body">

        <s:form beanclass="cz.bbmri.action.UserActionBean" class="form-horizontal">

            <c:if test="${not empty actionBean.user.shibboleth}">
                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Shibboleth.eppn" class="control-label"/>
                    <div class="controls">
                        <s:text name="user.shibboleth.eppn" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Shibboleth.affiliation" class="control-label"/>
                    <div class="controls">
                        <s:text name="user.shibboleth.affiliation" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Shibboleth.targeted" class="control-label"/>
                    <div class="controls">
                        <s:text name="user.shibboleth.targeted" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Shibboleth.persistent" class="control-label"/>
                    <div class="controls">
                        <s:text name="user.shibboleth.persistent" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Shibboleth.name" class="control-label"/>
                    <div class="controls">
                        <s:text name="user.shibboleth.name" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Shibboleth.surname" class="control-label"/>
                    <div class="controls">
                        <s:text name="user.shibboleth.surname" readonly="true"/>
                    </div>
                </div>


                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Shibboleth.email" class="control-label"/>
                    <div class="controls">
                        <s:text name="user.shibboleth.email" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Shibboleth.organization" class="control-label"/>
                    <div class="controls">
                        <s:text name="user.shibboleth.organization" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Shibboleth.displayName" class="control-label"/>
                    <div class="controls">
                        <s:text name="user.shibboleth.displayName" readonly="true"/>
                    </div>
                </div>

            </c:if>

        </s:form>

    </s:layout-component>
</s:layout-render>