let selectedFileId = null;
let selectedAlgorithmId = null;
let columnNames = []

document.getElementById('toggle-conditional-query').style.display = 'block';
document.getElementById('toggle-parallel-query').style.display = 'block';

document.getElementById('file-search-button').addEventListener('click', async () => {
    const filename = document.getElementById('file-name').value.trim();

    try {
        const response = await fetchWithAuth('/file/getFiles', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ filename })
        });

        const result = await response.json();

        if (result.code === 1) {
            selectedFileId = result.data.id;
            renderFileOutput(result.data, 'file-result-box');
            // fetchColumnFilters();
        } else {
            document.getElementById('output').innerText = `Error: ${result.message}`;
        }
    } catch (error) {
        document.getElementById('output').innerText = 'Network error. Please try again later.';
    }
});

document.getElementById('algorithm-search-button').addEventListener('click', async () => {
    const name = document.getElementById('algorithm-name').value.trim();

    try {
        const response = await fetchWithAuth('/algorithm/getAlgorithms', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ name })
        });

        const result = await response.json();

        if (result.code === 1) {
            selectedAlgorithmId = result.data.id;
            renderAlgorithmOutput(result.data, 'algorithm-result-box');
        } else {
            document.getElementById('output').innerText = `Error: ${result.message}`;
        }
    } catch (error) {
        document.getElementById('output').innerText = 'Network error. Please try again later.';
    }
});

function removeModal(){
    // 清空输入框
    document.querySelectorAll('#column-filters input').forEach(input => {
        input.value = null;
    });
    document.querySelectorAll('#column-subsets input').forEach(input => {
        input.value = null;
    })
    // 清空下拉框
    document.querySelectorAll('#column-filters select').forEach(select => {
        select.selectedIndex = 0;
    });

    document.getElementById('epsilon').value = null;
}

async function fetchColumnFilters() {
    if (!selectedFileId) return;
    try {
        const response = await fetchWithAuth(`/query/getFileColumns/${selectedFileId}`);
        const result = await response.json();
        if (result.code === 1) {
            columnNames = result.data;
            // renderColumnFilters(result.data);
        }
    } catch (error) {
        alert('Failed to load columns.');
    }
}

async function fetchPrivacyBudget(){
    if (!selectedFileId) return;
    try {
        const response = await fetchWithAuth(`/query/getBudget/${selectedFileId}`);

        const result = await response.json();
        if(result.code === 1){
            document.getElementById('privacy-budget-value').innerText = result.data.toFixed(2);
            document.getElementById('privacy-budget-box').style.display = 'block';
        } else {
            document.getElementById('privacy-budget-value').innerText = 'Error';
            document.getElementById('privacy-budget-box').style.display = 'block';
        }
    } catch (error) {
        document.getElementById('privacy-budget-value').innerText = 'Failed';
        document.getElementById('privacy-budget-box').style.display = 'block';
    }
}

document.getElementById('toggle-conditional-query').addEventListener('click', () => {
    const section = document.getElementById('conditional-query-section');

    if (section.style.display === 'none' || section.style.display === '') {
        if(document.getElementById('parallel-query-section').style.display === 'block'){
            document.getElementById('parallel-query-section').style.display = 'none';
            removeModal();
        }
        // 显示高级查询
        section.style.display = 'block';
    } else {
        // 隐藏高级查询，并清空所有输入框的值
        section.style.display = 'none';
    }
    removeModal();
});

// 监听 "添加筛选条件" 按钮
document.getElementById('add-filter').addEventListener('click', () => {
    if (columnNames.length === 0) {
        alert('Please select a file first, and wait for loading the column names.');
        return;
    }
    addFilterRow();
});

// 动态添加筛选条件行
function addFilterRow() {
    const container = document.getElementById('column-filters');

    const div = document.createElement('div');
    div.classList.add('filter-row');

    div.innerHTML = `
        <select class="column">
            ${columnNames.map(col => `<option value="${col}">${col}</option>`).join('')}
        </select>
        <select class="operator">
            <option value=">">&gt;</option>
            <option value=">=">&ge;</option>
            <option value="<">&lt;</option>
            <option value="<=">&le;</option>
            <option value="==">=</option>
        </select>
        <input type="text" class="value" placeholder="输入值">
        <select class="logic">
            <option value="and">AND</option>
            <option value="or">OR</option>
        </select>
        <button onclick="removeFilter(this)">Delete</button>
    `;

    container.appendChild(div);
}

document.getElementById('toggle-parallel-query').addEventListener('click', () => {
    const section = document.getElementById('parallel-query-section');

    if(section.style.display === 'none' || section.style.display === ''){
        if(document.getElementById('conditional-query-section').style.display === 'block'){
            document.getElementById('conditional-query-section').style.display = 'none';
            removeModal();
        }

        section.style.display = 'block';

        const dropdown = document.getElementById('columnName');
        dropdown.innerHTML = '';

        columnNames.forEach(columnName => {
            const opt = document.createElement('option');
            opt.value = columnName;
            opt.textContent = columnName;
            dropdown.appendChild(opt);
        })
    } else {
        section.style.display = 'none';
    }
    removeModal();
});

document.getElementById('add-subset').addEventListener('click', () => {
    addSubsetRow();
});

function addSubsetRow() {
    const container = document.getElementById('column-subsets');

    const div = document.createElement('div');
    div.classList.add('subset-row');

    div.innerHTML = `
        <label for="min">Lower bound: </label>
        <input type="number" min="-2147483648" max="2147483647" class="min">
        <label for="max">Higher bound: </label>
        <input type="number" min="-2147483648" max="2147483647" class="max">
        <label for="epsilon">Subset epsilon: </label>
        <input type="number" min="0.01" max="1" step="0.1" class="epsilon">
        <button onclick="removeFilter(this)">Delete</button>
    `;
    container.appendChild(div);
}

// 删除筛选条件行
function removeFilter(button) {
    button.parentElement.remove();
}

document.getElementById('submit-query-button').addEventListener('click', async () => {
    if (!selectedFileId || !selectedAlgorithmId) {
        alert('Please select both a file and an algorithm before submitting.');
        return;
    }

    // If conditional query
    let filters = [];
    document.querySelectorAll('.filter-row').forEach(row => {
        const columnName = row.querySelector('.column').value;
        const operator = row.querySelector('.operator').value;
        const value = row.querySelector('.value').value.trim();
        const logic = row.querySelector('.logic').value;

        if (value && columnName && operator && logic) {
            filters.push({ columnName, operator, value, logic });
        }
    });
    let epsilon = document.getElementById('epsilon')?.value|| null;
    if(filters.length === 0){
        filters = null;
    }

    // If parallel composition query
    let subsets = [];
    document.querySelectorAll('.subset-row').forEach(row => {
        const min = row.querySelector('.min').value;
        const max = row.querySelector('.max').value;
        const epsilon = row.querySelector('.epsilon').value;

        subsets.push({min, max, epsilon});

    });
    if(subsets.length > 0){
        epsilon = null;
    } else {
        subsets = null;
    }
    const columnName = document.getElementById('columnName')?.value|| null;

    try {
        const response = await fetchWithAuth('/query/query', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                fileId: selectedFileId,
                algorithmId: selectedAlgorithmId,
                filters,
                subsets,
                epsilon,
                columnName
            })
        });

        const result = await response.json();

        if (result.code === 1) {
            fetchPrivacyBudget();
            renderQueryOutput(result.data);
        } else {
            document.getElementById('output').innerText = `Error: ${result.message}`;
        }

    } catch (error) {
        document.getElementById('output').innerText = 'Network error. Please try again later.';
    }
});


function highlightSelected(selectedItem, parentId) {
    const parent = document.getElementById(parentId);
    const items = parent.querySelectorAll('li');
    items.forEach(item => item.classList.remove('selected'));
    selectedItem.classList.add('selected');
}

function renderFileOutput(data) {
    const outputDiv = document.getElementById('file-results');
    outputDiv.innerHTML = ''; // 清空之前的结果
    data.forEach(file => {
        const listItem = document.createElement('li');
        listItem.textContent = file.filename;
        listItem.dataset.id = file.id;
        listItem.classList.add('clickable');
        listItem.addEventListener('click', () => {
            selectedFileId = file.id;
            highlightSelected(listItem, 'file-results');
            fetchColumnFilters();
            fetchPrivacyBudget();
        });
        outputDiv.appendChild(listItem);
    });
}

function renderAlgorithmOutput(data) {
    const outputDiv = document.getElementById('algorithm-results');
    outputDiv.innerHTML = ''; // 清空之前的结果
    data.forEach(algorithm => {
        const listItem = document.createElement('li');
        listItem.textContent = algorithm.name;
        listItem.dataset.id = algorithm.id; // 设置 data-id 属性
        listItem.classList.add('clickable'); // 添加点击样式
        listItem.addEventListener('click', () => {
            selectedAlgorithmId = algorithm.id; // 记录选中的算法ID
            highlightSelected(listItem, 'algorithm-results'); // 高亮选中项
        });
        outputDiv.appendChild(listItem);
    });
}

function renderQueryOutput(data) {
    const outputDiv = document.getElementById('output');
    outputDiv.innerHTML = '<pre>' + JSON.stringify(data, null, 2) + '</pre>';
}