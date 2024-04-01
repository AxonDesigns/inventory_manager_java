/*class DataTable extends HTMLElement {

  static get observedAttributes() {
    return ['url'];
  }
  constructor() {
    super();
  }

  connectedCallback() {
    const link = document.createElement("link");
    link.setAttribute("rel", "stylesheet");
    link.setAttribute("href", "data_table.css");

    let shadow = this.attachShadow({ mode: 'open' });
    let url = this.getAttribute('url');

    shadow.appendChild(link);
    shadow.innerHTML = `<h2>${url}</h2>`;
  }

  disconnectedCallback() {

  }

  attributeChangedCallback(name, oldValue, newValue) {

  }
}
customElements.define('data-table', DataTable);
*/

function isValidDate(dateString) {
  const regex = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}(\.\d+)?$/;
  return regex.test(dateString);
}

function formatDate(dateString) {
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  return `${year}/${month}/${day} | ${hours}:${minutes}`;
}

function formatTitle(inputString) {
  return inputString.replace(/([A-Z])/g, ' $1').replace(/^./, function (str) { return str.toUpperCase(); });
}

function formatSnake(str) {
  return str.replace(/_/g, ' ').replace(/\b\w/g, c => c.toUpperCase());
}

const url = 'http://localhost:8082/';
let params = new URLSearchParams(window.location.search);
let tableName = params.get('table');

let tableContainer = document.getElementById('table-container');

function generateNewButton() {
  let newButton = document.createElement("a");
  newButton.classList.add("btn", "btn-primary");
  newButton.href = `edit.html?op=new&table=${tableName}`;
  newButton.innerText = "New Record";
  tableContainer.appendChild(newButton);
}

function generateTitle(content) {
  let title = document.createElement("h3");
  title.classList.add("title");
  title.innerText = formatSnake(content);
  return title;
}

function generateTableTitle() {
  let tableTitle = generateTitle(tableName);
  tableContainer.appendChild(tableTitle);
}

async function generateContent() {
  if (!tableName) {
    tableContainer.appendChild(generateTitle("Select a table"));
    return;
  }

  let res;
  try {
    res = await fetch(url + tableName, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    });
  } catch (error) {
    tableContainer.appendChild(generateTitle("Table Not Found"));
    return;
  }

  let json = await res.json();

  if (json.length == 0) {

    generateTableTitle();
    generateNewButton();

    let noData = document.createElement("p");
    noData.innerText = "No Records Found";
    tableContainer.appendChild(noData);
    return;
  }

  generateTable(json);
}

function generateTable(data) {

  let table = document.createElement("table");

  //create thead
  let thead = document.createElement("thead");
  let keys = Object.keys(data[0]);

  let theadRow = document.createElement("tr");
  keys.forEach(key => {
    let th = document.createElement("th");
    th.innerText = formatTitle(key);
    theadRow.appendChild(th);
  })

  //create actions column
  let th = document.createElement("th");
  th.innerText = "Actions";
  theadRow.appendChild(th);

  thead.appendChild(theadRow);
  table.appendChild(thead);

  //create tbody
  let tbody = document.createElement("tbody");
  data.forEach(item => {
    let tbodyRow = document.createElement("tr");
    keys.forEach(key => {
      let td = document.createElement("td");
      td.innerText = isValidDate(item[key]) ? formatDate(item[key]) : item[key];
      tbodyRow.appendChild(td);
    })

    //create edit and delete buttons
    let td = document.createElement("td");
    let buttonsDiv = document.createElement("div");
    buttonsDiv.classList.add("btn-group");
    let editButton = document.createElement("button");
    let deleteButton = document.createElement("button");
    editButton.innerText = "Edit";
    deleteButton.innerText = "Delete";
    editButton.classList.add("btn", "btn-warning");
    deleteButton.classList.add("btn", "btn-danger");
    editButton.onclick = () => editEntry(item['id']);
    deleteButton.onclick = () => deleteEntry(item['id']);
    buttonsDiv.appendChild(editButton);
    buttonsDiv.appendChild(deleteButton);
    td.appendChild(buttonsDiv);
    tbodyRow.appendChild(td);

    tbody.appendChild(tbodyRow);
  })

  table.appendChild(tbody);

  //append to table container
  generateTableTitle();
  generateNewButton();
  tableContainer.appendChild(table);

}

function editEntry(id) {
  console.log(id);
}

async function deleteEntry(id) {
  let res = await fetch(url + tableName + '/' + id, {
    method: 'DELETE',
  });

  let text = await res.text();

  if (!res.ok) {
    alert(text);
    return;
  }

  window.location.reload();
}

generateContent();