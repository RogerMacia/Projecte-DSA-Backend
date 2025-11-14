const BASE_URL = "http://localhost:8080/example/eetacbros/items"

$.postJSON = function(url, data, callback) {
    return jQuery.ajax({
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    },
    'type': 'POST',
    'url': url,
    'data': JSON.stringify(data),
    'dataType': 'json',
    'success': callback
    });
};

$(document).ready(function() {
    let cart = [];

    // Add to cart functionality
    $('.add-to-cart').click(function() {
        const product = $(this).closest('.product');
        const item = {
            id: product.data('id'),
            name: product.data('name'),
            price: parseFloat(product.data('price'))
        };

        // Check if item already exists in cart
        const existingItem = cart.find(i => i.id === item.id);
        if (existingItem) {
            existingItem.quantity++;
        } else {
            item.quantity = 1;
            cart.push(item);
        }

        updateCart();
        $(this).text('Added!').prop('disabled', true);
        setTimeout(() => {
            $(this).text('Add to Cart').prop('disabled', false);
        }, 1000);
    });

    // Update cart display
    function updateCart() {
        const cartItems = $('#cart-items');
        const emptyMessage = $('#empty-message');
        const cartTotal = $('#cart-total');
        const clearBtn = $('#clear-cart');

        if (cart.length === 0) {
            emptyMessage.show();
            cartItems.empty();
            cartTotal.empty();
            clearBtn.hide();
            return;
        }

        emptyMessage.hide();
        clearBtn.show();
        cartItems.empty();

        let total = 0;
        cart.forEach(item => {
            const itemTotal = item.price * item.quantity;
            total += itemTotal;

            cartItems.append(`
                <li>
                    ${item.name} - $${item.price.toFixed(2)} x ${item.quantity} = $${itemTotal.toFixed(2)}
                    <button class="remove-item" data-id="${item.id}">Remove</button>
                </li>
            `);
        });

        cartTotal.html(`<strong>Total: $${total.toFixed(2)}</strong>`);
    }

    // Remove item from cart
    $(document).on('click', '.remove-item', function() {
        const id = $(this).data('id');
        cart = cart.filter(item => item.id !== id);
        updateCart();
    });

    // Clear entire cart
    $('#clear-cart').click(function() {
        if (confirm('Are you sure you want to clear your cart?')) {
            cart = [];
            updateCart();
        }
    });
});