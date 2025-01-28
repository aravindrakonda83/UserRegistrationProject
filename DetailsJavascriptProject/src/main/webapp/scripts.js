// Function to submit the form data via AJAX
function submitForm() {
    // Get form data
    var name = document.getElementById('name').value;
    var email = document.getElementById('email').value;

    // Simple validation
    if (name === "" || email === "") {
        alert("Both fields are required!");
        return;
    }

    // Create the data object to send
    var data = {
        name: name,
        email: email
    };

    // Create a new XMLHttpRequest (AJAX Call)
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "MyServlet", true);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Process the response from the servlet
            document.getElementById('response').innerHTML = xhr.responseText;
        }
    };

    // Send the data as a JSON string
    xhr.send(JSON.stringify(data));
}

// Function to get data from the servlet and display on the webpage
function getData() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "GetData", true);

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var data = JSON.parse(xhr.responseText);
            var htmlContent = "<ul>";
            data.forEach(function(user) {
                htmlContent += "<li>" + user.name + " - " + user.email + "</li>";
            });
            htmlContent += "</ul>";

            // Display the data on the page
            document.getElementById('response').innerHTML = htmlContent;
        }
    };

    xhr.send();
}
