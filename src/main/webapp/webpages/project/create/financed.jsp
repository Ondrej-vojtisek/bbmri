<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="bbmri.action.project.ProjectActionBean.create" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.project.CreateProjectActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_create_project">

    <s:layout-component name="body">


        <s:form beanclass="bbmri.action.project.CreateProjectActionBean" method="GET">
            <s:submit name="generalBack" class="btn btn-inverse"/>
        </s:form>

        <s:form beanclass="bbmri.action.project.CreateProjectActionBean">
            <fieldset>


                <legend><f:message key="bbmri.action.project.CreateProjectActionBean.thirdStep"/></legend>
                <table>
                    <tr>
                        <th><s:label for="z2" name="project.fundingOrganization"/></th>
                        <td><s:text id="z2" name="project.fundingOrganization"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z3" name="project.approvedBy"/></th>
                        <td><s:text id="z3" name="project.approvedBy"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z4" name="project.approvalDate"/></th>
                        <td>
                            <%--<s:text id="z4" name="project.created"/>--%>

                            <div class="input-append date" id="dp" data-date="1-1-2014" data-date-format="dd-mm-yyyy">
                                <s:text id="z4" name="project.approvalDate" readonly="true" value=""/>
                                <%--<input class="span2" size="16" type="text" value="12-02-2012" readonly="">--%>
                            	    <span class="add-on"><i class="icon-calendar"></i></span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th><s:label for="z6" name="project.approvalStorage"/></th>
                        <td><s:text id="z6" name="project.approvalStorage"/></td>
                    </tr>
                </table>

                <s:submit name="financedConfirm" class="btn btn-primary"/>
            </fieldset>
        </s:form>
    </s:layout-component>

    <s:layout-component name="jsLibrary">
        <script type="text/javascript" src="${context}/libs/bootstrap-datepicker.js"></script>
    </s:layout-component>

    <s:layout-component name="script">
        <script type="text/javascript">
            $(function(){
            			$('#dp').datepicker();
            });
        </script>
    </s:layout-component>

</s:layout-render>