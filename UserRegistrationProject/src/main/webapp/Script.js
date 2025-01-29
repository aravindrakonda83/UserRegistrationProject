// script.js

document.addEventListener("DOMContentLoaded", () => {
    // Fetch countries from the server on page load
    fetch('CountryStateServlet?countries=true')
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to load countries');
            }
            return response.json();
        })
        .then(data => {
            const countryDropdown = document.getElementById('country');
            // Clear any existing options before adding new ones
            countryDropdown.innerHTML = '<option value="">Select a country</option>';
            
            // Populate countries
            data.forEach(country => {
                const option = document.createElement('option');
                option.value = country.name;
                option.textContent = country.name;
                countryDropdown.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error loading countries:', error);
        });
});

// Load states when a country is selected
function loadStates() {
    const country = document.getElementById('country').value;
    const stateDropdown = document.getElementById('state');
    stateDropdown.innerHTML = '<option value="">Select a state</option>'; // Reset states dropdown
    
    if (country) {
        // Fetch states for the selected country
        fetch(`CountryStateServlet?country=${encodeURIComponent(country)}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to load states');
                }
                return response.json();
            })
            .then(data => {
                data.forEach(state => {
                    const option = document.createElement('option');
                    option.value = state.name;
                    option.textContent = state.name;
                    stateDropdown.appendChild(option);
                });
            })
            .catch(error => {
                console.error('Error loading states:', error);
            });
    }
}

// Fetch data from DatabaseServlet
function fetchData() {
    fetch('DatabaseServlet')
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch data');
            }
            return response.text();
        })
        .then(data => {
            document.getElementById('dataContainer').innerHTML = data;
        })
        .catch(error => {
            console.error('Error fetching data:', error);
            document.getElementById('dataContainer').innerHTML = `<p>Error: ${error.message}</p>`;
        });
}

// Submit form data
function submitForm() {
    const form = document.getElementById('userForm');
    const state = document.getElementById('state').value;

    // Check if state is selected
    if (!state) {
        alert('Please select a state.');
        return;
    }

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
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to submit form');
            }
            return response.text();
        })
        .then(data => {
            alert(data);
            form.reset();
        })
        .catch(error => {
            console.error('Error submitting form:', error);
            alert('Failed to add user.');
        });
}
