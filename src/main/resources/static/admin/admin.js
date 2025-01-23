document.addEventListener('DOMContentLoaded', () => {
    // 默认加载用户管理页面数据
    loadUserData();

    // 绑定搜索按钮点击事件
    document.getElementById('search-button').addEventListener('click', loadUserData);

    // 绑定修改用户按钮
    document.getElementById('modify-selected').addEventListener('click', handleModify);

    // 绑定批量删除按钮
    document.getElementById('delete-selected').addEventListener('click', handleDelete);

    // 绑定新增用户按钮点击事件
    document.getElementById('add-user').addEventListener('click', showAddModal);
});

// 加载用户数据
async function loadUserData() {
    const userListDTO = {
        username: document.getElementById('username')?.value|| null,
        role: document.getElementById('role')?.value|| null,
        createTimeStart: document.getElementById('create-time-start')?.value
            ? formatDate(document.getElementById('create-time-start').value)
            : null,
        createTimeEnd: document.getElementById('create-time-end')?.value
            ? formatDate(document.getElementById('create-time-end').value)
            : null,
        updateTimeStart: document.getElementById('update-time-start')?.value
            ? formatDate(document.getElementById('update-time-start').value)
            : null,
        updateTimeEnd: document.getElementById('update-time-end')?.value
            ? formatDate(document.getElementById('update-time-end').value)
            : null,
        delFlag: document.getElementById('del-flag')?.value|| null
    };
    try {
        const response = await fetchWithAuth('/admin/getAllUsers', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userListDTO)
        });
        const result = await response.json();

        if (result.code === 1) {
            renderUserTable(result.data);
        } else {
            alert(result.message || 'Query failed, please contact admin.');
        }
    } catch (error) {
        console.error('Query error:', error);
        alert('Network error, please try again later.');
    }
}

// 渲染用户表格
function renderUserTable(users) {
    const tableBody = document.getElementById('user-table').querySelector('tbody');
    tableBody.innerHTML = '';
    users.forEach(user => {
        const row = `
            <tr>
                <td><input type="checkbox" data-id="${user.id}"></td>
                <td>${user.id}</td>
                <td>${user.role}</td>
                <td>${user.username}</td>
                <td>${formatDate(user.createTime)}</td>
                <td>${formatDate(user.updateTime)}</td>
                <td>${user.delFlag === 'DELETE' ? 'Deleted' : 'Not deleted'}</td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
}

function showAddModal() {
    const addModal = document.getElementById('add-modal');
    addModal.style.display = 'block';

    // 绑定确认和取消按钮事件
    document.getElementById('add-confirm').onclick = handleAddUser;
    document.getElementById('add-cancel').onclick = () => {
        addModal.style.display = 'none';
    };
}

async function handleAddUser() {
    const username = document.getElementById('add-username').value.trim();
    const role = document.getElementById('add-role').value;
    const password = document.getElementById('add-password').value;
    if (!username || !password) {
        alert('Username and password cannot be empty!');
        return;
    }

    // 隐藏新增用户弹窗
    document.getElementById('add-modal').style.display = 'none';
    try {
        const response = await fetchWithAuth('/admin/createUser', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, role, password })
        });
        const result = await response.json()
        if (result.code === 1) {
            alert('Create successfully');
            loadUserData(); // 刷新用户列表
        } else {
            alert(result.message || 'Create failed');
        }
    } catch (error) {
        console.error('Create failed:', error);
        alert('Network error, please try again later.');
    }
}

function handleModify() {
    const selected = getSelectedUsers();
    if (selected.length !== 1) {
        alert('Change one user per time.');
        return;
    }

    const userId = selected[0];
    // 显示修改弹窗
    showEditModal(userId);
}

function handleDelete() {
    const selected = getSelectedUsers();
    if (selected.length === 0) {
        alert('Please select a user to delete');
        return;
    }

    // 发送删除请求
    deleteUsers(selected);
}

// 获取选中用户ID
function getSelectedUsers() {
    return Array.from(document.querySelectorAll('tbody input[type="checkbox"]:checked'))
        .map(input => input.dataset.id);
}

// 删除用户
async function deleteUsers(id) {
    try {
        const response = await fetchWithAuth('/admin/deleteUsers', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(id)
        });
        const result = await response.json();
        if (result.code === 1) {
            alert('Deleted');
            loadUserData();
        } else {
            alert(result.message || 'Deletion failed');
        }
    } catch (error) {
        console.error('Deletion failed:', error);
        alert('Network error, please try again later.');
    }
}

// 显示编辑弹窗
function showEditModal(id) {
    document.getElementById('edit-modal').style.display = 'block';

    // 绑定编辑确认按钮事件
    document.getElementById('edit-confirm').onclick = async () => {
        const username = document.getElementById('edit-username').value;
        const role = document.getElementById('edit-role').value;
        const password = document.getElementById('edit-password').value;
        try {
            const response = await fetchWithAuth(`/admin/editUser`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ id, username, role, password })
            });
            const result = await response.json();
            if (result.code === 1) {
                alert('Edit successfully');
                document.getElementById('edit-modal').style.display = 'none';
                loadUserData();
            } else {
                alert(result.message || 'Edit failed');
            }
        } catch (error) {
            console.error('Edit failed:', error);
            alert('Network error, please try again later.');
        }
    };

    // 绑定取消按钮事件
    document.getElementById('edit-cancel').onclick = () => {
        document.getElementById('edit-modal').style.display = 'none';
    };

}

function getToken() {
    return localStorage.getItem('jwtToken');
}