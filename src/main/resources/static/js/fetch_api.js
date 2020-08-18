let addForm, addDate, addText;
let editForm, editDate, editText, editId;
let entriesHtml;

const BASE_URL = location.protocol + '//' + location.host + "/api/";

function initializeGlobalVariables() {
    addForm = document.getElementById('addForm');
    addDate = document.getElementById('date');
    addText = document.getElementById('text');
    editForm = document.getElementById('editForm');
    editDate = document.getElementById('edit-date');
    editText = document.getElementById('edit-text');
    editId = document.getElementById('edit-id');
    entriesHtml = document.getElementById('notices');
}


window.addEventListener("load", () => {
    initializeGlobalVariables();




    addForm.addEventListener('submit', (event) => {
        event.preventDefault();
        $('#addModal').modal('hide');
                addNotice(addDate.value, addText.value);
                resetFields(addDate, addText);

    });

    editForm.addEventListener('submit', (event) => {
        event.preventDefault();
        $('#editModal').modal('hide');
            editNotice(editId.value, editDate.value, editText.value);
            resetFields(editId, editDate, editText);

    });

});


function resetFields(...args) {
    args.forEach(argument => argument.value="");
}


function checkEmptyField(...args) {
    for (let i = 0; i < args.length; i++)
        if(args[i] === "") return true;
    return false;
}


function showEditNotice(element) {
    setEditFields(element.parentNode.parentNode.parentNode);
    $('#editModal').modal('show');
}


function setEditFields(element) {
    editDate.value = element.children[0].children[1].innerText;
    editId.value = element.children[0].children[0].innerText;
    editText.value = element.children[1].children[0].innerText;
    console.log(element);
}


function getAll() {
    $.ajax({
        url: BASE_URL + "notices",
        type: 'GET',
        contentType: "application/json",
        success: function(data) {
            updateNotices(data.data);
        },
        fail: (err) => console.log("Couldn't get contacts ", err)
    });
}


function addNotice(date, text) {
    if(checkEmptyField(date, text)) return;
    let dataObject = {date: date, text: text};
    console.log("Adding ", dataObject);
    $.ajax({
        url: BASE_URL + "notice/create",
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(dataObject),
        success: function(data) {
            getAll();
        },
        fail: (err) => console.log("Couldn't create contact " + dataObject, err)
    });
}


function editNotice(id, date, text) {
    if(checkEmptyField(id, date, text)) return;
    let dataObject = {id: id, date: date, text: text}
    console.log("Editing ", dataObject);
    $.ajax({
        url: BASE_URL + "notice/update",
        type: 'PATCH',
        contentType: "application/json",
        data: JSON.stringify(dataObject),
        success: function(data) {
            getAll();
        },
        fail: (err) => console.log("Couldn't update contact " + dataObject, err)
    });
}


function deleteNotice(element) {
    let parent = element.parentNode.parentNode.parentNode
    let id = parent.children[0].children[0].innerText;
    console.log("Deleting ", id);
    console.log(location.protocol + '//' + location.host + "/api/notice/delete/"+id)
    $.ajax({
        url: BASE_URL + "notice/delete/" + id,
        type: 'DELETE',
        contentType: "application/json",
        success: function(data) {
            parent.parentNode.removeChild(parent);
        },
        fail: (err) => console.log("Couldn't delete entry", err)
    });
}


function updateNotices(notices) {
    entriesHtml.innerHTML = "";
    notices.forEach(notice => {
       entriesHtml.appendChild(generateNotice(notice));
    });
}

function generateNotice(notice) {
    const entryHtml = `
        <div class="notice-header card-header">
            <div class="notice-id"><h2>${notice.id}</h2></div>
            <div class="notice-date"><h2>${notice.date}</h2></div>
            <div class="btn-group">
                <button class="btn btn-sm" onclick="showEditNotice(this);">
                    <span class="oi oi-pencil" title="pencil" aria-hidden="true"></span>
                </button>
                <button class="close" onclick="deleteNotice(this);">x</button>
            </div>
        </div>
        <div class="notice-body card-body">
            <div class="notice-text">${notice.text}</div>
            
        </div>
        <div class="clear"></div>`;

    let entryDiv = document.createElement('div');
    entryDiv.setAttribute('class', 'entry card');
    entryDiv.innerHTML = entryHtml;
    return entryDiv;
}
