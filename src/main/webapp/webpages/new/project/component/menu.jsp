<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="projectActionBean" beanclass="cz.bbmri.action.ProjectActionBean"/>

<s:layout-definition>

    <ul class="nav nav-tabs">

            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="projectActionBean" event="detail">
            <li <c:if test="${active == 'detail'}"> class="active" </c:if> >
                <s:link beanclass="cz.bbmri.action.ProjectActionBean" event="detail">
                    <s:param name="id" value="${actionBean.id}"/>
                    <f:message key="cz.bbmri.action.project.ProjectActionBean.basicData"/>
                </s:link>
            </li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>
        <security:allowed bean="projectActionBean" event="projectuser">
            <li <c:if test="${active == 'administrators'}"> class="active" </c:if>>
                <s:link beanclass="cz.bbmri.action.ProjectActionBean"
                        event="projectuser">
                    <s:param name="id" value="${actionBean.id}"/>
                    <f:message key="cz.bbmri.entity.ProjectUser.projectUsers"/>
                </s:link>
            </li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="projectActionBean" event="attachments">
            <li <c:if test="${active == 'attachments'}"> class="active" </c:if>>
                <s:link beanclass="cz.bbmri.action.ProjectActionBean" event="attachments">
                    <s:param name="id" value="${actionBean.id}"/>
                    <f:message key="cz.bbmri.entity.Attachment.attachments"/>
                </s:link>
            </li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="projectActionBean" event="questions">
            <li <c:if test="${active == 'questions'}"> class="active" </c:if>>
                <s:link beanclass="cz.bbmri.action.ProjectActionBean" event="questions">
                    <s:param name="id" value="${actionBean.id}"/>
                    <f:message key="cz.bbmri.entity.Question.questions"/>
                </s:link>
            </li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>

    </ul>

</s:layout-definition>