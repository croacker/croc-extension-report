/**
 * Клик на иконке extension, просто сообщаем что клик был.
 */
chrome.browserAction.onClicked.addListener(function (tab) {
    console.log('Click Simple Browser Extension');
});