
var table = document.querySelector('.table');
var editModal = document.querySelector('#editModal');
var modalInputs = editModal.querySelectorAll('input');
var hiddenInput = document.getElementById('editUid');

table.addEventListener('click', function(event) {
  if (event.target.tagName === 'A' && event.target.getAttribute('data-bs-target') === '#editModal') {
    var row = event.target.parentElement.parentElement;
    var data = row.querySelectorAll('td');
    hiddenInput.value = data[0].textContent;
    // fill inputs
    for (var i = 1; i < modalInputs.length; i++) {
      modalInputs[i].value = data[i].textContent;
    }
  }
});

let selectedUser;
function selectUserId(uid){
  console.log(uid);
  console.log(typeof(uid));
  selectedUser = parseInt(uid,10);
  console.log(selectedUser);
  console.log(typeof(selectedUser));
}

function prepareDeleteModal(){
  document.getElementById("deleteModalUID").value = selectedUser;
}


