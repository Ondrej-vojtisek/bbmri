<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <div class="pagination pagination-centered">
            <ul>
                    <%--<li class="disabled"><a href="#">«</a></li>--%>
                <li>
                    <s:link beanclass="${actionBean.name}" event="${actionBean.pagination.event}">
                        <s:param name="page" value="${actionBean.pagination.currentPage - 1}"/>
                        <s:param name="orderParam" value="${actionBean.pagination.orderParam}"/>
                        <s:param name="desc" value="${actionBean.pagination.desc}"/>
                        «
                    </s:link>
                </li>

                <c:forEach var="i" begin="${actionBean.pagination.myFirstLinkedPage}"
                           end="${actionBean.pagination.myLastLinkedPage}">

                    <li ${actionBean.pagination.currentPage == i ?  "class=\"active\"" : ""}>
                        <s:link beanclass="${actionBean.name}" event="${actionBean.pagination.event}">
                            <s:param name="page" value="${i}"/>
                            <s:param name="orderParam" value="${actionBean.pagination.orderParam}"/>
                            <s:param name="desc" value="${actionBean.pagination.desc}"/>
                            ${i}
                        </s:link>
                    </li>

                </c:forEach>

                <li>
                    <s:link beanclass="${actionBean.name}" event="${actionBean.pagination.event}">
                        <s:param name="page" value="${actionBean.pagination.currentPage + 1}"/>
                        <s:param name="orderParam" value="${actionBean.pagination.orderParam}"/>
                        <s:param name="desc" value="${actionBean.pagination.desc}"/>
                        »
                    </s:link>
                </li>
            </ul>
        </div>

</s:layout-definition>