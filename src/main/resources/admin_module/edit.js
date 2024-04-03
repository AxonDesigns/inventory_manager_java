const url = 'http://localhost:8082/';
let params = new URLSearchParams(window.location.search);
let tableName = params.get('table');
let id = params.get('id');

let $form = document.querySelector("#form");

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

    console.log(json);

    keys.forEach(key => {
        console.log(json[key]);
        let field = document.createElement('input');
        field.type = "text";
        field.name = key;
        field.value =json[key];
        /*if (typeof json[key] == 'object') {
        }*/
        $form.appendChild(field);
      })
}

generateForm();