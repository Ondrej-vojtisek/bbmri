<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="biobankBean" beanclass="bbmri.action.biobank.BiobankActionBean"/>

<%------------------------------------------------------------------------%>


<security:allowed bean="biobankBean" event="allBiobanks">
    <li
    <c:if test="${secondarymenu == 'biobank_all'}"> class="active" </c:if> ><s:link
        beanclass="${biobankBean.name}" event="allBiobanks"><f:message
        key="biobanks_all"/></s:link>
    </li>
</security:allowed>

<%------------------------------------------------------------------------%>

<%--There can't be used createActionBean because it is set as a wizard. Don't know why.--%>
<security:allowed bean="biobankBean" event="createBiobank">
    <li
    <c:if test="${secondarymenu == 'biobank_create'}"> class="active" </c:if> ><s:link
                beanclass="bbmri.action.biobank.CreateActionBean"
                event="display"><f:message key="biobank_create"/></s:link>
    </li>
</security:allowed>

<%------------------------------------------------------------------------%>

<%--<c:if test="${not empty biobank}">--%>
    <%--<li <c:if test="${secondarymenu == 'sample_approve_request'}"> class="active" </c:if> >--%>
        <%--<s:link beanclass="bbmri.action.SampleQuestionActionBean"--%>
                <%--event="approveSampleRequest"><f:message--%>
                <%--key="approve_sample_request"/></s:link></li>--%>
    <%--<li <c:if test="${secondarymenu == 'sample_create'}"> class="active" </c:if> ><s:link--%>
            <%--beanclass="bbmri.action.sample.CreateSampleActionBean"><f:message--%>
            <%--key="sample_create"/></s:link></li>--%>
    <%--&lt;%&ndash;--%>
    <%--<li <c:if test="${secondarymenu == 'sample_withdraw'}"> class="active" </c:if> ><s:link--%>
            <%--beanclass="bbmri.action.sample.SampleActionBean"><f:message--%>
            <%--key="sample.withdraw"/></s:link></li>     &ndash;%&gt;--%>
    <%--<li <c:if test="${secondarymenu == 'requestGroup_all'}"> class="active" </c:if> ><s:link--%>
            <%--beanclass="bbmri.action.SampleQuestionActionBean"--%>
            <%--event="allRequestGroups"><f:message key="sample.requests"/></s:link></li>--%>
    <%--<li <c:if test="${secondarymenu == 'sample_all'}"> class="active" </c:if> ><s:link--%>
            <%--beanclass="bbmri.action.sample.SampleActionBean" event="allSamples"><f:message--%>
            <%--key="sample.all"/></s:link></li>--%>
<%--</c:if>--%>