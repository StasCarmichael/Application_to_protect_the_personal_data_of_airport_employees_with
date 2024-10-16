document.addEventListener('DOMContentLoaded', function () {
    const searchPageButton = document.getElementById('searchPage');
    const newEmployeePageButton = document.getElementById('newEmployeePage');
    const searchForm = document.getElementById('searchForm');
    const newEmployeeForm = document.getElementById('newEmployeeForm');
    const editEmployeeForm = document.getElementById('editEmployeeForm');
    const resultsTable = document.getElementById('resultsTable').querySelector('tbody');
    const searchEmployeeName = document.getElementById('searchEmployeeName');
    const searchEmployeePosition = document.getElementById('searchEmployeePosition');
    const searchEmployeeDepartment = document.getElementById('searchEmployeeDepartment');
    const searchEmployeeBirthDate = document.getElementById('searchEmployeeBirthDate');
    const currentPageDisplay = document.getElementById('currentPage');
    const totalPagesDisplay = document.getElementById('totalPages');
    const previousButton = document.getElementById('previousPage');
    const nextButton = document.getElementById('nextPage');
    const confirmDeleteButton = document.getElementById('confirmDelete');
    const deleteModal = document.getElementById('deleteModal');
    const deleteEmployeeIdInput = document.getElementById('deleteEmployeeId');
    const searchEmployeeEmail = document.getElementById('searchEmployeeEmail');
    const searchEmployeePhone = document.getElementById('searchEmployeePhone');

    // Додаємо eventListener для полів введення
    searchEmployeeName.addEventListener('input', handleSearchInput);
    searchEmployeePosition.addEventListener('input', handleSearchInput);
    searchEmployeeBirthDate.addEventListener('input', handleSearchInput);
    searchEmployeeDepartment.addEventListener('change', handleSearchInput);

    searchEmployeeEmail.addEventListener('input', handleSearchInput);
    searchEmployeePhone.addEventListener('input', handleSearchInput);

    // Функція, яка викликається при зміні вмісту поля введення або вибірки
    function handleSearchInput() {
        currentPage = 1;
        searchEmployees();
    }


    // searchPageButton.addEventListener('click', function () {
    //     showPage(searchForm);
    //     searchEmployees();
    // });

    // newEmployeePageButton.addEventListener('click', function () {
    //     showPage(newEmployeeForm);
    //     searchEmployees();
    // });




    // Initial search on page load (you may modify this based on your needs)
    searchEmployees();

});

let currentPage = 1;
const itemsPerPage = 5; // Adjust as needed

let isAdmin = false; // Change to true for admin access
const baseUrl = 'http://localhost:8080/public/api/employees';

function nextPage() {
    currentPage++;
    searchEmployees(); // Викликаємо функцію для пошуку співробітників
    updatePaginationInfo();
}

function previousPage() {
    if (currentPage > 1) {
        currentPage--;
        searchEmployees(); // Викликаємо функцію для пошуку співробітників
    }
    updatePaginationInfo();
}

function showPage(page) {
    searchForm.style.display = 'none';
    newEmployeeForm.style.display = 'none';
    editEmployeeForm.style.display = 'none';
    page.style.display = 'block';
}

function updatePaginationInfo() {
    document.getElementById('pageInfo').innerText = `Page ${currentPage} of ${totalPages}`;
}

function addEmployee() {
    const employee = {
        name: document.getElementById('newEmployeeName').value,
        position: document.getElementById('newEmployeePosition').value,
        department: document.getElementById('newEmployeeDepartment').value,
        email: document.getElementById('newEmployeeEmail').value,
        phone: document.getElementById('newEmployeePhone').value,
        hireDate: document.getElementById('newEmployeeHireDate').value,
    };

    fetch(`${baseUrl}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(employee),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to add employee');
            }
            return response.json();
        })
        .then(data => {
            console.log('Employee added successfully:', data);
            // Reset form
            document.getElementById('employeeForm').reset();
            // Show search page after adding
            showPage(searchForm);
            // Reload search results
            searchEmployees();
        })
        .catch(error => {
            console.error('Error adding employee:', error.message);
        });
}

function editEmployee(id) {
    fetch(`${baseUrl}/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch employee');
            }
            return response.json();
        })
        .then(employee => {
            // Populate edit form with employee data
            document.getElementById('editEmployeeId').value = employee.id;
            document.getElementById('editEmployeeName').value = employee.name;
            document.getElementById('editEmployeePosition').value = employee.position;
            document.getElementById('editEmployeeDepartment').value = employee.department;
            document.getElementById('editEmployeeEmail').value = employee.email;
            document.getElementById('editEmployeePhone').value = employee.phone;
            document.getElementById('editEmployeeHireDate').value = employee.hireDate;

            // Display edit form
            showPage(editEmployeeForm);
        })
        .catch(error => {
            console.error('Error fetching employee:', error.message);
        });
}

function updateEmployee() {
    const id = document.getElementById('editEmployeeId').value;
    const employee = {
        name: document.getElementById('editEmployeeName').value,
        position: document.getElementById('editEmployeePosition').value,
        department: document.getElementById('editEmployeeDepartment').value,
        email: document.getElementById('editEmployeeEmail').value,
        phone: document.getElementById('editEmployeePhone').value,
        hireDate: document.getElementById('editEmployeeHireDate').value,
    };

    fetch(`${baseUrl}/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(employee),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to update employee');
            }
            return response.json();
        })
        .then(data => {
            console.log('Employee updated successfully:', data);
            // Reset form
            document.getElementById('editForm').reset();
            // Show search page after updating
            showPage(searchForm);
            // Reload search results
            searchEmployees();
        })
        .catch(error => {
            console.error('Error updating employee:', error.message);
        });
}

function cancelEdit() {
    // Reset form and show search page
    document.getElementById('editForm').reset();
    showPage(searchForm);
}

function deleteEmployee(id) {
    fetch(`${baseUrl}/${id}`, {
        method: 'DELETE',
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to delete employee');
            }
            return response.json();
        })
        .then(data => {
            console.log('Employee deleted successfully:', data);
            // Reload search results
            searchEmployees();
        })
        .catch(error => {
            console.error('Error deleting employee:', error.message);
        });
}



function confirmDelete(employeeId) {
    // Show the delete confirmation modal
    deleteEmployeeIdInput.value = employeeId;
    $('#deleteModal').modal('show');
}





function searchEmployees() {
    const params = new URLSearchParams({
        page: currentPage,
        per_page: itemsPerPage,
        name: searchEmployeeName.value.toLowerCase(),
        position: searchEmployeePosition.value.toLowerCase(),
        department: searchEmployeeDepartment.value,
        birthDate: searchEmployeeBirthDate.value,
        email: searchEmployeeEmail.value,
        phone: searchEmployeePhone.value,
    });

    fetch(`${baseUrl}?${params}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch employees');
            }
            return response.json();
        })
        .then(data => {
            console.log(data); // Перевірка структури даних
            // Якщо дані є масивом, обробляємо їх відповідно
            if (Array.isArray(data)) {
                displaySearchResults(data);
            } else {
                const { results, currentPage, totalPages } = data;
                displaySearchResults(results);
                currentPageDisplay.textContent = currentPage;
                totalPagesDisplay.textContent = totalPages;
                previousButton.disabled = currentPage === 1;
                nextButton.disabled = currentPage === totalPages || totalPages === 0;
            }
        })
        .catch(error => {
            console.error('Error fetching employees:', error.message);
        });

    // Отримання загальної кількості сторінок
    fetch(`${baseUrl}/count/`+itemsPerPage)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch total pages');
            }
            return response.json();
        })
        .then(totalPagesData => {
            totalPages = totalPagesData.totalPages;
            updatePaginationInfo()
        })
        .catch(error => {
            console.error('Error fetching total pages:', error.message);
        });
}


function displaySearchResults(results) {
    const resultsTableBody = document.querySelector('#resultsTable tbody');
    resultsTableBody.innerHTML = ''; // Очищуємо таблицю перед додаванням нових даних

    if (results && results.length > 0) {
        results.forEach(employee => {
            const row = document.createElement('tr');
            row.innerHTML = `
               <td>${employee.name}</td>
               <td>${employee.position}</td>
               <td>${employee.department}</td>
               <td>${employee.email}</td>
               <td>${employee.phone}</td>
               <td>${employee.hireDate}</td>
               <td>${employee.birthDate}</td>

           `;
            resultsTableBody.appendChild(row);
        });
    } else {
        const row = document.createElement('tr');
        row.innerHTML = '<td colspan="7">No employees found.</td>';
        resultsTableBody.appendChild(row);
    }
}

