function fetchData() {
    fetch('DatabaseServlet')
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json(); // Ensure JSON response is parsed
        })
        .then(data => {
            const tableBody = document.querySelector('#userTable tbody');
            tableBody.innerHTML = ''; // Clear existing data

            if (data.length === 0) {
                tableBody.innerHTML = `<tr><td colspan="11">No data available.</td></tr>`;
                return;
            }

            data.forEach(user => {
                const row = `
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.email}</td>
                        <td>${user.age}</td>
                        <td>${user.phone}</td>
                        <td>${user.address}</td>
                        <td>${user.city}</td>
                        <td>${user.state}</td>
                        <td>${user.zip}</td>
                        <td>${user.country}</td>
                        <td>${user.gender}</td>
                    </tr>
                `;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => {
            console.error('Error fetching data:', error);
            const tableBody = document.querySelector('#userTable tbody');
            tableBody.innerHTML = `<tr><td colspan="11">Failed to fetch data. Please try again later.</td></tr>`;
        });
}

function submitForm() {
    const formData = new FormData(document.getElementById('userForm'));
    const data = {};
    formData.forEach((value, key) => {
        data[key] = value;
    });

    const isUpdate = data.id && data.id.trim() !== '';
    const endpoint = isUpdate ? `DatabaseServlet?id=${data.id}` : 'DatabaseServlet';
    const method = isUpdate ? 'PUT' : 'POST';

    fetch(endpoint, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to save user data');
            }
            return response.json();
        })
        .then(() => {
            alert(isUpdate ? 'User updated successfully!' : 'User added successfully!');
            document.getElementById('userForm').reset(); // Clear the form
            fetchData(); // Refresh user table
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while saving the user data.');
        });
}

function fetchById() {
    const id = document.getElementById("idInput").value.trim();
    if (!id) {
        alert("Please provide a valid ID.");
        return;
    }

    const url = `DatabaseServlet?id=${id}`;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            const user = data[0]; // Assuming the response is an array
            if (user) {
                const resultDiv = document.getElementById("result");
                resultDiv.innerHTML = `
                    <p>ID: ${user.id}</p>
                    <p>Name: ${user.name}</p>
                    <p>Email: ${user.email}</p>
                    <p>Age: ${user.age}</p>
                `;
            } else {
                document.getElementById("result").innerHTML = "<p>No user found.</p>";
            }
        })
        .catch(error => {
            console.error("Error fetching data:", error);
            document.getElementById("result").innerHTML = "<p>Error fetching data.</p>";
        });
}

function updateUser() {
    const formData = new FormData(document.getElementById('userForm'));
    const data = {};
    formData.forEach((value, key) => {
        data[key] = value;
    });

    if (!data.id || data.id.trim() === '') {
        alert('User ID is required for updating.');
        return;
    }

    fetch(`DatabaseServlet?id=${data.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to update user');
            }
            return response.json();
        })
        .then(() => {
            alert(`User with ID ${data.id} updated successfully!`);
            fetchData(); // Refresh user list
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while updating the user.');
        });
}

function navigateToUpdateForm(userId) {
    const url = `DatabaseServlet?id=${userId}`;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch user details');
            }
            return response.json();
        })
        .then(user => {
            if (user.length === 0) {
                alert('No user found with the given ID.');
                return;
            }

            const userDetails = user[0];
            document.getElementById('name').value = userDetails.name;
            document.getElementById('email').value = userDetails.email;
            document.getElementById('age').value = userDetails.age;
            document.getElementById('phone').value = userDetails.phone;
            document.getElementById('address').value = userDetails.address;
            document.getElementById('city').value = userDetails.city;
            document.getElementById('state').value = userDetails.state;
            document.getElementById('zip').value = userDetails.zip;
            document.getElementById('country').value = userDetails.country;
            document.getElementById('gender').value = userDetails.gender;

            let idInput = document.getElementById('userId');
            if (!idInput) {
                idInput = document.createElement('input');
                idInput.type = 'hidden';
                idInput.id = 'userId';
                idInput.name = 'id';
                document.getElementById('userForm').appendChild(idInput);
            }
            idInput.value = userDetails.id;

            document.getElementById('register').scrollIntoView({ behavior: 'smooth' });
        })
        .catch(error => {
            console.error('Error fetching user data:', error);
            alert('Failed to fetch user details.');
        });
}
