<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="biobankBean" beanclass="cz.bbmri.action.biobank.BiobankActionBean"/>
<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 secondarymenu="biobank_all">

    <stripes:layout-component name="body">

        <stripes:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                         pagination="${actionBean.pagination}"
                         componentManager="${actionBean.componentManager}"
                         targetBean="cz.bbmri.action.biobank.BiobankActionBean"
                         eventName="detail"
                         paramName="biobankId"/>


        <%--<table class="table table-hover table-striped">--%>
            <%--<stripes:layout-render name="/webpages/component/detail/biobank/header.jsp"/>--%>
            <%--<tbody>--%>

            <%--<stripes:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"--%>
                             <%--collection="${biobankBean.biobanks}"/>--%>

            <%--<core:forEach items="${biobankBean.biobanks}" var="biobank">--%>

                <%--<tr>--%>

                    <%--<stripes:layout-render name="/webpages/component/detail/biobank/row.jsp" record="${biobank}"/>--%>

                    <%--<td class="action">--%>

                            <%--&lt;%&ndash;This is important for the instance based ACL&ndash;%&gt;--%>
                        <%--<core:set target="${biobankBean}" property="biobankId" value="${biobank.id}"/>--%>

                        <%--<security:allowed bean="biobankBean" event="detail">--%>
                            <%--<div class="tableAction">--%>
                                <%--<stripes:link beanclass="cz.bbmri.action.biobank.BiobankActionBean" event="detail"--%>
                                        <%--class="btn btn-info btnMargin">--%>
                                    <%--<stripes:param name="biobankId" value="${biobank.id}"/>--%>
                                    <%--<format:message key="detail"/>--%>
                                <%--</stripes:link>--%>
                            <%--</div>--%>
                        <%--</security:allowed>--%>


                        <%--<security:allowed bean="biobankBean" event="delete">--%>
                            <%--<stripes:form beanclass="cz.bbmri.action.biobank.BiobankActionBean">--%>

                                <%--<format:message var="question"--%>
                                           <%--key="cz.bbmri.action.biobank.BiobankActionBean.questionDelete"/>--%>

                                <%--<stripes:submit name="delete" class="btn btn-danger" onclick="return confirm('${question}')">--%>
                                    <%--<stripes:param name="biobankId" value="${biobank.id}"/>--%>
                                <%--</stripes:submit>--%>
                            <%--</stripes:form>--%>
                        <%--</security:allowed>--%>

                    <%--</td>--%>
                <%--</tr>--%>
            <%--</core:forEach>--%>
            <%--</tbody>--%>
        <%--</table>--%>
    </stripes:layout-component>
</stripes:layout-render>