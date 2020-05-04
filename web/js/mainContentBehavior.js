function convertDataForTable(sourceData) {
    let changedData = [];
    sourceData.forEach(function (object) {
        let changedObject = {
            id: object.id,
            author: object.author,
            request_date: new Date(object.request_date).toLocaleDateString(),
        };
        if ("response_date" in object) {
            changedObject.response_date = new Date(object.response_date).toLocaleDateString()
            changedObject.status = "Ответ получен";
        } else {
            changedObject.status = "Ответ не получен";
        }
        changedData.push(changedObject);
    });
    return changedData;
}

function cellStyle(value, row, index) {
    var classes = [
        'bg-green',
        'bg-red'
    ];

    return {
        css: {
            color: value === "Ответ получен" ? 'green': 'red'
        }
    };
}

function fillModalForm(data) {
    let withPatronymic;
    // If patronymic is exists
    withPatronymic = "patronymic" in data;

    let withPassportData;
    // If passport data is exists
    withPassportData = "passport_number" in data;

    let withBirthPlaceData;
    // If birth place data is exists
    withBirthPlaceData = "place_type" in data;

    // If response is exists
    if ("snils" in data) {
        if (withPatronymic) {
            $("#resPatronymic").val(data.patronymic);
        } else {
            $("#resPatronymicDiv").hide();
        }

        $("#resFirstName").val(data.first_name);
        $("#resFamilyName").val(data.family_name);
        $("#resSnils").val(data.snils);
        $("#resBirthDate").val(new Date(data.birth_date).toISOString().substr(0, 10));

        if (data.gender === "MALE"){
            $('#resGender').val("1");
        } else {
            $('#resGender').val("2");
        }

        if (withPassportData) {
            $("#resSeries").val(data.passport_series);
            $("#resNumber").val(data.passport_number);
            $("#resIssueDate").val(new Date(data.passport_issue_date).toISOString().substr(0, 10));
            $("#resIssuer").val(data.passport_issuer);
        } else {
            $("#resPassportDiv").hide();
        }

        if (withBirthPlaceData) {
            if (data.place_type === "SPECIAL") {
                $("#resPlaceType").val("1");
            } else {
                $("#resPlaceType").val("2");
            }
            $("#resSettlement").val(data.settlement);

            if ("district" in data) {
                $("#resDistrict").val(data.district);
            } else {
                $("#resDistrictDiv").hide();
            }
            if ("region" in data) {
                $("#resRegion").val(data.district);
            } else {
                $("#resRegionDiv").hide();
            }
            if ("country" in data) {
                $("#resCountry").val(data.country);
            } else {
                $("#resCountryDiv").hide();
            }
        } else {
            $("#resBirthPlaceDiv").hide();
        }
    }

    if (withPatronymic) {
        $("#reqPatronymic").val(data.patronymic);
    } else {
        $("#patronymicDiv").hide();
    }

    $("#reqFirstName").val(data.first_name);
    $("#reqFamilyName").val(data.family_name);
    $("#reqSnils").val(data.snils);
    $("#reqBirthDate").val(new Date(data.birth_date).toISOString().substr(0, 10));

    if (data.gender === "MALE"){
        $('#reqGender').val("1");
    } else {
        $('#reqGender').val("2");
    }

    if (withPassportData) {
        $("#reqSeries").val(data.passport_series);
        $("#reqNumber").val(data.passport_number);
        $("#reqIssueDate").val(new Date(data.passport_issue_date).toISOString().substr(0, 10));
        $("#reqIssuer").val(data.passport_issuer);
    } else {
        $("#passportDiv").hide();
    }

    if (withBirthPlaceData) {
        if (data.place_type === "SPECIAL") {
            $("#reqPlaceType").val("1");
        } else {
            $("#reqPlaceType").val("2");
        }
        $("#reqSettlement").val(data.settlement);

        if ("district" in data) {
            $("#reqDistrict").val(data.district);
        } else {
            $("#reqDistrictDiv").hide();
        }
        if ("region" in data) {
            $("#reqRegion").val(data.district);
        } else {
            $("#reqRegionDiv").hide();
        }
        if ("country" in data) {
            $("#reqCountry").val(data.country);
        } else {
            $("#reqCountryDiv").hide();
        }
    } else {
        $("#birthPlaceDiv").hide();
    }
}

function showRequestData(requestId) {
    const GET_REQUEST_URL = 'http://localhost:8080/myWebAdapter_war_exploded/app/rest/request';
    const modalForm = $("#requestModal");
    $("#sendReqButton").hide();
    $("#patronymicCheckBlock").hide();
    $("#requestType").hide();
    modalForm.modal('show');
    modalForm.find('input, select, textarea').each(function () {
        $(this).prop("disabled", true);
        $(this).val('');
    });

    modalForm.on('hidden.bs.modal', function () {
        $("#secondColumn").show();
        $("#sendReqButton").show();
        $("#patronymicCheckBlock").show();
        $("#requestType").show();
        $(this).find ('input, textarea, select').each(function() {
            $(this).val('');
            $(this).prop("disabled", false);
        });
        modalForm.off();

        $("#patronymicDiv").show();
        $("#resPatronymicDiv").show();

        $("#resPassportDiv").show();
        $("#passportDiv").show();

        $("#resBirthPlaceDiv").show();
        $("#birthPlaceDiv").show();

        $("#resDistrictDiv").show();
        $("#resRegionDiv").show();
        $("#resCountryDiv").show();

        $("#reqDistrictDiv").show();
        $("#reqRegionDiv").show();
        $("#reqCountryDiv").show();
    });

    $.ajax({
        type: "GET",
        url: GET_REQUEST_URL,
        data: $.param({id:requestId}),
        contentType: 'application/json',
        beforeSend: function (xhr){
            xhr.setRequestHeader('Authorization', sessionStorage[BTOA_KEY]);
        },
        success: function (data) {
            if (!("snils" in data)) {
                $("#secondColumn").hide();
            }
            console.log(data)
            fillModalForm(data);
        },
        error: function (er) {
            console.log(er)
        }
    });
}

var searchData = {};

function createSearchParamObj(searchParams) {
    let paramObj = {};
    if ("author" in searchParams) {
        paramObj.author = searchParams.author
    }
    return paramObj;
}

function sendGetReqCount(params){
    const GET_REQ_COUNT_URL = 'http://localhost:8080/myWebAdapter_war_exploded/app/rest/reqCount';
    let paramObj = createSearchParamObj(searchData);
    return $.ajax ({
        type: "GET",
        url: GET_REQ_COUNT_URL,
        contentType: 'application/json',
        data: $.param(paramObj),
        beforeSend: function (xhr){
            xhr.setRequestHeader('Authorization', sessionStorage[BTOA_KEY]);
        },
        error: function (er) {
            console.log(er)
        }
    });
}

function ajaxRequest(params) {
    sendGetReqCount(params).then(function (reqCount) {
        const GET_REQUESTS_URL = 'http://localhost:8080/myWebAdapter_war_exploded/app/rest/requests';
        let paramObj = createSearchParamObj(searchData);
        paramObj.page_number = (params.data.offset / params.data.limit + 1);
        paramObj.page_size = params.data.limit;
        console.log(params);
        return $.ajax({
            type: "GET",
            url: GET_REQUESTS_URL,
            data: $.param(paramObj),
            contentType: 'application/json',
            beforeSend: function (xhr){
                xhr.setRequestHeader('Authorization', sessionStorage[BTOA_KEY]);
            },
            success: function (data) {
                params.success({
                    "rows": convertDataForTable(data),
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
            const requestId = row.id;
            console.log(row);
            showRequestData(requestId);
        }
    });
}

$(document).ready(function () {
    // expand search button
    /*const $expandableSearchFields = [$("#firstNameSearchField"),
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
    });*/

    // Refresh table button
    var refreshButton = $("#refreshButton");
    refreshButton.on('click', function () {
        $('#requestsTable').bootstrapTable('refresh');
    });

    const authorSearchField = $("#authorSearchField");

    const searchButton = $("#searchButton");
    searchButton.on('click', function (e) {
        e.preventDefault();
        searchData.author = authorSearchField.val().trim();
        if (searchData.author.length === 0) {
            delete searchData.author;
        }
        refreshButton.trigger('click');
    })

});