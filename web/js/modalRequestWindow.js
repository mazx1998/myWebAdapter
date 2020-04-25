/*class RequestModalWindowValidator {
    modal = $("#requestModal");
    secondColumn = $("#secondColumn");
    firstColumnHeader = $("#firstColumnHeader");
    // Can be hidden
    patronymicDiv = $("#patronymicDiv");
    passportDiv = $("#passportDiv");
    birthPlaceDiv = $("#birthPlaceDiv");
    // Start fields
    firstNameField = $('#reqFirstName');
    familyNameField = $('#reqFamilyName');
    patronymicCheckField = $("#patronymicCheck");
    patronymicField = $("#reqPatronymic");
    birthDateField = $('#reqBirthDate');
    genderCB = $('#reqGender');
    requestTypeCB = $('#reqType');
    startRequiredFields = [this.firstNameField, this.familyNameField,
        this.birthDateField, this.genderCB, this.requestTypeCB ];
    // Passport fields
    seriesField = $('#reqSeries');
    numberField = $('#reqNumber');
    issueDateField = $('#reqIssueDate');
    issuerField = $('#reqIssuer');
    passportRequiredFields = [this.seriesField, this.numberField , this.issueDateField, this.issuerField];
    // Birth place fields
    placeTypeCB = $('#reqPlaceType');
    settlementField = $('#reqSettlement');
    districtField = $('#reqDistrict');
    regionField = $('#reqRegion');
    countryField = $('#reqCountry');
    birthPlaceRequiredFields = [this.placeTypeCB, this.settlementField];

    constructor() {
        // Hide unused elements
        this.secondColumn.hide();
        this.firstColumnHeader.hide();
        // Hide blocks that must be hidden
        this.patronymicDiv.hide();
        this.passportDiv.hide();
        this.birthPlaceDiv.hide();
        // Add 'is-invalid' class on visible fields
        this.addInvalidToAll(this.startRequiredFields);
        // Clear all input values
        this.clearInputValues();
    }

    setDefaultProperties() {
        this.removeInvalidFromAll(this.startRequiredFields);
        this.removeInvalidFromAll(this.passportRequiredFields);
        this.removeInvalidFromAll(this.birthPlaceRequiredFields);
        this.clearInputValues();
        this.patronymicCheckField.prop("checked", false);
        this.patronymicField.removeClass('is-valid');
        this.patronymicField.removeClass('is-invalid');
    }

    addInvalidToAll($fieldsId) {
        $.each($fieldsId, function() {
            this.addClass('is-invalid');
        });
    }

    removeInvalidFromAll($fieldsId){
        $.each($fieldsId, function() {
            this.removeClass('is-invalid');
            this.removeClass('is-valid');
        });
    }

    clearInputValues(){
        this.modal.find ('input, textarea, select').each(function() {
            $(this).val('');
        });
    }
}*/

function makeValid($field){
    $field.removeClass('is-invalid').addClass('is-valid');
}

function makeInvalid($field){
    $field.removeClass('is-valid').addClass('is-invalid');
}

function addInvalid($fieldsId){
    // Add 'is-invalid' class on fieldsId
    $.each($fieldsId, function() {
        this.addClass('is-invalid');
    });
}

function removeInvalid($fieldsId){
    // remove 'is-invalid' class from fieldsId
    $.each($fieldsId, function() {
        this.removeClass('is-invalid');
        this.removeClass('is-valid');
    });
}

function clearInputValues($modal){
    $modal.find ('input, textarea, select').each(function() {
        $(this).val('');
    });
}

$(document).ready(function(){

    let defaultInputsId = [$('#reqFirstName'), $('#reqFamilyName'), $('#reqBirthDate'), $('#reqGender'), $('#reqType')];
    let passportDataId = [$('#reqSeries'), $('#reqNumber'), $('#reqIssueDate'), $('#reqIssuer')];
    let birthPlaceDataId = [$('#reqPlaceType'), $('#reqSettlement')];

    let requestTypeField = $("#reqType");
    let modalReqForm = $("#requestModal");
    let patronymicCheckField = $("#patronymicCheck");
    let openCreateRequestModal = $("#openCreateRequestModal");

    openCreateRequestModal.on('click', function () {
        let secondColumn = $("#secondColumn");
        let firstColumnHeader = $("#firstColumnHeader");
        let patronymicDiv = $("#patronymicDiv");
        let patronymicField = $("#reqPatronymic");

        let passportDiv = $("#passportDiv");
        let birthPlaceDiv = $("#birthPlaceDiv");

        // Set parameters
        secondColumn.hide();
        firstColumnHeader.hide();
        patronymicDiv.hide();
        passportDiv.hide();
        birthPlaceDiv.hide();
        addInvalid(defaultInputsId);
        clearInputValues(modalReqForm);

        // Set default parameters of modal window when close
        modalReqForm.on('hidden.bs.modal', function () {
            removeInvalid(defaultInputsId);
            removeInvalid(passportDataId);
            removeInvalid(birthPlaceDataId);
            clearInputValues($(this));
            patronymicCheckField.prop("checked", false);
            patronymicField.removeClass('is-valid');
            patronymicField.removeClass('is-invalid');
            secondColumn.show();
            firstColumnHeader.show();
            patronymicDiv.show();
            passportDiv.show();
            birthPlaceDiv.show();

            // Remove all event handlers
            modalReqForm.off();
            requestTypeField.off();
            $.each(inputModalFields, function () {
                this.off();
            });
            $.each(datesInputModalFields, function () {
                this.off();
            });
            $.each(selectInputModalFields, function () {
                this.off();
            });
            patronymicCheckField.off();
            requestTypeField.off();
            series.off();
            number.off();
            modalReqForm.off();
        });

        // Empty space validation
        let inputModalFields = [ $("#reqFirstName"), $("#reqFamilyName"), $("#reqPatronymic"),
            $("#reqIssuer"), $("#reqSettlement") ];
        for (let i = 0; i < inputModalFields.length; i++) {
            inputModalFields[i].on('keyup', function () {
                let field = $(this).val();
                if (field.trim()) {
                    // is not empty or whitespace
                    makeValid($(this));
                } else {
                    makeInvalid($(this));
                }
            });
        }

        // Dates validator
        let datesInputModalFields = [$("#reqBirthDate"), $("#reqIssueDate")];
        for (let i = 0; i < datesInputModalFields.length; i++) {
            datesInputModalFields[i].change(function () {
                let minDate = new Date(1900, 1, 1);
                let maxDate = new Date(); // Current
                let date = new Date($(this).val());

                if (date.getTime() <= maxDate.getTime() && date.getTime() >= minDate.getTime()) {
                    makeValid($(this));
                } else {
                    makeInvalid($(this));
                }
            });
        }

        // Selects validator
        let selectInputModalFields = [$("#reqGender"), $("#reqPlaceType"), requestTypeField];
        for (let i = 0; i < selectInputModalFields.length; i++) {
            selectInputModalFields[i].change(function () {
                let selectedItemValue = $(this).children("option:selected").val();
                let itemsCount = $(this).children("option").length;
                if (selectedItemValue > 0 && selectedItemValue <= itemsCount) {
                    makeValid($(this));
                } else {
                    makeInvalid($(this));
                }
            });
        }

        // Check patronymic controller
        patronymicCheckField.change(function() {
            if(this.checked) {
                patronymicField.addClass('is-invalid');
                patronymicDiv.show();
            } else {
                patronymicField.val('');
                patronymicField.removeClass('is-invalid');
                patronymicField.removeClass('is-valid');
                patronymicDiv.hide();
            }
        });

        // Request type controller
        requestTypeField.change(function () {
            let selectedItemValue = $(this).children("option:selected").val();
            switch (selectedItemValue) {
                // Passport
                case "1":
                    addInvalid(passportDataId);
                    removeInvalid(birthPlaceDataId);
                    passportDiv.show();
                    birthPlaceDiv.hide();
                    $.each(birthPlaceDataId, function () {
                        this.val('');
                    });
                    break;
                // Birth place
                case "2":
                    addInvalid(birthPlaceDataId);
                    removeInvalid(passportDataId);
                    passportDiv.hide();
                    birthPlaceDiv.show();
                    $.each(passportDataId, function () {
                        this.val('');
                    });
                    break;
                // Both
                case "3":
                    addInvalid(passportDataId);
                    addInvalid(birthPlaceDataId);
                    passportDiv.show();
                    birthPlaceDiv.show();
                    break;
            }
        });

        // Passport data validations
        let series = $("#reqSeries");
        let number = $("#reqNumber");
        // Series
        series.mask('0000', {placeholder: "____"});
        series.on('keyup', function () {
            if ($(this).val().length === 4) {
                makeValid($(this));
            } else {
                makeInvalid($(this));
            }
        });
        //  Number
        number.mask('000009', {placeholder: "______"});
        number.on('keyup', function () {
            if ($(this).val().length === 6) {
                makeValid($(this));
            } else {
                makeInvalid($(this));
            }
        });

    });

    $.fn.fillPassportData = function($requestObj) {
        $requestObj.passport_series = $('#reqSeries').val();
        $requestObj.passport_number = $('#reqNumber').val();
        $requestObj.passport_issue_date = new Date($('#reqIssueDate').val()).getTime();
        $requestObj.passport_issuer = $('#reqIssuer').val().toUpperCase();
    };

    $.fn.fillBirthPlaceData = function($requestObj){
        $requestObj.place_type = $('#reqPlaceType').children("option:selected").html().toUpperCase();
        $requestObj.settlement = $('#reqSettlement').val().toUpperCase();

        let districtField = $('#reqDistrict');
        if (districtField.val().trim()) {
            // Is not empty
            $requestObj.district = districtField.val().toUpperCase();
        }
        let regionField = $('#reqRegion');
        if (regionField.val().trim()) {
            // Is not empty
            $requestObj.region = regionField.val().toUpperCase();
        }
        let countryField = $('#reqCountry');
        if (countryField.val().trim()) {
            // Is not empty
            $requestObj.country = countryField.val().toUpperCase();
        }
    };

    let CREATE_REQUEST_URL = "http://localhost:8080/myWebAdapter_war_exploded/app/rest/createRequest";
    let BTOA_KEY = 'btoa';

    let sendReqButton = $("button#sendReqButton");
    let createRequestSpinner = $('#createRequestSpinner');
    createRequestSpinner.hide();

    $.fn.sendCreateRequest = function($jsonObj){
        let btoaData = sessionStorage[BTOA_KEY];
        $.ajax
        ({
            type: "POST",
            url: CREATE_REQUEST_URL,
            contentType: 'application/json; charset=utf-8',
            data: $jsonObj,
            beforeSend: function (xhr){
                xhr.setRequestHeader('Authorization', btoaData);
                createRequestSpinner.show();
                openCreateRequestModal.prop('disabled', true);
            },
            success: function (){
                alert("Запрос отправлен");
                createRequestSpinner.hide();
                openCreateRequestModal.prop('disabled', false);
            },
            error: function () {
                alert("Ошибка при отправке запроса");
                createRequestSpinner.hide();
                openCreateRequestModal.prop('disabled', false);
            }
        });
    };

    // After send click
    sendReqButton.on('click', function(){
        let allFieldsIsValid = true;

        modalReqForm.find ('input, textarea, select').each(function() {
            if ($(this).hasClass('is-invalid')) {
                alert("Не все поля заполнены");
                allFieldsIsValid = false;
                return false;
            }
        });
        if (allFieldsIsValid) {
            let requestType = requestTypeField.children("option:selected").val();

            let request = {
                author: atob(sessionStorage[BTOA_KEY]).split(":")[0],
                first_name: $("#reqFirstName").val().toUpperCase(),
                family_name: $("#reqFamilyName").val().toUpperCase()
            };
            if (patronymicCheckField.checked) {
                request.patronymic = $("#reqPatronymic").val().toUpperCase();
            }
            request.gender = $('#reqGender').children("option:selected").html().toUpperCase();
            request.birth_date = new Date($('#reqBirthDate').val()).getTime();
            request.request_date = new Date().getTime();

            switch (requestType) {
                // Passport
                case "1":
                    $.fn.fillPassportData(request);
                    break;
                // Birth place
                case "2":
                    $.fn.fillBirthPlaceData(request);
                    break;
                // Both
                case "3":
                    $.fn.fillPassportData(request);
                    $.fn.fillBirthPlaceData(request);
                    break;
            }
            console.log(JSON.stringify(request));
            // Send request to server
            $.fn.sendCreateRequest(JSON.stringify(request));
            // Close form
            modalReqForm.modal('toggle');
        }
    });

});