let selectedFileId = null;
let selectedAlgorithmId = null;

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
            selectedFileId = result.data.id; // 假设返回的 JSON 包含文件的 ID
            renderFileOutput(result.data, 'file-result-box');
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
            selectedAlgorithmId = result.data.id; // 假设返回的 JSON 包含算法的 ID
            renderAlgorithmOutput(result.data, 'algorithm-result-box');
        } else {
            document.getElementById('output').innerText = `Error: ${result.message}`;
        }
    } catch (error) {
        document.getElementById('output').innerText = 'Network error. Please try again later.';
    }
});

document.getElementById('submit-query-button').addEventListener('click', async () => {
    if (!selectedFileId || !selectedAlgorithmId) {
        alert('Please select both a file and an algorithm before submitting.');
        return;
    }

    try {
        const response = await fetch('/query/query', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ fileId: selectedFileId, algorithmId: selectedAlgorithmId })
        });

        const result = await response.json();

        if (result.code === 1) {
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
        listItem.dataset.id = file.id; // 设置 data-id 属性
        listItem.classList.add('clickable'); // 添加点击样式
        listItem.addEventListener('click', () => {
            selectedFileId = file.id; // 记录选中的文件ID
            highlightSelected(listItem, 'file-results'); // 高亮选中项
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