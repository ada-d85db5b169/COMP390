async function fetchWithAuth(url, options = {}) {
    const token = localStorage.getItem('jwtToken'); // 从 localStorage 获取 token
    console.log('Sent token: ', token);
    // 如果 token 存在，添加到请求头中
    const headers = {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json',
        ...options.headers, // 合并用户传入的 headers
    };

    const response = await fetch(url, {
        ...options,
        headers: headers,
    });

    // 处理 401 错误，令牌过期
    if (response.status === 401) {
        alert('登录已过期，请重新登录');
        window.location.href = '/login.html'; // 跳转到登录页
    }

    return response;
}

async function fetchWithAuthMultipart(url, options = {}) {
    const token = localStorage.getItem('jwtToken'); // 从 localStorage 获取 token
    console.log('Sent token: ', token);
    // 如果 token 存在，添加到请求头中
    const headers = {
        'Authorization': `Bearer ${token}`,
        ...options.headers, // 合并用户传入的 headers
    };

    const response = await fetch(url, {
        ...options,
        headers: headers,
    });

    // 处理 401 错误，令牌过期
    if (response.status === 401) {
        alert('Login expired.');
        window.location.href = '/login.html'; // 跳转到登录页
    }

    return response;
}

function formatDate(date) {
    const d = new Date(date);
    const year = d.getFullYear();
    const month = String(d.getMonth() + 1).padStart(2, '0');
    const day = String(d.getDate()).padStart(2, '0');
    const hours = String(d.getHours()).padStart(2, '0');
    const minutes = String(d.getMinutes()).padStart(2, '0');
    const seconds = String(d.getSeconds()).padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}