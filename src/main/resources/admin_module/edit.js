const url = 'http://localhost:8082/';
let params = new URLSearchParams(window.location.search);
let tableName = params.get('table');
let id = params.get('id');

let $form = document.querySelector("#form");

function isValidDate(dateString) {
    const regex = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}(\.\d+)?$/;
    return regex.test(dateString);
}

function camelToSnake(str) {
    return str.replace(/[A-Z]/g, letter => `_${letter.toLowerCase()}`);
}

async function generateForm() {

    let res;
    try {
        res = await fetch(url + tableName + "/" + id, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });
    } catch (error) {
        return;
    }

    let json = await res.json();

    let keys = Object.keys(json);

    let hidden = ["id", "createdOn", "updatedOn"];
    let dateInputs = ["expiresOn", "createdOn", "updatedOn"]

    keys.forEach(async (key) => {
        if (hidden.includes(key)) return;
        let label = document.createElement("label");
        let field;

        if (dateInputs.includes(key)) {
            field = document.createElement('input');
            field.type = "date";
            if (json[key] !== null) field.value = json[key].split("T")[0];
        }
        else if (typeof json[key] == 'object') {
            field = document.createElement('select');

            fetch(url + camelToSnake(key)).then(data => data.json()).then(list => {
                let index = -1;
                list.forEach(e => {
                    let option = document.createElement("option");
                    option.value = e.id;
                    option.innerText = e.description;
                    field.appendChild(option);
                    if (e.id === json[key].id) {
                        index = list.indexOf(e);
                    }
                });
                field.selectedIndex = index;
            });
        }
        else {
            field = document.createElement('input');
            field.type = "text";
            field.value = json[key];
        }

        label.setAttribute("for", key);
        label.innerText = key;
        field.name = key;
        $form.appendChild(label);
        $form.appendChild(field);
    });

    let button = document.createElement("input")
    button.type = "submit";
    button.value = "Save";
    $form.appendChild(button);
}

function editEntry(id) {

}

function onSubmit(event) {
    event.preventDefault();
    let data = new FormData($form);
    let body = {}
    if (id == 0) {
        for (const pair of data.entries()) {
            let value = pair[1]
            body[pair[0]] = isNaN(value)? value : parseInt(value);
        }

        fetch(url + tableName,
            {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                method: "POST",
                body: JSON.stringify(body),
            })
            .then(function (res) { console.log(res) })
            .catch(function (res) { console.log(res) })
    }
    else {
        for (const pair of data.entries()) {
            let value = pair[1]
            body[pair[0]] = isNaN(value)? value : parseInt(value);
        }
        console.log(url + tableName + "/" + id)
        console.log(JSON.stringify(body))
        fetch(url + tableName + "/" + id,
            {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                method: "PUT",
                body: JSON.stringify(body),
            })
            .then(function (res) { window.location.replace("/?table=" + tableName) })
            .catch(function (res) { console.log(res) })
    }

    
}

generateForm();