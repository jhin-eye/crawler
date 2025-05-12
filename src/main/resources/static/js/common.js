// common.js



// 공통 fetch 함수
function authFetch(url, options = {}) {

    if (!options.headers) {
        options.headers = {};
    }

    // JSON 요청의 경우 Content-Type 헤더 설정
    if (!options.headers['Content-Type']) {
        options.headers['Content-Type'] = 'application/json';
    }

    return fetch(url, options);
}

