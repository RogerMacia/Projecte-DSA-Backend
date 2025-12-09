const UPDATE_URL = `${BASE_URL}/user/update`;

const DELETE_USER_URL = `${BASE_URL}/user/delete/{id}`;

// Initialize form with current data
function loadUserData() {
    document.getElementById('username').value = localStorage.getItem("username") || '';
    document.getElementById('email').value = localStorage.getItem("email") || '';
    document.getElementById('name').value = localStorage.getItem("name") || '';
    document.getElementById('coins').value = localStorage.getItem("coins") || '';
}

// Show notification
function showNotification(message, type = 'success') {
    const notification = document.getElementById('notification');
    const notificationText = document.getElementById('notificationText');

    notificationText.textContent = message;
    notification.className = `notification ${type}`;
    notification.classList.remove('hidden');

    setTimeout(() => {
        notification.classList.add('hidden');
    }, 3000);
}

// Validate password
function validatePassword(password) {
    const minLength = password.length >= 8;
    const hasUpperCase = /[A-Z]/.test(password);
    const hasNumber = /[0-9]/.test(password);

    return minLength && hasUpperCase && hasNumber;
}

// Save changes
document.getElementById('saveBtn').addEventListener('click', function() {
    const username = document.getElementById('username').value.trim();
    const email = document.getElementById('email').value.trim();
    const name = document.getElementById('name').value.trim();
    const newPassword = document.getElementById('newPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    // Validation
    if (!username) {
        showNotification('Username cannot be empty', 'error');
        return;
    }

    if (!email || !email.includes('@')) {
        showNotification('Please enter a valid email address', 'error');
        return;
    }

    if (newPassword) {
        if (newPassword !== confirmPassword) {
            showNotification('New passwords do not match', 'error');
            return;
        }

        if (!validatePassword(newPassword)) {
            showNotification('Password does not meet requirements', 'error');
            return;
        }
    }

    // Prepare data to send to backend
    const updateData = {
        id: parseInt(localStorage.getItem("userId")),
        username: username,
        name: name,
        email: email,
        password: newPassword

    };


    console.log("Sending update data:", updateData); // Log the data being sent

    $.ajax({
        url: UPDATE_URL,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(updateData)
    })
    .done(function(data) {
        // Update local storage
        localStorage.setItem('username', username);
        localStorage.setItem('name', name);
        localStorage.setItem('email', email);

        // Clear password fields
        document.getElementById('newPassword').value = '';
        document.getElementById('confirmPassword').value = '';

        showNotification('✓ Settings saved successfully!', 'success');
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        console.error(`Error saving settings: ${textStatus}`, errorThrown);
        showNotification('Failed to save changes. Please try again.', 'error');
    });
});

// Clear changes
document.getElementById('clearBtn').addEventListener('click', function() {
    if (confirm('Are you sure you want to clear all changes?')) {
        document.getElementById('currentPassword').value = '';
        document.getElementById('newPassword').value = '';
        document.getElementById('confirmPassword').value = '';
        showNotification('Changes cleared', 'success');
    }
});

// Delete account
const deleteDialog = document.getElementById('deleteDialog');

document.getElementById('deleteAccountBtn').addEventListener('click', function() {
    deleteDialog.showModal();
});

document.getElementById('confirmDeleteBtn').addEventListener('click', function(e) {
    e.preventDefault();

    const userId = localStorage.getItem("userId");

    if (!userId) {
        showNotification('User ID not found. Please log in again.', 'error');
        return;
    }

    const deleteUrl = DELETE_USER_URL.replace('{id}', userId);

    $.ajax({
        url: deleteUrl,
        type: 'DELETE',
        contentType: 'application/json'
    })
    .done(function(data) {
        deleteDialog.close();
        showNotification('Account deleted successfully', 'success');

        // Clear local storage and redirect to login
        localStorage.clear();
        setTimeout(() => {
            window.location.href = '../login';
        }, 2000);
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        console.error(`Error deleting account: ${textStatus}`, errorThrown);
        showNotification('Failed to delete account. Please try again.', 'error');
    });
});


// Event listener for the profile button
document.getElementById('profileBtn').addEventListener('click', function() {
    window.location.href = '../profile';
});

// Load user data when the page loads
document.addEventListener('DOMContentLoaded', loadUserData);