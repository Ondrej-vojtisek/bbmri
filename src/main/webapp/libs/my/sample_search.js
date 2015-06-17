
// submit button to add sample to reservation
// after is neccessary to update list os requested samples and list of search results

function submitAdd(button) {

    var form = button.form;
    var params = $(form).serializeArray();
    params.push({name: '_eventName', value: button.name});
    var xhr = $.post(form.action, params, function (data) {
            if (xhr.getResponseHeader('X-Stripes-Success')) {
                $('#searched_samples').html(data);

                // get params given from form
                len = params.length,
                    dataObj = {};

                for (i = 0; i < len; i++) {
                    dataObj[params[i].name] = params[i].value;
                }

                var reservationId = dataObj['reservationId'];
                var withdrawId = dataObj['withdrawId'];
                var questionId = dataObj['questionId'];
                var biobankId = dataObj['biobankId'];

                //                Refresh list of requests
                refresh(reservationId, withdrawId, questionId)

                // Refresh list of samples
                submitSearch(reservationId, withdrawId, questionId, biobankId)


            }
            else {
                console.log('LOG submitAdd ERROR');
            }

        }
    );

    return false;
}


// Submit increase/decrease/remove request

function submitForm(button) {
    var form = button.form;
    var params = $(form).serializeArray();
    params.push({name: '_eventName', value: button.name});
    var xhr = $.post(form.action, params, function (data) {
        if (xhr.getResponseHeader('X-Stripes-Success')) {
            $('#requests').html(data);

            // get params given from form
            len = params.length,
                dataObj = {};

            for (i = 0; i < len; i++) {
                dataObj[params[i].name] = params[i].value;
            }

            var reservationId = dataObj['reservationId'];
            var withdrawId = dataObj['withdrawId'];
            var questionId = dataObj['questionId'];
            var biobankId = dataObj['biobankId'];

            //                Refresh list of requests
            refresh(reservationId, withdrawId, questionId)

            submitSearch(reservationId, withdrawId, questionId, biobankId)

        }
        else {
            console.log('LOG submitForm ERROR');
        }


    });

    return false;
}

// Submit search criteria and print updated version of search result

function submitSearch(reservationId, withdrawId, questionId, biobankId) {

    $.get(SAMPLE_SEARCH_URL,
        {
            'sexId': $("#sexId").val(),
            'materialTypeId': $("#materialTypeId").val(),
            'retrievedId': $("#retrievedId").val(),
            'diagnosisKey': $("#diagnosisKey").val(),
            '_eventName': 'findSamples',
            'reservationId': reservationId,
            'withdrawId': withdrawId,
            'questionId': questionId,
            'biobankId': biobankId
        },
        function (data) {
            $('#searched_samples').html(data);
        }
    );
}

// Refresh list of requests

function refresh(reservationId, withdrawId, questionId) {

    $.get(REQUEST_URL,
        {
            '_eventName': 'refresh',
            'reservationId': reservationId,
            'withdrawId': withdrawId,
            'questionId': questionId
        },
        function (data) {
            $('#requests').html(data);
        }
    );
}
