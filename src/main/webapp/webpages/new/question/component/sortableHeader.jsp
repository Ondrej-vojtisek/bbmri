<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <thead>
    <tr>
        <th>
            <s:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Question.created"
                             column="created"
                             pagination="${pagination}"/>
        </th>
        <th>
            <s:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.QuestionState.questionState"
                             column="questionState"
                             pagination="${pagination}"/>
        </th>
        <th>
            <s:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Project.project"
                             column="project"
                             pagination="${pagination}"/>
        </th>
        <th>
            <s:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Question.specification"
                             column="specification"
                             pagination="${pagination}"/>
        </th>

    </tr>
    </thead>

</s:layout-definition>