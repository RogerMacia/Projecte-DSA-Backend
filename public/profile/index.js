// Define BASE_URL if not already defined (adjust to your API URL)
const userId = localStorage.getItem("userId");
const username = localStorage.getItem("username");
const coins = localStorage.getItem("coins");
const SHOP_GET_USER_ITEMS_URL = `${BASE_URL}/user/items/${userId}`;

let userData = {
    username: null,
    userId: null,
    memberSince: null,
    coins: 0,
    gamesPlayed: 0,
    wins: 0,
    inventory: [],
    recentGames: [],
    achievements: []
};

// Function to make AJAX GET requests
function getJsonItems(url) {
    return $.ajax({
        url: url,
        dataType: "json",
        method: "GET"
    });
}

function loadUserProfile() {
    userData.userId = userId; // Set userId in userData
    document.getElementById('username').textContent = username;
    document.getElementById('memberSince').textContent = new Date(userData.memberSince).toLocaleDateString();
    document.getElementById('totalCoins').textContent = coins;
    document.getElementById('gamesPlayed').textContent = userData.gamesPlayed;
    document.getElementById('totalWins').textContent = userData.wins;

    const winRate = userData.gamesPlayed > 0
        ? ((userData.wins / userData.gamesPlayed) * 100).toFixed(1)
        : 0;
    document.getElementById('winRate').textContent = winRate + '%';

    loadInventory();
    loadGameResults();
    updateAchievements();
}

function loadInventory() {
    const inventoryGrid = document.getElementById('inventoryGrid');

    // Show loading state
    inventoryGrid.innerHTML = '<div class="empty-state"><p>Loading inventory...</p></div>';

    // Check if we have userId
    if (!userData.userId) {
        inventoryGrid.innerHTML = '<div class="empty-state"><p>Unable to load inventory.<br>Please refresh the page.</p></div>';
        return;
    }

    // Fetch inventory items from API
    getJsonItems(SHOP_GET_USER_ITEMS_URL)
        .done(function(data) {
            userData.inventory = data || [];

            if (userData.inventory.length === 0) {
                inventoryGrid.innerHTML = '<div class="empty-state"><p>No items yet!<br>Visit the shop to buy items.</p></div>';
                return;
            }

            inventoryGrid.innerHTML = '';
            userData.inventory.forEach(item => {
                const itemDiv = document.createElement('div');
                itemDiv.className = 'inventory-item';
                itemDiv.innerHTML = `
                            <div class="item-icon">${item.emoji}</div>
                            <div class="item-name">${item.name}</div>
                            <div class="item-quantity">${item.quantity}</div>
                        `;
                inventoryGrid.appendChild(itemDiv);
            });
        })
        .fail(function(err) {
            console.error("Error fetching user inventory:", err);
            inventoryGrid.innerHTML = '<div class="empty-state"><p>Error loading inventory.<br>Please try again later.</p></div>';
        });
}

function loadGameResults() {
    const container = document.getElementById('gameResultsContainer');

    if (!userData.recentGames || userData.recentGames.length === 0) {
        container.innerHTML = '<div class="empty-state"><p>No games played yet!<br>Start playing to see your results.</p></div>';
        return;
    }

    container.innerHTML = '';
    userData.recentGames.slice(0, 5).forEach(game => {
        const gameDiv = document.createElement('div');
        gameDiv.className = 'game-result';
        gameDiv.innerHTML = `
                    <div class="result-header">
                        <span class="game-name">${game.name}</span>
                        <span class="result-badge ${game.result}">${game.result.toUpperCase()}</span>
                    </div>
                    <div class="result-details">
                        Score: ${game.score} • ${new Date(game.date).toLocaleDateString()}
                    </div>
                `;
        container.appendChild(gameDiv);
    });
}

function updateAchievements() {
    const achievements = document.querySelectorAll('.achievement');
    const achievementMap = {
        0: 'first_steps',
        1: 'first_win',
        2: 'coin_collector',
        3: 'shopaholic',
        4: 'win_streak',
        5: 'champion'
    };

    achievements.forEach((achievement, index) => {
        if (userData.achievements && userData.achievements.includes(achievementMap[index])) {
            achievement.classList.add('unlocked');
        }
    });
}

function onLogoutClick() {
    localStorage.removeItem("userId");
    localStorage.removeItem("username");
    localStorage.removeItem("coins"); // Clear coins on logout
    window.location.href = "../login"; // Canvia la ruta si el login està en un altre lloc
}

$(document).ready(function() {
    loadUserProfile();
    $("#logoutBtn").click(onLogoutClick);
    $("#shopBtn").click(function() {
        window.location.href = "../shop";
    });
    $("#settingsBtn").click(function() {
        window.location.href = "../settings";
    });
});