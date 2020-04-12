$(document).ready(function(){
    $("#createRequestForm").hide();

    $("#loginButton").click(function(e){
        e.preventDefault();
        if ($("#loginId").val() !== "" && $("#passwordId").val() !== "") {
            $("#loginForm").hide();
            $("#createRequestForm").show();
        }
    });
});