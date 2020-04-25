function sendGetReqCount(){
    const GET_REQ_COUNT_URL = 'http://localhost:8080/myWebAdapter_war_exploded/app/rest/reqCount';
    return $.ajax ({
        type: "GET",
        url: GET_REQ_COUNT_URL,
        contentType: 'application/json',
        data: $.param({}),
        beforeSend: function (xhr){
            xhr.setRequestHeader('Authorization', sessionStorage[BTOA_KEY]);
        },
        error: function (er) {
            console.log(er)
        }
    });
}

function ajaxRequest(params) {
    sendGetReqCount().then(function (reqCount) {
        const GET_REQUESTS_URL = 'http://localhost:8080/myWebAdapter_war_exploded/app/rest/requests';
        return $.ajax({
            type: "GET",
            url: GET_REQUESTS_URL,
            data: $.param({page_number: (params.data.offset / params.data.limit + 1), page_size: params.data.limit}),
            contentType: 'application/json',
            beforeSend: function (xhr){
                xhr.setRequestHeader('Authorization', sessionStorage[BTOA_KEY]);
            },
            success: function (data) {
                params.success({
                    "rows": data,
                    "total": reqCount["rows_count"]
                })
            },
            error: function (er) {
                console.log(er)
            }
        });
    });
}

function requestsTableInit() {
    $('#requestsTable').bootstrapTable({
        pagination: true,
        ajax: ajaxRequest,
        sidePagination: 'server',
        pageSize: 15,
        onClickRow: function (row, $element, field) {
            console.log("Test");
        }
    });
}

$(document).ready(function () {
    // expand search button
    const $expandableSearchFields = [$("#firstNameSearchField"),
        $("#familyNameSearchField"), $("#patronymicSearchField")];
    $.each($expandableSearchFields, function () {
        this.hide();
    });

    const $expandButton = $("#expandButton");
    $expandButton.attr('data-toggle', 'tooltip');
    $expandButton.attr('data-placement', 'top');
    $expandButton.attr('title', 'Расширить поиск');
    $expandButton.click(function () {
        if ($(this).hasClass('fa-arrow-left')) {
            // To reduce search
            $(this).removeClass('fa-arrow-left');
            $(this).addClass('fa-arrow-right');
            $.each($expandableSearchFields, function () {
                this.show();
            });
            $expandButton.attr('title', 'Сузить поиск');
        } else {
            // To expand search
            $(this).removeClass('fa-arrow-right');
            $(this).addClass('fa-arrow-left');
            $.each($expandableSearchFields, function () {
                this.hide();
            });
            $expandButton.attr('title', 'Расширить поиск');
        }
    });

    // Refresh table button
    let refreshButton = $("#refreshButton");
    refreshButton.on('click', function () {
        $('#requestsTable').bootstrapTable('refresh');
    });

});