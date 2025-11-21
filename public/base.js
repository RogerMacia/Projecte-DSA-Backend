let BASE_URL;

if (window.location.host.includes("localhost")) {
    BASE_URL = "http://localhost:8080/example/eetacbros";
} else {
    BASE_URL = "https://dsa3.upc.edu/example/eetacbros";
}