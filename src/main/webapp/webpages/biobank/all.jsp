<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.biobank.BiobankActionBean.allBiobanks" var="title"/>
<s:useActionBean var="biobankBean" beanclass="cz.bbmri.action.biobank.BiobankActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 secondarymenu="biobank_all">

    <s:layout-component name="body">
        <table class="table table-hover table-striped">
            <thead>
            <tr>
                <th><f:message key="cz.bbmri.entities.Biobank.name"/></th>
                <th><f:message key="cz.bbmri.entities.Biobank.address"/></th>
                <th class="noSort"><f:message key="actions"/></th>
            </tr>
            </thead>
            <tbody>


            <c:if test="${empty biobankBean.biobanks}">
                <tr>
                    <td colspan="3"><f:message key="empty"/></td>
                </tr>
            </c:if>
            <c:forEach items="${biobankBean.biobanks}" var="biobank">

                <tr>
                    <td>${biobank.name}</td>
                    <td>${biobank.address}</td>
                    <td class="action">

                            <%--This is important for the instance based ACL--%>
                        <c:set target="${biobankBean}" property="biobankId" value="${biobank.id}"/>

                        <security:allowed bean="biobankBean" event="edit">
                            <div class="tableAction">
                                <s:link beanclass="cz.bbmri.action.biobank.BiobankActionBean" event="edit"
                                        class="btn btn-primary btnMargin">
                                    <s:param name="biobankId" value="${biobank.id}"/>
                                    <f:message key="edit"/>
                                </s:link>
                            </div>
                        </security:allowed>
                        <security:notAllowed bean="biobankBean" event="edit">
                            <security:allowed bean="biobankBean" event="detail">
                                <div class="tableAction">
                                    <s:link beanclass="cz.bbmri.action.biobank.BiobankActionBean" event="detail"
                                            class="btn btn-info btnMargin">
                                        <s:param name="biobankId" value="${biobank.id}"/>
                                        <f:message key="detail"/>
                                    </s:link>
                                </div>
                            </security:allowed>
                        </security:notAllowed>

                        <security:allowed bean="biobankBean" event="delete">
                            <s:form beanclass="cz.bbmri.action.biobank.BiobankActionBean">

                                <f:message var="question" key="cz.bbmri.action.biobank.BiobankActionBean.questionDelete"/>

                                <s:submit name="delete" class="btn btn-danger" onclick="return confirm('${question}')">
                                    <s:param name="biobankId" value="${biobank.id}"/>
                                </s:submit>
                            </s:form>
                        </security:allowed>

                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </s:layout-component>
</s:layout-render>