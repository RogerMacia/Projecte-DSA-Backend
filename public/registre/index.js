
//const BASE_URL = "https://en.wikipedia.org/api/rest_v1/page/"
const BASE_URL = "http://localhost:8080/example/eetacbros/user/register"

function onClearBtnClick() {
    console.log("ClearBtn clicked!");
    $("#usernameTbx").val('');
    $("#nameTbx").val('');
    $("#emailTbx").val('');
    $("#passwordTbx").val('');
    $("#repeatTbx").val('');
}

function onSignUpbtnClick() {
    console.log("signupBtn clicked!");
    if (!checkPassword()) return;
    let username = $("#usernameTbx").val();
    let password = $("#passwordTbx").val();
    let name = $("#nameTbx").val();
    let email = $("#emailTbx").val();
    user = { username:username, nom:name, email:email, password:password};
    buffer = JSON.stringify(user);
    console.log(buffer);
    $.postJSON(BASE_URL, user ,(data, status) => {
        console.log(`Satus: ${status} \n${data}`);
        //$("#res").slideDown("slow");
        $("#res").fadeIn("slow");
        $("#res").text(status);
        setTimeout(function(){ window.location = "login"; }, 1000);
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

function showBubble(text) {
    $("#res").fadeIn("slow");
    $("#res").text(text);
    $("#res").delay(3000).fadeOut("slow"); // https://stackoverflow.com/questions/25005222/fade-out-after-delay-of-5-seconds
}

function checkPassword() {
    console.log("Checking password.");
    let password = $("#passwordTbx").val();
    let repeat = $("#repeatTbx").val();

    same = false;
    enoughLength = false;
    enoughChars = false;

    console.log(password);

    same = (password == repeat); // validacion de que sean el mismo
    enoughLength = password.length >= 6; // validacion de longintud
    enoughChars = (/[a-z]/).test(password) && /[A-Z]/.test(password) && (/[0-9]/.test(password)); // validacion con expresiones regulares.

    if (!same) {
        showBubble("No coincideixen!")
        return false;
    }
    if (!enoughLength) {
        showBubble("Contrasenya masa curta!")
        return false;
    }
    if (!enoughChars) {
        showBubble("La contrasenya té que contindre caracters númerics i lletres majúscules i minúscules!")
        return false;
    }

    return true;
}


$.postJSON = function(url, data, callback) {
    return jQuery.ajax({
    headers: { 
        'Accept': 'application/json',
        'Content-Type': 'application/json' 
    },
    'type': 'POST',
    'url': url,
    'data': JSON.stringify(data),
    'dataType': 'json',
    'success': callback
    });
};