//const BASE_URL = "https://en.wikipedia.org/api/rest_v1/page/"
const REGISTER_URL = `${BASE_URL}/user/register`;
console.log("Calling:", REGISTER_URL);

$.postJSON = function(url, data, callback) { // https://stackoverflow.com/questions/40804301/jquery-ajax-post-call-unsupported-media-type
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
    if(!checkUsername()) return;
    if(!checkName()) return;
    if(!checkEmail()) return;
    if(!checkPassword()) return;
    let username = $("#usernameTbx").val();
    let password = $("#passwordTbx").val();
    let name = $("#nameTbx").val();
    let email = $("#emailTbx").val();
    user = { username:username, nom:name, email:email, password:password};
    buffer = JSON.stringify(user);
    console.log(buffer);
    $.postJSON(REGISTER_URL, user ,(data, status) => {
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

function checkUsername()
{
    console.log("Checking username.");
    let username = $("#usernameTbx").val();

    const special_chars = new RegExp("^[0-9A-Za-z]");
    const special_ending = new RegExp("[0-9A-Za-z]$");

    special_chars_test = !special_chars.test(username);
    special_ending_test = !special_ending.test(username);

    if(special_chars_test) 
    {
        //console.log("Invalid username")
        showBubble("Invalid username, it must begin with a non-special character.");
        return false;
    }

    if(special_ending_test)
    {
        showBubble("Invalid username, it must end with a non-special character.");
        return false;
    }

    return true;
}

function checkName()
{
    let name = $("#nameTbx").val();
    console.log("Checking name.")

    if(name === "")
    {
        showBubble("Name field is required!");
        return false;
    }

    return true;
}

function checkEmail()
{
    let email = $("#emailTbx").val();
    console.log("Checking email format.")

    // https://stackoverflow.com/questions/201323/how-can-i-validate-an-email-address-using-a-regular-expression
    const format = new RegExp(/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);

    format_test = format.test(email);

    if(!format_test)
    {
        showBubble("Incorrect email format.");
        return false;
    }
    return true;
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
        showBubble("Passwords don't match!");
        return false;
    }
    if (!enoughLength) {
        showBubble("Too short password!");
        return false;
    }
    if (!enoughChars) {
        showBubble("The password must include numbers, uppercase and lowercase alphabet characters!");
        return false;
    }

    return true;
}