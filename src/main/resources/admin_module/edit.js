let params = new URLSearchParams(window.location.search);
let tableName = params.get('table');
let operation = params.get('op');

let $form = document.querySelector("#form");

console.log(tableName);
console.log(operation);