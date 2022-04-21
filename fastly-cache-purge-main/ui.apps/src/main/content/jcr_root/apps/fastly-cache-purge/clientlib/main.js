$(function () {
    const SERVLET_PATH = "/apps/fastly-cache-purge/jcr:content/content/items/app.json"
    var foundationUI = $(window).adaptTo('foundation-ui');
    const cleanPaths = pathsString => {
        return pathsString
        .split('\n')
        .filter(Boolean)
        .map(u => u.trim());
    }
    const getInvalidPaths = (pathsString) => {
        if (!pathsString) return [];
        else return cleanPaths(pathsString)
        .filter(u => !u.startsWith('/'))
    }

    const invalidationItem = (path) => {
        var item = new Coral.List.Item()
        var content = `<coral-status variant="info" class="blinking"><coral-status-label>${path}</coral-status-label></coral-status>`
        item.set({
            innerHTML: content,
            id: path
        })
        return item;
    }

    const showRequestInfo = (url, response) => {
        const dialog = document.getElementById('request-info')
        const urlEl = document.getElementById('request-info-url')
        const responseEl = document.getElementById('request-info-response')
        urlEl.innerHTML = url
        responseEl.value = response
        dialog.show();
    }


    // run 
    const pathsTextArea = document.getElementById('paths')
    const pathsErrorTooltip = document.getElementById('paths-error')
    const invalidPathsElement = document.getElementById('invalid-paths')
    const hostInput = document.getElementById('publish-host')
    const hostError = document.getElementById('host-error')
    const invalidateButton = document.getElementById('invalidate')
    const invalidateList = document.getElementById('invalidate-list')


    // host field validation
    hostInput.addEventListener('change', function() {
        const host = hostInput.value
        const innerInput = hostInput.querySelector('input[is="coral-textfield"]');
        console.log(hostInput, host)
        if (!host) {
            innerInput.invalid = true
            hostError.content.innerHTML = 'required';
            hostError.show();
        } else if (!host.startsWith('http://') & !host.startsWith('https://')) {
            innerInput.invalid = true
             hostError.content.innerHTML = 'Must start with https:// or http://';
             hostError.show();
        } else {
            innerInput.invalid = false
            hostError.hide();
        }
    }, false);

    // Paths field Validation
    pathsTextArea.addEventListener('change', function() {
        const invalidPaths = getInvalidPaths(pathsTextArea.value)
        if (invalidPaths.length) {
            pathsTextArea.setCustomValidity(invalidPaths.join('\n'))
            invalidPathsElement.innerHTML = invalidPaths.join('</br>')
            pathsErrorTooltip.show()
        } else {
            pathsTextArea.setCustomValidity('')
            pathsErrorTooltip.hide()
        }
    }, false);

    invalidateButton.addEventListener('click', function () {
        invalidateList.items.clear()
        pathsTextArea.set({disabled: true})
        hostInput.set({disabled: true})
        const publishDomain = hostInput.value;
        const paths = cleanPaths(pathsTextArea.value)

        const invalidationItems = paths.map(invalidationItem)
        // add items
        invalidationItems.forEach(item => invalidateList.items.add(item));
        const promises = invalidationItems.map(item => {
            const host = publishDomain;
            const path = item.id;
            return $.post(`${SERVLET_PATH}?${$.param({ host: host, path: path})}`,)
            .done(data => {
                const statusEl = item.querySelector(".blinking")
                statusEl.classList.remove("blinking")
                statusEl.set({variant: 'success'})
                item.on('click', () => showRequestInfo(host+path, JSON.stringify(data)))
            })
            .fail((error) => {
                const statusEl = item.querySelector(".blinking")
                statusEl.classList.remove("blinking")
                statusEl.set({variant: 'error'})
                item.on('click', () => showRequestInfo(host+path, err))
            });
        });

        $.when(...promises)
        .always(function() {
            pathsTextArea.set({disabled: false})
            hostInput.set({disabled: false})
            foundationUI.notify("", "Cache invalidation Complete for all items. Check status in items list");
        })
        invalidateList.scrollIntoView();
    })
})