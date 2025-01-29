document.getElementById('registerBtn').addEventListener('click', function() {
    document.querySelector('.welcome-container').style.display = 'none';
    document.getElementById('registrationFormContainer').style.display = 'block';
});

document.getElementById('loginBtn').addEventListener('click', function() {
    document.querySelector('.welcome-container').style.display = 'none';
    document.getElementById('loginFormContainer').style.display = 'block';
});


document.getElementById('backToHomeFromLogin').addEventListener('click', function() {
    document.querySelector('.welcome-container').style.display = 'block';
    document.getElementById('loginFormContainer').style.display = 'none';
});

document.getElementById("backToHomeFromRegister").addEventListener("click", function () {
    window.location.href = "Index.html"; // Replace "Index.html" with the actual homepage file path
});
