/**
 * @author Markitanov Vadim
 * @since 20.09.2022
 */

const taskItemIdPrefix = 'task-item-id-';
let selectedTaskId = null;

const postTaskButton = document.getElementById('post-task-button');
const deleteTaskButton = document.getElementById('delete-task-button');

requestConfig();
requestTasks();

function requestConfig() {
    axios.get('http://localhost:8080/config')
        .then(response => {
            const config = response.data;
            const urlValueDiv = document.getElementById('url-value-div');
            urlValueDiv.innerText = config.url;
            document.getElementById('schema-value-div').innerText = config.user;
            document.getElementById('server-value-div').innerText = config.server;
        }).catch(error => {
        console.error('Request config error: ', error);
    });
}

function requestTasks() {
    axios.get('http://localhost:8080/tasks')
        .then(response => {
            const json = response.data;
            const divContent = document.getElementById('div-content');
            divContent.innerText = '';
            json.forEach(function (item) {
                this.addTaskInDiv(divContent, item);
            });
        }).catch(error => {
        console.error('Request config error: ', error);
    });
}

function addTaskInDiv(divContent, item) {
    let taskItemDiv = document.createElement('div');
    taskItemDiv.setAttribute("class", "task-item-div")
    taskItemDiv.setAttribute('id', taskItemIdPrefix + item.id);
    const urlArr = item.url.split('/');
    const taskNumber = urlArr[urlArr.length - 1];
    const taskA = document.createElement('a');
    taskA.setAttribute('href', item.url);
    taskA.innerText = taskNumber;
    taskA.target = '_blank';
    taskA.setAttribute("class", 'task-link');
    taskItemDiv.append(taskA);
    taskItemDiv.append(' ');

    const taskImg = document.createElement('img');
    taskImg.setAttribute('alt', 'Priority');
    taskImg.setAttribute('src', '../images/priorities/' + item.priority + '.svg');
    taskImg.setAttribute('width', '15');
    taskImg.setAttribute('height', '15');
    taskItemDiv.append(taskImg);
    taskItemDiv.append(' ')

    taskItemDiv.append(item.name);
    taskItemDiv.addEventListener('click', function (event) {
        const target = event.target;
        const pattern = /task-item-id-\d+$/;
        if (target.id.match(pattern) == null) {
            return;
        }
        const targetIdArr = target.id.split('-');
        const targetId = targetIdArr[targetIdArr.length - 1];

        if (targetId === selectedTaskId) {
            if (target.classList.contains('task-item-div-active')) {
                target.classList.remove('task-item-div-active');
                selectedTaskId = null;
            } else {
                target.classList.add('task-item-div-active');
                selectedTaskId = targetId;
            }
        } else {
            if (selectedTaskId == null) {
                target.classList.add('task-item-div-active');
                selectedTaskId = targetId;
            } else {
                const old = document.getElementById(taskItemIdPrefix + selectedTaskId);
                old.classList.remove('task-item-div-active');

                target.classList.add('task-item-div-active');
                selectedTaskId = targetId;
            }
        }

        deleteTaskButton.style.visibility = selectedTaskId === null ? 'hidden' : 'visible';
    });

    divContent.append(taskItemDiv);
}

function postTaskHandler() {
    console.log('Post task.');
    const priority = this.getRandomInt(0, 4);
    const newTask = {
        name: 'Задание №' + priority,
        url: 'https://jira-fss.bftcom.com/browse/FSSPPO-52044',
        priority: priority
    };
    axios({
        url: 'http://localhost:8080/tasks',
        method: 'POST',
        data: newTask
    }).then(response => {
        console.log('data: ', response.data);
        const divContent = document.getElementById('div-content');
        this.addTaskInDiv(divContent, response.data);
    }).catch(error => {
        console.error('Post task error: ', error);
    });
}

function getRandomInt(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

function deleteTaskHandler() {
    if (selectedTaskId == null) {
        return;
    }

    axios({
        url: 'http://localhost:8080/tasks/' + selectedTaskId,
        method: 'DELETE'
    }).then(response => {
        if (response.data === true) {
            selectedTaskId = null;
            deleteTaskButton.style.visibility = 'hidden';
            this.requestTasks();
        }
    }).catch(error => {
        console.error('Delete task error: ', error);
    });
}

postTaskButton.addEventListener("click", async () => {
    postTaskHandler();
});

deleteTaskButton.addEventListener('click', async () => {
    deleteTaskHandler();
});