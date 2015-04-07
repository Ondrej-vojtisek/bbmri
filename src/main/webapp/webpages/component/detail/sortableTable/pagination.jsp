<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <div class="pagination pagination-centered">
        <ul>
            <li>
                <stripes:link beanclass="${actionBean.name}" event="${pagination.event}">

                    <%-- we must store param about context - for instance for which biobank we print samples--%>
                    <core:if test="${not empty pagination.identifierParam}">
                        <stripes:param name="${pagination.identifierParam}"
                                 value="${pagination.identifier}"/>
                    </core:if>

                    <stripes:param name="page${pagination.webParamDiscriminator}" value="${pagination.currentPage - 1}"/>
                    <stripes:param name="orderParam${pagination.webParamDiscriminator}" value="${pagination.orderParam}"/>
                    <stripes:param name="desc${pagination.webParamDiscriminator}" value="${pagination.desc}"/>
                    «
                </stripes:link>
            </li>

            <core:forEach var="i" begin="${pagination.myFirstLinkedPage}"
                       end="${pagination.myLastLinkedPage}">

                <li ${pagination.currentPage == i ?  "class=\"active\"" : ""}>
                    <stripes:link beanclass="${actionBean.name}" event="${pagination.event}">

                        <%-- we must store param about context - for instance for which biobank we print samples--%>
                        <core:if test="${not empty pagination.identifierParam}">
                            <stripes:param name="${pagination.identifierParam}"
                                     value="${pagination.identifier}"/>
                        </core:if>

                        <stripes:param name="page${pagination.webParamDiscriminator}" value="${i}"/>
                        <stripes:param name="orderParam${pagination.webParamDiscriminator}" value="${pagination.orderParam}"/>
                        <stripes:param name="desc${pagination.webParamDiscriminator}" value="${pagination.desc}"/>
                        ${i}
                    </stripes:link>
                </li>

            </core:forEach>

            <li>
                <stripes:link beanclass="${actionBean.name}" event="${pagination.event}">

                    <%-- we must store param about context - for instance for which biobank we print samples--%>
                    <core:if test="${not empty pagination.identifierParam}">
                        <stripes:param name="${pagination.identifierParam}"
                                 value="${pagination.identifier}"/>
                    </core:if>

                    <stripes:param name="page${pagination.webParamDiscriminator}" value="${pagination.currentPage + 1}"/>
                    <stripes:param name="orderParam${pagination.webParamDiscriminator}" value="${pagination.orderParam}"/>
                    <stripes:param name="desc${pagination.webParamDiscriminator}" value="${pagination.desc}"/>

                    »
                </stripes:link>
            </li>
        </ul>
    </div>

</stripes:layout-definition>