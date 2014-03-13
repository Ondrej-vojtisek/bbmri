<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.biobank.BiobankActionBean.allBiobanks" var="title"/>
<s:useActionBean var="biobankBean" beanclass="cz.bbmri.action.biobank.BiobankActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 secondarymenu="biobank_all">

    <s:layout-component name="body">

        <s:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                         pagination="${actionBean.pagination}"
                         componentManager="${actionBean.componentManager}"
                         targetBean="cz.bbmri.action.biobank.BiobankActionBean"
                         eventName="detail"
                         paramName="biobankId"/>


        <%--<table class="table table-hover table-striped">--%>
            <%--<s:layout-render name="/webpages/component/detail/biobank/header.jsp"/>--%>
            <%--<tbody>--%>

            <%--<s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"--%>
                             <%--collection="${biobankBean.biobanks}"/>--%>

            <%--<c:forEach items="${biobankBean.biobanks}" var="biobank">--%>

                <%--<tr>--%>

                    <%--<s:layout-render name="/webpages/component/detail/biobank/row.jsp" record="${biobank}"/>--%>

                    <%--<td class="action">--%>

                            <%--&lt;%&ndash;This is important for the instance based ACL&ndash;%&gt;--%>
                        <%--<c:set target="${biobankBean}" property="biobankId" value="${biobank.id}"/>--%>

                        <%--<security:allowed bean="biobankBean" event="detail">--%>
                            <%--<div class="tableAction">--%>
                                <%--<s:link beanclass="cz.bbmri.action.biobank.BiobankActionBean" event="detail"--%>
                                        <%--class="btn btn-info btnMargin">--%>
                                    <%--<s:param name="biobankId" value="${biobank.id}"/>--%>
                                    <%--<f:message key="detail"/>--%>
                                <%--</s:link>--%>
                            <%--</div>--%>
                        <%--</security:allowed>--%>


                        <%--<security:allowed bean="biobankBean" event="delete">--%>
                            <%--<s:form beanclass="cz.bbmri.action.biobank.BiobankActionBean">--%>

                                <%--<f:message var="question"--%>
                                           <%--key="cz.bbmri.action.biobank.BiobankActionBean.questionDelete"/>--%>

                                <%--<s:submit name="delete" class="btn btn-danger" onclick="return confirm('${question}')">--%>
                                    <%--<s:param name="biobankId" value="${biobank.id}"/>--%>
                                <%--</s:submit>--%>
                            <%--</s:form>--%>
                        <%--</security:allowed>--%>

                    <%--</td>--%>
                <%--</tr>--%>
            <%--</c:forEach>--%>
            <%--</tbody>--%>
        <%--</table>--%>
    </s:layout-component>
</s:layout-render>