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
    const params = {
        username: document.getElementById('username').value,
        role: document.getElementById('role').value,
        createTimeStart: document.getElementById('create-time-start').value,
        createTimeEnd: document.getElementById('create-time-end').value,
        updateTimeStart: document.getElementById('update-time-start').value,
        updateTimeEnd: document.getElementById('update-time-end').value,
        delFlag: document.getElementById('del-flag').value
    };
    try {
        const response = await fetchWithAuth('/admin/getAllUsers', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(params)
        });
        const result = await response.json();

        if (result.code === 1) {
            renderUserTable(result.data);
        } else {
            alert(result.message || '查询失败');
        }
    } catch (error) {
        console.error('查询失败:', error);
        alert('网络错误，请稍后重试！');
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
                <td>${user.createTime}</td>
                <td>${user.updateTime}</td>
                <td>${user.delFlag === 1 ? '已删除' : '未删除'}</td>
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
        alert('用户名和密码不能为空！');
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
        const result = await response.code
        if (result === 1) {
            alert('新增成功');
            // document.getElementById('add-modal').style.display = 'none';
            loadUserData(); // 刷新用户列表
        } else {
            alert(result.message || '新增失败');
        }
    } catch (error) {
        console.error('新增失败:', error);
        alert('网络错误，请稍后重试！');
    }
}

function handleModify() {
    const selected = getSelectedUsers();
    if (selected.length !== 1) {
        alert('请选择一个用户进行修改');
        return;
    }

    const userId = selected[0];
    // 显示修改弹窗
    showEditModal(userId);
}

function handleDelete() {
    const selected = getSelectedUsers();
    if (selected.length === 0) {
        alert('请选择要删除的用户');
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
async function deleteUsers(userIds) {
    try {
        const response = await fetchWithAuth('/api/users/delete', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ ids: userIds })
        });
        const result = await response.json();
        if (result.code === 1) {
            alert('删除成功');
            loadUserData();
        } else {
            alert(result.message || '删除失败');
        }
    } catch (error) {
        console.error('删除失败:', error);
        alert('网络错误，请稍后重试！');
    }
}

// 显示编辑弹窗
function showEditModal(userId) {
    document.getElementById('edit-modal').style.display = 'block';

    // 绑定编辑确认按钮事件
    document.getElementById('edit-confirm').onclick = async () => {
        const username = document.getElementById('edit-username').value;
        const role = document.getElementById('edit-role').value;
        const password = document.getElementById('edit-password').value;
        try {
            const response = await fetchWithAuth(`/api/users/${userId}/update`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username, role, password })
            });
            const result = await response.json();
            if (result.code === 1) {
                alert('修改成功');
                document.getElementById('edit-modal').style.display = 'none';
                loadUserData();
            } else {
                alert(result.message || '修改失败');
            }
        } catch (error) {
            console.error('修改失败:', error);
            alert('网络错误，请稍后重试！');
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