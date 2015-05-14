<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean beanclass="cz.bbmri.action.BiobankActionBean" var="biobankActionBean"/>
<stripes:useActionBean beanclass="cz.bbmri.action.SampleActionBean" var="sampleActionBean"/>
<core:set var="samplePagination" value="${actionBean.samplePagination}"/>

<stripes:layout-render name="${component.layout.content}"
                       primarymenu="biobank">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.biobank}" active="samples"/>

        <table class="table table-hover table-striped">
            <stripes:layout-render name="${component.header.sample}" pagination="${samplePagination}"/>

            <tbody>
            <stripes:layout-render name="${component.table.emptyTable}" collection="${samplePagination.myPageList}"/>
            <core:forEach var="item" items="${samplePagination.myPageList}">
                <tr>
                    <stripes:layout-render name="${component.row.sample}" item="${item}"/>
                    <td class="action">
                        <span class="pull-right">
                            <div class="tableAction">
                                <security:allowed bean="sampleActionBean" event="detail">
                                    <stripes:link beanclass="cz.bbmri.action.SampleActionBean"
                                                  event="detail"
                                                  class="btn btn-info btnMargin">
                                        <stripes:param name="id" value="${item.id}"/>
                                        <format:message key="detail"/>
                                    </stripes:link>
                                </security:allowed>
                            </div>
                        </span>
                    </td>

                </tr>
            </core:forEach>
            </tbody>
        </table>

        <%--show pagination only if list contains some data--%>
        <core:if test="${not empty samplePagination.myPageList}">
            <stripes:layout-render name="${component.pager}" pagination="${samplePagination}"/>
        </core:if>


    </stripes:layout-component>
</stripes:layout-render>
