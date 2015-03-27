<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <div class="pagination pagination-centered">
        <ul>
            <li>
                <s:link beanclass="${actionBean.name}" event="${pagination.event}">

                    <%-- we must store param about context - for instance for which biobank we print samples--%>
                    <c:if test="${not empty pagination.identifierParam}">
                        <s:param name="${pagination.identifierParam}"
                                 value="${pagination.identifier}"/>
                    </c:if>

                    <s:param name="page${pagination.webParamDiscriminator}" value="${pagination.currentPage - 1}"/>
                    <s:param name="orderParam${pagination.webParamDiscriminator}" value="${pagination.orderParam}"/>
                    <s:param name="desc${pagination.webParamDiscriminator}" value="${pagination.desc}"/>
                    «
                </s:link>
            </li>

            <c:forEach var="i" begin="${pagination.myFirstLinkedPage}"
                       end="${pagination.myLastLinkedPage}">

                <li ${pagination.currentPage == i ?  "class=\"active\"" : ""}>
                    <s:link beanclass="${actionBean.name}" event="${pagination.event}">

                        <%-- we must store param about context - for instance for which biobank we print samples--%>
                        <c:if test="${not empty pagination.identifierParam}">
                            <s:param name="${pagination.identifierParam}"
                                     value="${pagination.identifier}"/>
                        </c:if>

                        <s:param name="page${pagination.webParamDiscriminator}" value="${i}"/>
                        <s:param name="orderParam${pagination.webParamDiscriminator}" value="${pagination.orderParam}"/>
                        <s:param name="desc${pagination.webParamDiscriminator}" value="${pagination.desc}"/>
                        ${i}
                    </s:link>
                </li>

            </c:forEach>

            <li>
                <s:link beanclass="${actionBean.name}" event="${pagination.event}">

                    <%-- we must store param about context - for instance for which biobank we print samples--%>
                    <c:if test="${not empty pagination.identifierParam}">
                        <s:param name="${pagination.identifierParam}"
                                 value="${pagination.identifier}"/>
                    </c:if>

                    <s:param name="page${pagination.webParamDiscriminator}" value="${pagination.currentPage + 1}"/>
                    <s:param name="orderParam${pagination.webParamDiscriminator}" value="${pagination.orderParam}"/>
                    <s:param name="desc${pagination.webParamDiscriminator}" value="${pagination.desc}"/>

                    »
                </s:link>
            </li>
        </ul>
    </div>

</s:layout-definition>