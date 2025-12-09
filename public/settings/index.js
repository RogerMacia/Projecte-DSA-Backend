// ============================================================================
// CONSTANTS
// ============================================================================

const UPDATE_URL = `${BASE_URL}/user/update`;
const DELETE_USER_URL = `${BASE_URL}/user/delete/{id}`;

const NOTIFICATION_DURATION = 3000;
const REDIRECT_DELAY = 2000;

const PASSWORD_REQUIREMENTS = {
    minLength: 8,
    hasUpperCase: /[A-Z]/,
    hasNumber: /[0-9]/
};


// ============================================================================
// USER DATA MANAGEMENT
// ============================================================================

function loadUserData() {
    const fields = ['username', 'email', 'name', 'coins'];
    fields.forEach(field => {
        const element = document.getElementById(field);
        if (element) {
            element.value = localStorage.getItem(field) || '';
        }
    });
}

function saveUserDataToLocalStorage(username, name, email) {
    localStorage.setItem('username', username);
    localStorage.setItem('name', name);
    localStorage.setItem('email', email);
}

function getUserIdFromStorage() {
    return localStorage.getItem('userId');
}


// ============================================================================
// VALIDATION
// ============================================================================

function validateEmail(email) {
    return email && email.includes('@');
}

function validatePassword(password) {
    const meetsLength = password.length >= PASSWORD_REQUIREMENTS.minLength;
    const hasUpperCase = PASSWORD_REQUIREMENTS.hasUpperCase.test(password);
    const hasNumber = PASSWORD_REQUIREMENTS.hasNumber.test(password);

    return meetsLength && hasUpperCase && hasNumber;
}

function validateUserInput(username, email, newPassword, confirmPassword) {
    if (!username) {
        showNotification('Username cannot be empty', 'error');
        return false;
    }

    if (!validateEmail(email)) {
        showNotification('Please enter a valid email address', 'error');
        return false;
    }

    if (newPassword) {
        if (newPassword !== confirmPassword) {
            showNotification('New passwords do not match', 'error');
            return false;
        }

        if (!validatePassword(newPassword)) {
            showNotification('Password does not meet requirements', 'error');
            return false;
        }
    }

    return true;
}


// ============================================================================
// UI NOTIFICATIONS
// ============================================================================

function showNotification(message, type = 'success') {
    const notification = document.getElementById('notification');
    const notificationText = document.getElementById('notificationText');

    notificationText.textContent = message;
    notification.className = `notification ${type}`;
    notification.classList.remove('hidden');

    setTimeout(() => {
        notification.classList.add('hidden');
    }, NOTIFICATION_DURATION);
}


// ============================================================================
// FORM HANDLING
// ============================================================================

function getFormValues() {
    return {
        username: document.getElementById('username').value.trim(),
        email: document.getElementById('email').value.trim(),
        name: document.getElementById('name').value.trim(),
        newPassword: document.getElementById('newPassword').value,
        confirmPassword: document.getElementById('confirmPassword').value
    };
}

function clearPasswordFields() {
    document.getElementById('newPassword').value = '';
    document.getElementById('confirmPassword').value = '';

    const currentPasswordField = document.getElementById('currentPassword');
    if (currentPasswordField) {
        currentPasswordField.value = '';
    }
}

function buildUpdateData(username, name, email, password) {
    return {
        id: parseInt(getUserIdFromStorage()),
        username,
        name,
        email,
        password
    };
}


// ============================================================================
// API CALLS
// ============================================================================

function updateUserSettings(updateData) {
    return $.ajax({
        url: UPDATE_URL,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(updateData)
    });
}

function deleteUserAccount(userId) {
    const deleteUrl = DELETE_USER_URL.replace('{id}', userId);

    return $.ajax({
        url: deleteUrl,
        type: 'DELETE',
        contentType: 'application/json'
    });
}


// ============================================================================
// EVENT HANDLERS
// ============================================================================

function handleSaveSettings() {
    const { username, email, name, newPassword, confirmPassword } = getFormValues();

    if (!validateUserInput(username, email, newPassword, confirmPassword)) {
        return;
    }

    const updateData = buildUpdateData(username, name, email, newPassword);
    console.log('Sending update data:', updateData);

    updateUserSettings(updateData)
        .done(() => {
            saveUserDataToLocalStorage(username, name, email);
            clearPasswordFields();
            showNotification('✓ Settings saved successfully!', 'success');
        })
        .fail((jqXHR, textStatus, errorThrown) => {
            console.error(`Error saving settings: ${textStatus}`, errorThrown);
            showNotification('Failed to save changes. Please try again.', 'error');
        });
}

function handleClearChanges() {
    if (confirm('Are you sure you want to clear all changes?')) {
        clearPasswordFields();
        showNotification('Changes cleared', 'success');
    }
}

function handleDeleteAccount(e) {
    e.preventDefault();

    const userId = getUserIdFromStorage();

    if (!userId) {
        showNotification('User ID not found. Please log in again.', 'error');
        return;
    }

    deleteUserAccount(userId)
        .done(() => {
            closeDeleteDialog();
            showNotification('Account deleted successfully', 'success');
            redirectToLogin();
        })
        .fail((jqXHR, textStatus, errorThrown) => {
            console.error(`Error deleting account: ${textStatus}`, errorThrown);
            showNotification('Failed to delete account. Please try again.', 'error');
        });
}

function handleProfileNavigation() {
    window.location.href = '../profile';
}


// ============================================================================
// DIALOG MANAGEMENT
// ============================================================================

function showDeleteDialog() {
    const deleteDialog = document.getElementById('deleteDialog');
    deleteDialog.showModal();
}

function closeDeleteDialog() {
    const deleteDialog = document.getElementById('deleteDialog');
    deleteDialog.close();
}


// ============================================================================
// NAVIGATION
// ============================================================================

function redirectToLogin() {
    localStorage.clear();
    setTimeout(() => {
        window.location.href = '../login';
    }, REDIRECT_DELAY);
}


// ============================================================================
// INITIALIZATION
// ============================================================================

function initializeEventListeners() {
    document.getElementById('saveBtn').addEventListener('click', handleSaveSettings);
    document.getElementById('clearBtn').addEventListener('click', handleClearChanges);
    document.getElementById('deleteAccountBtn').addEventListener('click', showDeleteDialog);
    document.getElementById('confirmDeleteBtn').addEventListener('click', handleDeleteAccount);
    document.getElementById('profileBtn').addEventListener('click', handleProfileNavigation);
}

function initialize() {
    loadUserData();
    initializeEventListeners();
}

document.addEventListener('DOMContentLoaded', initialize);