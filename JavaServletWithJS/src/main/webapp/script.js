function showPage(pageId) {
    const pages = ['welcomePage', 'registerPage', 'loginPage'];
    pages.forEach(page => {
        document.getElementById(page).classList.add('d-none');
    });
    document.getElementById(pageId).classList.remove('d-none');
}


function fetchData() {
    fetch('DatabaseServlet')
        .then(response => response.text())
        .then(data => {
            document.getElementById('dataContainer').innerHTML = data;
        })
        .catch(error => {
            console.error('Error fetching data:', error);
            document.getElementById('dataContainer').innerHTML = `<p>Error: ${error.message}</p>`;
        });
}

function submitForm() {
    const form = document.getElementById('userForm');
    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }

    const formData = new URLSearchParams();
    const inputs = document.querySelectorAll('#userForm input, #userForm select');
    inputs.forEach(input => formData.append(input.name, input.value));

    fetch('DatabaseServlet', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: formData.toString(),
    })
        .then(response => response.text())
        .then(data => {
            alert(data);
            form.reset();
        })
        .catch(error => {
            console.error('Error submitting form:', error);
            alert('Failed to add user.');
        });
}


function fetchById() {
    const id = document.getElementById("idInput").value;
    const url = `DatabaseServlet?id=${id}`;

    fetch(url)
        .then(response => response.text())
        .then(data => {
            document.getElementById("result").innerHTML = data;
        })
        .catch(error => {
            console.error("Error fetching data:", error);
            document.getElementById("result").innerHTML = "<p>Error fetching data.</p>";
        });
}

