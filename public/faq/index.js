/**
 * EETAC BROS - FAQ Application
 * Modular and maintainable refactored version
 */

// ============================================================================
// CONFIGURATION
// ============================================================================
const FAQ_URL = `${BASE_URL}/faqs`;
console.log("FAQ URL:", FAQ_URL);

// ============================================================================
// UTILITY FUNCTIONS
// ============================================================================
function getJson(url) {
    return $.ajax({
        url: url,
        dataType: "json",
        method: "GET"
    });
}

function showNotification(text) {
    const notification = $("#notification");
    $("#notificationText").text(text);
    notification.removeClass("hidden");

    setTimeout(() => {
        notification.addClass("hidden");
    }, 3000);
}

// ============================================================================
// FAQ FUNCTIONS
// ============================================================================

/**
 * Render FAQ items to the DOM
 * @param {Array} faqs - Array of FAQ objects
 */
function renderFAQs(faqs) {
    const faqList = $('#faq-list');
    faqList.empty();
    
    if (!faqs || faqs.length === 0) {
        faqList.html('<p class="nes-text is-warning">No FAQs available yet. Check back soon!</p>');
        return;
    }
    
    faqs.forEach(faq => {
        const faqItem = `
            <div class="faq-item">
                <div class="faq-question">
                    <span class="nes-text is-primary">${faq.question}</span>
                </div>
                <div class="faq-answer">
                    <p>${faq.answer}</p>
                </div>
            </div>
        `;
        faqList.append(faqItem);
    });
}

/**
 * Fetch and display FAQs from the API
 */
function loadFAQs() {
    console.log("Loading FAQs from API...");
    
    getJson(FAQ_URL)
        .done((data, status) => {
            console.log(`Status: ${status}`);
            
            if (status === "success") {
                console.log("FAQs loaded successfully:", data);
                renderFAQs(data);
            }
        })
        .fail((jqXHR, textStatus, errorThrown) => {
            console.error(`Error Status: ${textStatus}, Error Thrown: ${errorThrown}`);
            console.error(`Response Text: ${jqXHR.responseText}`);
            
            renderFAQs([]);
            showNotification("⚠️ API not available. Unable to load FAQs.");
        });
}

/**
 * Handle FAQ accordion click
 */
function onFAQQuestionClick() {
    $(this).next('.faq-answer').slideToggle();
    $(this).closest('.faq-item').toggleClass('is-open');
}

/**
 * Handle profile button click
 */
function onProfileBtnClick() {
    console.log("Profile button clicked!");
    window.location.href = '../profile';
}

// ============================================================================
// INITIALIZATION
// ============================================================================

/**
 * Initialize the FAQ page
 */
function onReadyDocument() {
    console.log("Initializing FAQ page...");
    
    // Setup event handlers
    $('#faq-list').on('click', '.faq-question', onFAQQuestionClick);
    $('#profileBtn').on('click', onProfileBtnClick);
    
    // Load FAQs
    loadFAQs();
}

$(document).ready(onReadyDocument);