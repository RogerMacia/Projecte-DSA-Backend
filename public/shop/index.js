// ================= Constants =================
const SHOP_GET_ITEMS_URL = `${BASE_URL}/shop/items`;
// https://dsa3.upc.edu/example/eetacbros/shop/items
console.log("Calling:", SHOP_GET_ITEMS_URL);

// ================= State Variables =================
let cart = [];
let coins = 1000;
currentPlayerId = localStorage.getItem("userId");

// ================= API Functions =================
function getJsonItems(url) {
    return $.ajax({
        url: url,
        dataType: "json",
        method: "GET"
    });
}

const SHOP_BUY_ITEM_URL = `${BASE_URL}/shop/buy`;
function postJsonBuyItems(url,purchaseData) {
    return $.ajax({
        url: url,
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(purchaseData),
        dataType: "json"
    });
}

// ================= UI Functions =================
function showNotification(message, type = 'info') {
    const notification = $('<div class="notification nes-container is-rounded"></div>');

    if (type === 'success') {
        notification.addClass('is-success');
    } else if (type === 'error') {
        notification.addClass('is-error');
    } else {
        notification.addClass('is-primary');
    }

    notification.text(message);

    $('body').append(notification);

    setTimeout(() => {
        notification.fadeOut(300, function() {
            $(this).remove();
        });
    }, 3000);
}

function renderProducts(products) {
    const productsGrid = $('#productsGrid');
    productsGrid.empty();

    if (products.length === 0) {
        productsGrid.html('<div class="empty-cart-message">No hay productos disponibles</div>');
        return;
    }

    products.forEach(function(product) {
        const productCard = createProductCard(product);
        productsGrid.append(productCard);
    });
}

function createProductCard(product) {
    const productCard = $('<div class="product-card"></div>');

    const productHtml = `
        <div class="product-image">
            ${product.emoji}
        </div>
        <div class="product-info">
            <div class="product-name">${product.name}</div>
            <div class="product-description">${product.description}</div>
            <div class="product-price">
                <div class="price">$${product.price}</div>
                <button class="nes-btn is-primary add-to-cart">Add to cart</button>
            </div>
            <div class="durability">Durabilidad: ${product.durability}</div>
        </div>
    `;

    productCard.html(productHtml);

    // Attach click handler to the button
    productCard.find('.add-to-cart').click(function() {
        addItemToCart(product);
    });

    return productCard;
}

function renderCart() {
    const cartItemsContainer = $('#cartItems');
    const cartCount = $('#cartCount');
    const cartTotal = $('#cartTotal');

    cartItemsContainer.empty();

    if (cart.length === 0) {
        cartItemsContainer.html('<div class="empty-cart-message">Your cart is empty.</div>');
        cartCount.text('0');
        cartTotal.text('0');
        return;
    }

    let total = 0;
    let totalItems = 0;

    cart.forEach(function(item, index) {
        total += item.price * item.quantity;
        totalItems += item.quantity;
        const cartItem = createCartItem(item, index);
        cartItemsContainer.append(cartItem);
    });

    cartCount.text(totalItems);
    cartTotal.text(total);

    // Update checkout button state
    updateCheckoutButton(total);
}

function createCartItem(item, index) {
    const cartItem = $('<div class="cart-item"></div>');

    const cartItemHtml = `
        <div class="item-info">
            <div class="item-icon">${item.emoji}</div>
            <div class="item-details">
                <div class="item-name">${item.name}</div>
                <div class="item-price">$${item.price}</div>
            </div>
        </div>
        <div class="item-quantity">
            <button class="nes-btn quantity-btn decrease-quantity">-</button>
            <span class="quantity">${item.quantity}</span>
            <button class="nes-btn quantity-btn increase-quantity">+</button>
            <button class="nes-btn is-error remove-item">Remove</button>
        </div>
    `;

    cartItem.html(cartItemHtml);

    // Attach event handlers
    cartItem.find('.decrease-quantity').click(function() {
        decreaseItemQuantity(index);
    });

    cartItem.find('.increase-quantity').click(function() {
        increaseItemQuantity(index);
    });

    cartItem.find('.remove-item').click(function() {
        removeItemFromCart(index);
    });

    return cartItem;
}

function updateCheckoutButton(total) {
    const checkoutBtn = $('#checkoutBtn');

    if (total > coins) {
        checkoutBtn.prop('disabled', true);
        checkoutBtn.attr('title', 'No tienes suficientes monedas');
    } else {
        checkoutBtn.prop('disabled', false);
        checkoutBtn.removeAttr('title');
    }
}

function updateCoinsDisplay() {
    $('#coins').text(coins);
}

// ================= Cart Functions =================
function addItemToCart(item) {
    // Check if item already exists in cart
    const existingItemIndex = cart.findIndex(cartItem => cartItem.id === item.id);

    if (existingItemIndex !== -1) {
        // Increase quantity if item already exists
        cart[existingItemIndex].quantity += 1;
    } else {
        // Add new item with quantity 1
        cart.push({
            ...item,
            quantity: 1
        });
    }

    renderCart();
    showNotification(`${item.name} added to cart`, 'success');
}

function removeItemFromCart(index) {
    const removedItem = cart[index];
    cart.splice(index, 1);
    renderCart();
    showNotification(`${removedItem.name} removed from cart`);
}

function increaseItemQuantity(index) {
    cart[index].quantity += 1;
    renderCart();
}

function decreaseItemQuantity(index) {
    if (cart[index].quantity > 1) {
        cart[index].quantity -= 1;
        renderCart();
    } else {
        removeItemFromCart(index);
    }
}

function clearCart() {
    cart = [];
    renderCart();
    showNotification('Emptying the cart');
}

function buyCartItems() {
    if (cart.length === 0) {
        showNotification('Your cart is empty.', 'error');
        return;
    }

    // Build the purchase JSON
    const purchaseData = {
        playerId: currentPlayerId,
        items: cart.map(item => ({
            id: item.id,
            name: item.name,
            durability: item.durability,
            price: item.price,
            emoji: item.emoji,
            description: item.description
        }))
    };

    postJsonBuyItems(SHOP_BUY_ITEM_URL, purchaseData)
        .done(function(data) {
            console.log("Purchase successful:", data);
            showNotification('Items purchased successfully!', 'success'); // Mirar totes les crides a aquesta funció
        })
        .fail(function(err) {
            console.error("Error fetching data:", err);
            showNotification('Error to send the products', 'error');
        });
}

function checkout() {
    const total = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);

    if (total > coins) {
        showNotification('You don\'t have enough coins to complete the purchase.', 'error');
        return;
    }

    buyCartItems();
    // Deduct coins
    coins -= total;
    updateCoinsDisplay();


    // Show success message
    showNotification(`Purchase complete! You spent ${total} coins`, 'success');

    // Clear cart
    clearCart();

    // Close cart modal
    closeCartModal();
}

// =================  =================
function onLogoutClick() {
    localStorage.removeItem("userId");
    localStorage.removeItem("username");
    window.location.href = "../login"; // Canvia la ruta si el login està en un altre lloc
}

// ================= Modal Functions =================
function openCartModal() {
    $('#cartModal').addClass('active');
}

function closeCartModal() {
    $('#cartModal').removeClass('active');
}

// ================= Initialization =================
function initializeShop() {
    // Load products
    getJsonItems(SHOP_GET_ITEMS_URL)
        .done(function(data) {
            renderProducts(data);
        })
        .fail(function(err) {
            console.error("Error fetching data:", err);
            showNotification('Error al cargar los productos', 'error');
            $('#productsGrid').html('<div class="empty-cart-message">Error al cargar los productos</div>');
        });

    // Set up event handlers
    $('#cartIcon').click(openCartModal);
    $('#closeCart').click(closeCartModal);
    $('#clearCart').click(clearCart);
    $('#checkoutBtn').click(checkout);

    // Close modal when clicking outside
    $('#cartModal').click(function(e) {
        if (e.target === this) {
            closeCartModal();
        }
    });

    //
    $(document).ready(function() {
        $("#logoutBtn").click(onLogoutClick);
    });

    // Initialize cart display
    renderCart();
    updateCoinsDisplay();
}

// ================= Main =================
$(document).ready(function() {
    initializeShop();
});