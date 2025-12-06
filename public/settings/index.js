// Load current user data
const currentUserData = {
    username: localStorage.getItem("username") || "Player1",
    email: "player1@eetacbros.com",
    name: "The Amazing Player",
    emailNotifications: "enabled",
    profileVisibility: "public",
    soundEffects: "enabled",
    coins: localStorage.getItem("coins") || 0
};

// Initialize form with current data
function loadUserData() {
    document.getElementById('username').value = currentUserData.username;
    document.getElementById('email').value = currentUserData.email;
    document.getElementById('name').value = currentUserData.name;

    // Set radio buttons
    document.querySelector(`input[name="emailNotifications"][value="${currentUserData.emailNotifications}"]`).checked = true;
    document.querySelector(`input[name="profileVisibility"][value="${currentUserData.profileVisibility}"]`).checked = true;
    document.querySelector(`input[name="soundEffects"][value="${currentUserData.soundEffects}"]`).checked = true;
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
    const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);

    return minLength && hasUpperCase && hasNumber && hasSpecialChar;
}

// Save changes
document.getElementById('saveBtn').addEventListener('click', async function() {
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
        id: localStorage.getItem("userId"),
        username: username,
        name: name,
        email: email,
        password: newPassword
    };

    try {
        const response = await fetch(`${BASE_URL}/example/eetacbros/user/update`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updateData)
        });

        if (!response.ok) {
            throw new Error('Failed to update profile');
        }

        // Clear password fields
        document.getElementById('newPassword').value = '';
        document.getElementById('confirmPassword').value = '';

        showNotification('✓ Settings saved successfully!', 'success');
    } catch (error) {
        console.error('Error updating profile:', error);
        showNotification('Failed to save changes. Please try again.', 'error');
    }
});

// Clear changes
document.getElementById('clearBtn').addEventListener('click', function() {
    if (confirm('Are you sure you want to clear all changes?')) {
        loadUserData();
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

document.getElementById('confirmDeleteBtn').addEventListener('click', async function(e) {
    e.preventDefault();

    // TODO: Replace with actual API call
    try {
        // Simulated API call
        console.log('Deleting account...');

        // Example API call (uncomment and modify when ready):
        /*
        const response = await fetch('/api/user/delete', {
            method: 'DELETE',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        });

        if (!response.ok) {
            throw new Error('Failed to delete account');
        }
        */

        deleteDialog.close();
        showNotification('Account deleted successfully', 'success');

        // Redirect to login after 2 seconds
        setTimeout(() => {
            window.location.href = '/login';
        }, 2000);
    } catch (error) {
        console.error('Error deleting account:', error);
        showNotification('Failed to delete account. Please try again.', 'error');
    }
});

// Initialize page
loadUserData();

// Event listener for the profile button
document.getElementById('profileBtn').addEventListener('click', function() {
    window.location.href = '../profile';
});