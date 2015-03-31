<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.ProjectCreateActionBean.create" var="title"/>

<s:layout-render name="${component.layout.content}" title="${title}"
                 primarymenu="project">

    <s:layout-component name="body">

        <s:form beanclass="cz.bbmri.action.ProjectCreateActionBean" class="form-horizontal">
            <fieldset>
                <legend><f:message key="cz.bbmri.action.ProjectCreateActionBean.thirdStep"/></legend>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Project.fundingOrganization" class="control-label"/>
                    <div class="controls">
                        <s:text name="project.fundingOrganization"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Project.approvedBy" class="control-label"/>
                    <div class="controls">
                        <s:text name="project.approvedBy"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Project.approvalDate" class="control-label"/>
                    <div class="controls">
                        <div class="input-append date" id="dp" data-date="${actionBean.today}" data-date-format="dd-mm-yyyy">
                            <s:text name="project.approvalDate" readonly="true" value=""/>
                            <span class="add-on"><i class="icon-calendar"></i></span>
                        </div>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Project.approvalStorage" class="control-label"/>
                    <div class="controls">
                        <s:text name="project.approvalStorage"/>
                    </div>
                </div>

                <div class="form-actions">
                    <s:submit name="fourth" class="btn btn-primary btnMargin"/>
                    <s:submit name="backToSecond" class="btn btn-inverse"/>
                </div>
            </fieldset>
        </s:form>
    </s:layout-component>

    <s:layout-component name="script">
        <script type="text/javascript">
            $(function () {
                $('#dp').datepicker();
            });
        </script>
    </s:layout-component>

</s:layout-render>