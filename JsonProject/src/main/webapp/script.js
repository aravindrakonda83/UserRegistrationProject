$(document).ready(function () {
    $("#user-form").on("submit", function (event) {
        event.preventDefault(); // Prevent the default form submission
        

        // Get form data
        const formData = {
            name: $("#name").val(),
            age: $("#age").val(),
            email: $("#email").val(),
        };

        console.log("Form Data:", formData);  // Check the data being sent

        // Add the new data to the table with animation
        const newRow = `
            <tr>
                <td>${formData.name}</td>
                <td>${formData.age}</td>
                <td>${formData.email}</td>
            </tr>
        `;
        $("#data-table tbody").append($(newRow).hide().fadeIn(1000));

        // Send the data to the servlet via AJAX
        $.ajax({
            url: "http://localhost:4545/JsonProject/ServletData", 
            type: "POST",
            contentType: "application/json", 
            data: JSON.stringify(formData),
            success: function (response) {
                console.log("Response:", response);  
				

                // If the response status is 'success', update the table
                if (response.status === "success") {
                    console.log(response.message); // Log success message from server

                    // Clear existing rows in the table
                    $("#data-table tbody").empty();

                    // Append new rows from the server response
                    response.data.forEach((item) => {
                        const row = `<tr>
                            <td>${item.name}</td>
                            <td>${item.age}</td>
                            <td>${item.email}</td>
                        </tr>`;
                        $("#data-table tbody").append($(row).hide().fadeIn(1000));
                    });
                } else {
                    // Handle the error message if status is not 'success'
                    alert("Error: " + response.message);
                }
            },
            error: function (xhr) {
                console.error("Error:", xhr.responseText);
                alert("An error occurred: " + xhr.responseText);
            },
        });

        // Clear the form
        this.reset();
    });
});
