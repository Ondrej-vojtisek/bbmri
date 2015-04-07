<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <stripes:layout-component name="body">

        <core:if test="${actionBean.isRackBox}">

            <stripes:layout-render name="/webpages/component/detail/container/ribbon.jsp"
                             record="${actionBean.rackBox.rack.container}"/>

            <stripes:layout-render name="/webpages/component/detail/rack/ribbon.jsp"
                             record="${actionBean.rackBox.rack}"/>

            <stripes:layout-render name="/webpages/component/detail/box/ribbon.jsp"
                             record="${actionBean.rackBox}"/>
        </core:if>

        <fieldset>
            <legend><format:message key="cz.bbmri.entities.infrastructure.Position.positions"/></legend>

            <%--Layout sortableTable can't be used because we want to assign position.sample.id (item.sample.id) as parameter instead of--%>
            <%--position.id (item.id) as it is in all other tables--%>

            <table class="table table-hover table-striped">

                    <stripes:layout-render name="${actionBean.componentManager.sortableHeader}"
                                     pagination="${actionBean.pagination}"/>

                    <tbody>

                    <stripes:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                                     collection="${actionBean.pagination.myPageList}"/>

                    <core:forEach var="item" items="${actionBean.pagination.myPageList}">
                        <tr>
                            <stripes:layout-render name="${actionBean.componentManager.tableRow}" record="${item}"/>
                            <td class="action">

                                    <%--fix styles of button--%>
                                    <div class="tableAction">
                                        <stripes:link beanclass="cz.bbmri.action.sample.SampleActionBean" event="detail"
                                                class="btn btn-info btnMargin">

                                            <%--which parameter to access event--%>
                                            <stripes:param name="sampleId" value="${item.sample.id}"/>
                                            <format:message key="detail"/>
                                        </stripes:link>
                                    </div>
                            </td>
                        </tr>
                    </core:forEach>
                    </tbody>
                </table>

                <stripes:layout-render name="/webpages/component/detail/sortableTable/pagination.jsp"
                                 pagination="${actionBean.pagination}"/>
        </fieldset>

    </stripes:layout-component>
</stripes:layout-render>