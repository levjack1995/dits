const togglePassword = document.querySelector('#togglePassword');
const password = document.querySelector('#password-input');

togglePassword.addEventListener('click', function (e) {
  const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
  password.setAttribute('type', type);
  this.classList.toggle('fa-eye-slash');
});

const loginInput = document.querySelector('#login-input');
const loginHelp = document.getElementById('loginHelp');
const submitButton = document.querySelector('#submit');

// submitButton.addEventListener('click', function (){
//   const length = document.getElementById('login-input').value.getLength();
//   if (length < 4) {
//     loginHelp.setAttribute('display','block');
//   }
//
// });

const input = document.getElementById('login-input');
input.addEventListener('input',checkInputForPassword);

function checkInputForPassword() {
  console.log(input.value.length);
  if (input.value.length >= 1 && input.value.length < 4) {
    loginHelp.style.display = 'block';
  }
  else {
    loginHelp.style.display = 'none';
  }
}