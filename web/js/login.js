// Session storage keys
var IS_AUTHORIZED_KEY = 'isAuthorized';
var BTOA_KEY = 'btoa';

$(document).ready(function(){
    const $mainContent = $("div#mainContent");
    const $loginForm = $("div#loginForm");
    const $lbSpinner = $("#loginButtonSpinner");
    const $loginButton = $("#loginButton");
    const $logoutButton = $("#logoutButton");
    $lbSpinner.hide();


    if (sessionStorage[IS_AUTHORIZED_KEY] != null) {
        const isAuthorized = sessionStorage[IS_AUTHORIZED_KEY];
        if (isAuthorized === "false") {
            // If not authorized
            $mainContent.hide();
            $loginForm.show();
        } else {
            $mainContent.show();
            $loginForm.hide();
            requestsTableInit();
        }
    } // If page opened first time
    else {
        $mainContent.hide();
        sessionStorage[IS_AUTHORIZED_KEY] = false;
    }

    $loginButton.click(function(e){
        e.preventDefault();
        const LOGIN_GET_REQUEST_URL = "http://localhost:8080/myWebAdapter_war_exploded/app/rest/login";
        const $loginField = $("#loginField");
        const $passwordField = $("#passwordField");
        const btoaData = btoa($loginField.val() + ":" + $passwordField.val());

        // Authorization
        if ($loginField.val() !== "" && $passwordField.val() !== "") {
            $.ajax
            ({
                type: "GET",
                url: LOGIN_GET_REQUEST_URL,
                beforeSend: function (xhr){
                    xhr.setRequestHeader('Authorization', btoaData);
                    // Disable login button and turn on it's spinner
                    $loginButton.prop('disabled', true);
                    $lbSpinner.show();
                },
                success: function (){
                    // Remove 'is-valid' class from input fields
                    $loginField.removeClass('is-invalid');
                    $passwordField.removeClass('is-invalid');
                    // Enable login button and turn off it's spinner
                    $loginButton.prop('disabled', false);
                    $lbSpinner.hide();

                    $loginForm.hide();
                    $mainContent.show();

                    sessionStorage[IS_AUTHORIZED_KEY] = true;
                    sessionStorage[BTOA_KEY] = btoaData;

                    // Init requests table
                    requestsTableInit();
                },
                error: function () {
                    $loginField.addClass('is-invalid');
                    $passwordField.addClass('is-invalid');
                    // Enable login button and turn off it's spinner
                    $loginButton.prop('disabled', false);
                    $lbSpinner.hide();
                }
            });
        } else {
            $loginField.addClass('is-invalid');
            $passwordField.addClass('is-invalid');
        }
    });

    $logoutButton.click(function (e) {
        e.preventDefault();
        sessionStorage[IS_AUTHORIZED_KEY] = false;
        sessionStorage.removeItem(BTOA_KEY);
        location.reload();
    });
});