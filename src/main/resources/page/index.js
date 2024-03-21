function getTableName() {
  let params = new URLSearchParams(window.location.search);
  return params.get("table");
}

async function getTable() {
  let tableName = getTableName();
  let title = document.getElementById("table-title");
  title.innerText = tableName ?? "Select a Table";

  let response = await fetch(`http://localhost:8082/${getTableName()}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  });
  let data = await response.json();
  if (data) {
    let addButton = document.createElement("a");
    addButton.setAttribute("class", "btn btn-info");
    addButton.setAttribute("href", `edit.html?table=${getTableName()}`);
    addButton.textContent = 'New Record';
    document.getElementById("new-button").replaceWith(addButton);

    if (data.length > 0) createTable(data);
  }

}

async function getTables() {
  let response = await fetch('http://localhost:8082/api', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  });

  return await response.json();
}

function createTable(data) {
  let headers = Object.keys(data[0]);

  let table = document.createElement('table');
  let headerRow = document.createElement('thead');

  headers.forEach(e => {
    let th = document.createElement('th');
    th.textContent = e[0].toUpperCase() + e.slice(1);
    headerRow.appendChild(th);
  });

  let actions = document.createElement('th');
  actions.textContent = 'Actions';
  headerRow.appendChild(actions);
  table.appendChild(headerRow);

  let tbody = document.createElement('tbody');

  data.forEach(e => {
    let tr = document.createElement('tr');
    headers.forEach(header => {
      let td = document.createElement('td');
      td.textContent = e[header];
      tr.appendChild(td);
    });
    let td = document.createElement('td');
    let editButton = document.createElement('a');
    editButton.setAttribute("class", "btn btn-warning");
    editButton.setAttribute("href", `edit.html?table=${getTableName()}&id= ${e.id}`);
    editButton.textContent = 'Edit';
    td.appendChild(editButton);
    let deleteButton = document.createElement('button');
    deleteButton.onclick = function () {
      fetch(`http://localhost:8082/${getTableName()}/${e.id}`, {
        method: 'DELETE',
      })
        .then(response => {
          if (!response.ok) {
            console.error('Error:', response.status);
          }
          return response.text();
        })
        .then(data => {
          console.log(data);
          window.location.reload();
        })
        .catch(error => console.error('Error:', error));
    }
    deleteButton.setAttribute("class", "btn btn-danger");
    deleteButton.textContent = 'Delete';
    td.appendChild(deleteButton);
    tr.appendChild(td);
    tbody.appendChild(tr);

    table.appendChild(tbody);
  })

  document.getElementById('table-container').appendChild(table);
}

async function init() {
  let table_list = await getTables();

  let side_links = document.getElementById("side-links");

  table_list.forEach(e => {
    let li = document.createElement("li");
    let a = document.createElement("a");
    a.href = `index.html?table=${e}`
    a.innerText = e;
    li.appendChild(a);

    side_links.appendChild(li);
  });

  getTable();
}

init();