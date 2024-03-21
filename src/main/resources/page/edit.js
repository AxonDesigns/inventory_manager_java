function getTableName(){
  let params = new URLSearchParams(window.location.search);
  return params.get("table");
}

function createForm() {
  let params = new URLSearchParams(window.location.search);

  let id = params.get('id') ?? 0;
  fetch(`http://localhost:8082/${getTableName()}/` + id, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  })
    .then(response => response.json())
    .then(data => {
      let hidden = ["id", "createdOn", "updatedOn"];
      let form = document.getElementById('form');
      Object.keys(data).forEach(key => {
        if(hidden.includes(key)) return;

        let input = document.createElement('input');
        
        input.name = key;
        input.type = 'text';
        input.required = true;
        input.placeholder = key;
        if (key !== "updatedOn" && data[key]) {
          input.value = data[key];
        }

        form.appendChild(input);
      });

      let button = document.createElement('button');
      button.type = 'submit';
      button.classList.add('btn', 'btn-info');
      button.innerText = 'Save Record';
      form.appendChild(button);
    })
    .catch((error) => {
      console.error('Error:', error);
    });

}

function editRecord(event) {
  event.preventDefault();

  let formData = new FormData(document.querySelector('form'))
  let data = {};
  formData.forEach((value, key) => {
    data[key] = value === "" ? null : value;
  });

  let params = new URLSearchParams(window.location.search);
  let id = params.get('id') ?? 0;

  fetch(`http://localhost:8082/${getTableName()}` + (id == 0 ? '' : `/${id}`), {
    method: id == 0? 'POST' : 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  })
    .then(response => response.json())
    .then(data => {
      window.location.href = `index.html?table=${getTableName()}`;
    })
    .catch((error) => {
      console.error('Error:', error);
    });
}

createForm();