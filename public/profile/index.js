// Define BASE_URL if not already defined (adjust to your API URL)
const userId = localStorage.getItem("userId");
const username = localStorage.getItem("username");
const coins = localStorage.getItem("coins");
const score = localStorage.getItem("score");
const SHOP_GET_USER_ITEMS_URL = `${BASE_URL}/user/items`;
const RANKING_URL = `${BASE_URL}/ranking`;

let userData = {
    username: null,
    userId: null,
    memberSince: null,
    coins: 0,
    score: 0,
    gamesPlayed: 0,
    wins: 0,
    inventory: [],
    recentGames: [],
    achievements: []
};

// Function to make AJAX GET requests
function getJson(url) {
    return $.ajax({
        url: url,
        dataType: "json",
        method: "GET"
    });
}

function loadRanking() {
    getJson(`${RANKING_URL}/${userId}`)
        .done(function(data) {
            const rankingBody = $('#ranking-body');
            rankingBody.empty();

            // Helper function to get medal emoji for top 3
            function getMedalEmoji(position) {
                if (position === 1) return '🥇';
                if (position === 2) return '🥈';
                if (position === 3) return '🥉';
                return '';
            }

            // Helper function to format large numbers
            function formatScore(score) {
                if (score >= 1000000) return (score / 1000000).toFixed(1) + 'M';
                return score;
            }

            // Display podium entries
            data.podium.forEach(user => {
                const isCurrentUser = user.username === username;
                let rowClass = '';

                if (isCurrentUser) {
                    rowClass = 'is-success current-user-row';
                } else if (user.position === 1) {
                    rowClass = 'rank-1';
                } else if (user.position === 2) {
                    rowClass = 'rank-2';
                } else if (user.position === 3) {
                    rowClass = 'rank-3';
                }

                const medal = getMedalEmoji(user.position);
                const displayUsername = isCurrentUser ? `${user.username} (You)` : user.username;

                const row = `
                    <tr class="${rowClass}">
                        <td>${medal} ${user.position}</td>
                        <td class="username-cell">${displayUsername}</td>
                        <td class="score-cell">${formatScore(user.score)}</td>
                    </tr>
                `;
                rankingBody.append(row);
            });

            // Add separator if user is not in podium
            if (data.userEntry && !data.podium.some(p => p.username === data.userEntry.username)) {
                // Add visual separator
                const separator = `
                    <tr class="ranking-separator">
                        <td colspan="3">
                            <div class="separator-line">â€¢ â€¢ â€¢</div>
                        </td>
                    </tr>
                `;
                rankingBody.append(separator);

                // Add user's position
                const row = `
                    <tr class="is-success current-user-row">
                        <td>#${data.userEntry.position}</td>
                        <td class="username-cell">${data.userEntry.username} (You)</td>
                        <td class="score-cell">${data.userEntry.score}</td>
                    </tr>
                `;
                rankingBody.append(row);
            }

            // Update user stats
            let userStats = data.userEntry;

            // If user is in top 3, userEntry is null in response, so find in podium
            if (!userStats) {
                userStats = data.podium.find(u => u.username === username);
            }

            if (userStats) {
                $('#gamesPlayed').text(userStats.gamesPlayed);
                $('#totalWins').text(userStats.wins);
                $('#totalScore').text(userStats.score);

                const winRate = userStats.gamesPlayed > 0
                    ? ((userStats.wins / userStats.gamesPlayed) * 100).toFixed(1)
                    : 0;
                $('#winRate').text(winRate + '%');

                // Add position badge to stats
                updateUserPositionDisplay(userStats.position);
            }
        })
        .fail(function(err) {
            console.error("Error fetching ranking:", err);
            const rankingBody = $('#ranking-body');
            rankingBody.empty();
            rankingBody.append(`
                <tr>
                    <td colspan="3" class="error-message">
                        ⚠️ Error loading ranking. Please try again later.
                    </td>
                </tr>
            `);
        });
}

// New function to display user's global position prominently
function updateUserPositionDisplay(position) {
    // Find the score stat box
    const scoreBox = $('#totalScore').closest('.stat-box');
    
    // Determine appropriate styling and emoji based on position
    let positionClass = '';
    let rankEmoji = '';
    
    if (position === 1) {
        positionClass = 'champion-position';
        rankEmoji = '👑 ';
    } else if (position <= 3) {
        positionClass = 'podium-position';
        rankEmoji = '🏆 ';
    } else if (position <= 10) {
        positionClass = 'top-ten-position';
        rankEmoji = '⭐ ';
    }
    
    // Apply special styling if user is in top positions
    if (positionClass) {
        scoreBox.addClass(positionClass);
    }
    
    // Update the score label to include ranking
    scoreBox.find('.stat-label').html(`Score <span style="display: block; font-size: 0.8em; margin-top: 5px;">${rankEmoji}#${position} Global</span>`);
}


function loadUserProfile() {
    userData.userId = userId; // Set userId in userData
    document.getElementById('username').textContent = username;
    document.getElementById('memberSince').textContent = new Date(userData.memberSince).toLocaleDateString();
    document.getElementById('totalCoins').textContent = coins;
    document.getElementById('totalScore').textContent = score;


    loadInventory();
    loadGameResults();
    loadRanking();
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
    getJson(`${SHOP_GET_USER_ITEMS_URL}/${userId}`)
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
    // Clear all user data from localStorage
    localStorage.removeItem("userId");
    localStorage.removeItem("username");
    localStorage.removeItem("coins");
    localStorage.removeItem("score");

    // Redirect to the login page
    window.location.href = "../login";
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
    $("#faqBtn").click(function() {
        window.location.href = "../faq";
    });
});