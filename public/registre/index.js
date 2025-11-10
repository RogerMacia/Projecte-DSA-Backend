
const BASE_URL = "https://en.wikipedia.org/api/rest_v1/page/"

function onClearBtnClick() {
    console.log("ClearBtn clicked!");
    $("#usernameTbx").val('');
    $("#passwordTbx").val('');
}

function onSignUpbtnClick()
{
    console.log("signupBtn clicked!");
    let username = $("#usernameTbx").val();
    let password = $("#passwordTbx").val();
    $.get(BASE_URL, (data,status) => {
        console.log(`Satus: ${status} \n${data}`);
        //$("#res").slideDown("slow");
        $("#res").fadeIn("slow");
        $("#res").text(data);
    });
}

function onReadyDocument() {
    //$(".app").hide();
    console.log("Initializing...");
    //$("#app").hover()
    //$("#app").hide();
    $("#app").slideUp("slow");
    $("#app").show("slow");
    $("#clearBtn").click(onClearBtnClick);
    $("#signupBtn").click(onSignUpbtnClick);
    //console.log($("#app").html());
}