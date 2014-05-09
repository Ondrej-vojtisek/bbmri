<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <s:layout-component name="body">

        <div class="form-actions">

            <s:form beanclass="cz.bbmri.action.infrastructure.MonitoringActionBean" method="POST" class="form-inline">
                <s:hidden name="biobankId" value="${actionBean.biobankId}"/>
                <s:hidden name="containerId" value="${actionBean.containerId}"/>
                <div class="control-group">
                    <f:message key="cz.bbmri.action.infrastructure.MonitoringActionBean.selectMonitoring"/>

                    <div class="controls">
                        <s:select name="monitoringId" class="btnMargin">
                            <s:options-collection collection="${actionBean.container.monitorings}" value="id"
                                                  label="description"/>
                        </s:select>

                        <s:submit name="monitoring" class="btn btn-info"/>

                    </div>
                </div>
            </s:form>

        </div>

        <c:if test="${empty actionBean.monitoring.records}">
            <f:message key="cz.bbmri.action.infrastructure.MonitoringActionBean.noRecords"/>
        </c:if>

        <c:if test="${not empty actionBean.monitoring}">

            <%--Graph object--%>

            <div class="demo-container">
                <div id="placeholder" class="demo-placeholder"></div>
            </div>

        </c:if>

    </s:layout-component>


    <s:layout-component name="script">
        <script type="text/javascript">
            //            $(document).ready(function () {
            //                console.log('Pokus1');
            //
            //                $.ajax({
            //                    url: "/pokus",
            //                    method: 'GET',
            //                    dataType: 'json',
            //                    success: onOutboundReceived
            //                });
            //                function onOutboundReceived(series) {
            //                    console.log('Pokus2');
            //                    console.log(series);
            //
            //                    var length = series.length;
            //                    var finalData = series;
            //
            //                    var options = {
            //                        lines: {
            //                            show: true
            //                        },
            //                        points: {
            //                            show: true
            //                        },
            //                        xaxis: {
            //                            tickDecimals: 0,
            //                            tickSize: 1
            //                        }
            //                    };
            //                    console.log('Pokus3');
            //                    $.plot($("#placeholder"), finalData, options);
            //
            //                    console.log('Pokus4');
            //
            //                }
            //            });

            $(function () {
                var d = [];
                <c:forEach var="record" items="${actionBean.monitoring.records}">

                d.push([${record.time.time}, ${record.value}]);

                </c:forEach>
                console.log(d);
                $.plot($("#placeholder"), [
                    {label: "${actionBean.monitoring.measurementType}" + ":" + "${actionBean.monitoring.name}", data: d}
                ],
                        {
                            xaxis: { mode: "time", timeformat: "%m/%d/%H:%M:%S", labelHeight: 30},
                            yaxis: { labelWidth: 30 }
                        });

                var xaxisLabel = $("<div class='axisLabel xaxisLabel'></div>").text("Time").appendTo($('#placeholder'));

                var yaxisLabel = $("<div class='axisLabel yaxisLabel'></div>")
                        .text("${actionBean.monitoring.measurementType.physicalQuantity}" +
                                " [" +
                                "${actionBean.monitoring.measurementType.unit}" + "]").appendTo($('#placeholder'));
                yaxisLabel.css("margin-top", yaxisLabel.width() / 2 - 20);
            });


        </script>
    </s:layout-component>

</s:layout-render>