<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.biobank.BiobankActionBean.allBiobanks" var="title"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <s:layout-component name="body">

        <c:if test="${actionBean.isRackBox}">

            <s:layout-render name="/webpages/component/detail/container/ribbon.jsp"
                             record="${actionBean.rackBox.rack.container}"/>

            <s:layout-render name="/webpages/component/detail/rack/ribbon.jsp"
                             record="${actionBean.rackBox.rack}"/>

            <s:layout-render name="/webpages/component/detail/box/ribbon.jsp"
                             record="${actionBean.rackBox}"/>
        </c:if>

        <fieldset>
            <legend><f:message key="cz.bbmri.entities.infrastructure.Position.positions"/></legend>

            <%--Layout sortableTable can't be used because we want to assign position.sample.id (item.sample.id) as parameter instead of--%>
            <%--position.id (item.id) as it is in all other tables--%>

            <table class="table table-hover table-striped">

                    <s:layout-render name="${actionBean.componentManager.sortableHeader}"
                                     pagination="${actionBean.pagination}"/>

                    <tbody>

                    <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                                     collection="${actionBean.pagination.myPageList}"/>

                    <c:forEach var="item" items="${actionBean.pagination.myPageList}">
                        <tr>
                            <s:layout-render name="${actionBean.componentManager.tableRow}" record="${item}"/>
                            <td class="action">

                                    <%--fix styles of button--%>
                                    <div class="tableAction">
                                        <s:link beanclass="cz.bbmri.action.sample.SampleActionBean" event="detail"
                                                class="btn btn-info btnMargin">

                                            <%--which parameter to access event--%>
                                            <s:param name="sampleId" value="${item.sample.id}"/>
                                            <f:message key="detail"/>
                                        </s:link>
                                    </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <s:layout-render name="/webpages/component/detail/sortableTable/pagination.jsp"
                                 pagination="${actionBean.pagination}"/>
        </fieldset>

    </s:layout-component>
</s:layout-render>