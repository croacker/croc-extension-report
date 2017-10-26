(function () {
    /**
     * Пишем что внедрились
     */
    console.log('injected to tab');

    /**
     * Подписываемся на события от browser extension.
     */
    chrome.runtime.onMessage.addListener(
        function (request, sender, sendResponse) {
            console.log(request);
            chrome.runtime.sendMessage(request.id, {msg:'injected'});
        });
})();
